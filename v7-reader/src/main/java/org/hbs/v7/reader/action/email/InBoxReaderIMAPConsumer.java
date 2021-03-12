package org.hbs.v7.reader.action.email;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import org.apache.commons.io.IOUtils;
import org.hbs.core.beans.model.Producers;
import org.hbs.core.kafka.IKAFKAPartition;
import org.hbs.core.security.resource.IPathBase.EMedia;
import org.hbs.core.util.CommonValidator;
import org.hbs.core.util.CustomException;
import org.hbs.v7.beans.DataInTopicBean;
import org.hbs.v7.beans.InBoxReaderTopicBean;
import org.hbs.v7.beans.UIDMimeMessageBean;
import org.hbs.v7.beans.model.DataAttachments;
import org.hbs.v7.beans.model.ICoreBase;
import org.hbs.v7.beans.model.IncomingData;
import org.hbs.v7.beans.model.IncomingData.EIncomingStatus;
import org.hbs.v7.dao.IncomingDao;
import org.hbs.v7.reader.action.core.IncomingDataCreator;
import org.hbs.v7.userdefined.dao.CoreBaseDao;
import org.hbs.v7.userdefined.model.CoreBaseFactory;
import org.hbs.v7.util.IKafkaTopicConstants;
import org.hbs.v7.util.PartitionFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.mail.imap.IMAPFolder;

@Service
public class InBoxReaderIMAPConsumer extends InBoxReaderIMAPBase implements IKafkaTopicConstants
{

	private static final long	serialVersionUID	= -3529623337510779624L;

	@Autowired
	private IncomingDao			incomingDao;

	@Autowired
	private CoreBaseDao			coreBaseDao;

	int							iter				= 0;

	private final Logger		logger				= LoggerFactory.getLogger(InBoxReaderIMAPConsumer.class);

	@KafkaListener(topicPartitions = @TopicPartition(topic = MESSAGE_TOPIC, partitions = { NORMAL, EXPEDITE }), groupId = MESSAGE_GROUP, clientIdPrefix = EMAIL)
	public void consume(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, String payload)
	{
		logger.info(String.format((iter++) + "#### -> Consumed message -> %s", payload));
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>" + new Date() + ">>>>>>>flow > " + partition);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		try
		{
			ObjectMapper oMapper = new ObjectMapper();
			InBoxReaderTopicBean inBoxReaderBean = oMapper.readValue(payload, InBoxReaderTopicBean.class);

			this.config = inBoxReaderBean.config;

			logger.info(inBoxReaderBean.messageNumber + " -    -    -   " + inBoxReaderBean.sentDate);

			IMAPFolder imapFolder = getIMAPFolder(EFolder.Inbox);
			Message[] messages = imapFolder.getMessages(new int[] { inBoxReaderBean.messageNumber });

			if (CommonValidator.isArrayFirstNotNull(messages) && messages[0].getSentDate().compareTo(inBoxReaderBean.sentDate) == 0)
			{
				Message message = messages[0];
				logger.info(message.getMessageNumber() + " -S- " + inBoxReaderBean.messageNumber + "   -  " + inBoxReaderBean.sentDate + "  -   " + message.getSentDate());

				IncomingData incomingData = processMultiPart(new UIDMimeMessageBean(config, message));

				if (CommonValidator.isNotNullNotEmpty(incomingData))
				{
					ICoreBase coreBase = CoreBaseFactory.getInstance(config.getProducerId());
					coreBaseDao.save(coreBase);

					incomingData.setCandidateEmail(message.getFrom()[0].toString());
					incomingData.setMedia(EMedia.Email);
					incomingData.setIncomingStatus(EIncomingStatus.New);
					incomingData.setSubject(message.getSubject());
					incomingData.setSentTime(message.getSentDate().getTime());
					incomingData.setUniqueId(imapFolder.getUID(message) + "");
					incomingData.setReaderInstance(this.getClass().getSimpleName());
					incomingData.setCreatedDate(new Timestamp(System.currentTimeMillis()));
					incomingData.setProducer(new Producers(config.getProducerId()));

					for (DataAttachments _DATT : incomingData.getAttachmentList())
					{
						_DATT.setDataURN(coreBase.getDataURN());
					}
					incomingDao.save(incomingData);

					for (DataAttachments _DATT : incomingData.getAttachmentList())
					{
						DataInTopicBean inBean = _DATT.constructDataInTopicBean();
						IKAFKAPartition ePartition = PartitionFinder.getInstance().find(ETopic.DataExtract, incomingData.acquirePriority(), inBean.getExtension());

						gKafkaProducer.send(ETopic.DataExtract, ePartition, inBean);
					}
				}
				logger.info(incomingData.getAttachmentList().toString());

			}

		}
		catch (Exception excep)
		{
			excep.printStackTrace();
		}
		finally
		{
			PersistantStoreHandler.getInstance().removeStore(this);
		}
	}

	private IncomingData processMultiPart(UIDMimeMessageBean uidMsg)
	{
		Set<DataAttachments> attachmentsSet = null;
		FileOutputStream outStream = null;
		try
		{
			BodyPart bodyPart = null;
			IncomingData incomingData = new IncomingData(uidMsg.message);
			attachmentsSet = new HashSet<DataAttachments>();
			Multipart multipart = (Multipart) uidMsg.message.getContent();
			for (int idx = 0; idx < multipart.getCount(); idx++)
			{
				bodyPart = multipart.getBodyPart(idx);

				if (CommonValidator.isNotNullNotEmpty(bodyPart, bodyPart.getFileName()) && CommonValidator.isEqual(bodyPart.getDisposition(), Part.ATTACHMENT))
				{
					if (bodyPart.getSize() > 4113632)
					{
						break;
					}
					System.out.println(">>>>>>>>>>>>> bodyPart.getFileName()>>>>>>>>>>>>>>>>" + bodyPart.getFileName());
					uidMsg.setFileName(bodyPart.getFileName());
					outStream = uidMsg.getOutputStream();

					IOUtils.copy(bodyPart.getInputStream(), outStream);

					if (bodyPart.getInputStream() != null)
						bodyPart.getInputStream().close();

					if (outStream != null)
						outStream.close();

					IncomingDataCreator.getInstance().unpackAndCreateDataAttachmentSet(incomingData, uidMsg, attachmentsSet);
				}

			}

			incomingData.setAttachmentList(attachmentsSet);
			return incomingData;
		}
		catch (MessagingException | IOException | CustomException excep)
		{
			excep.printStackTrace();
		}
		return null;
	}

}
