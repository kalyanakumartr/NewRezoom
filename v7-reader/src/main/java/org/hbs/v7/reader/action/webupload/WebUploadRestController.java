package org.hbs.v7.reader.action.webupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.hbs.core.beans.model.IConfiguration;
import org.hbs.core.beans.model.Producers;
import org.hbs.core.kafka.GenericKafkaProducer;
import org.hbs.core.kafka.IKAFKAPartition;
import org.hbs.core.util.CommonValidator;
import org.hbs.core.util.CustomException;
import org.hbs.v7.beans.DataInTopicBean;
import org.hbs.v7.beans.model.DataAttachments;
import org.hbs.v7.beans.model.ICoreBase;
import org.hbs.v7.beans.model.IncomingData;
import org.hbs.v7.beans.model.IncomingData.EIncomingStatus;
import org.hbs.v7.dao.IncomingDao;
import org.hbs.v7.reader.action.core.IncomingDataCreator;
import org.hbs.v7.reader.bo.ExtractorBo;
import org.hbs.v7.userdefined.dao.CoreBaseDao;
import org.hbs.v7.userdefined.model.CoreBaseFactory;
import org.hbs.v7.util.IKafkaTopicConstants.ETopic;
import org.hbs.v7.util.PartitionFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class WebUploadRestController implements IWebUploadRestController
{
	private static final long	serialVersionUID			= 7440761569052436826L;

	private final Logger		logger						= LoggerFactory.getLogger(WebUploadRestController.class);

	public String				INVALID_REQUEST_PARAMETERS	= "invalid.request.parameters";

	@Autowired
	GenericKafkaProducer		gKafkaProducer;

	@Autowired
	ExtractorBo					extractorBo;

	@Autowired
	private IncomingDao			incomingDao;

	@Autowired
	private CoreBaseDao			coreBaseDao;

	@Override
	public ResponseEntity<?> processUpload(Authentication auth, @PathVariable("uploadFileType") String uploadFileType, @RequestParam("files[]") MultipartFile[] files)
	{
		WebUploadBean wuBean = null;
		try
		{
			List<IConfiguration> configList = extractorBo.getConfigurationList(EAuth.User.getProducerId(auth), EMedia.WebUpload, EMediaMode.External);
			if (CommonValidator.isListFirstNotEmpty(configList))
			{
				wuBean = new WebUploadBean(configList.iterator().next());

				// Producer Id will be the first SubFolder
				wuBean.appendSubFolderPath(EAuth.User.getUserId(auth));
				wuBean.appendSubFolderPath(EDate.YYYYMMDD.formatted(new Date()));
				wuBean.appendSubFolderPath(EDate.HHMM.formatted(new Date()));

				IncomingData incomingData = processMultiPart(wuBean, files);

				if (CommonValidator.isNotNullNotEmpty(incomingData))
				{
					ICoreBase coreBase = CoreBaseFactory.getInstance(wuBean.producerId);
					coreBaseDao.save(coreBase);

					incomingData.setCandidateEmail(EAuth.User.getUserId(auth));
					incomingData.setMedia(EMedia.WebUpload);
					incomingData.setIncomingStatus(EIncomingStatus.New);
					incomingData.setSentTime(new Date().getTime());
					incomingData.setReaderInstance(this.getClass().getSimpleName());
					incomingData.setCreatedDate(new Timestamp(System.currentTimeMillis()));
					incomingData.setProducer(new Producers(wuBean.producerId));

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
				return new ResponseEntity<>(EReturn.Success, HttpStatus.OK);
			}
			throw new InvalidRequestException(INVALID_REQUEST_PARAMETERS);
		}
		catch (Exception excep)
		{
			return new ResponseEntity<>(wuBean == null ? EReturn.Failure : wuBean.failureList, HttpStatus.BAD_REQUEST);
		}
	}

	private IncomingData processMultiPart(WebUploadBean wuBean, MultipartFile[] files)
	{
		try
		{
			File folder = new File(wuBean.getOutputPath());

			if (!folder.exists())
				folder.mkdirs();

			IncomingData incomingData = new IncomingData();
			Set<DataAttachments> attachmentsSet = new HashSet<DataAttachments>();
			for (MultipartFile multiFile : files)
			{
				try
				{

					InputStream is = multiFile.getInputStream();
					Files.copy(is, Paths.get(wuBean.getOutputPath(), multiFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

					IncomingDataCreator.getInstance().unpackAndCreateDataAttachmentSet(incomingData, wuBean, attachmentsSet);
				}
				catch (IOException e)
				{
					e.printStackTrace();
					wuBean.failureList.add(multiFile.getOriginalFilename());
				}
			}
			incomingData.setAttachmentList(attachmentsSet);

			return incomingData;
		}
		catch (MessagingException | CustomException excep)
		{
			excep.printStackTrace();
		}
		return null;
	}
}