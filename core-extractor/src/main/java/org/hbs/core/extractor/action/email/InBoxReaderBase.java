package org.hbs.core.extractor.action.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;

import org.hbs.core.beans.model.channel.ConfigurationEmail;
import org.hbs.core.extractor.action.core.InBoxReader;
import org.hbs.core.kafka.GenericKafkaProducer;
import org.hbs.core.kafka.IKafkaConstants.EPartition;
import org.hbs.core.kafka.IKafkaConstants.ETopic;
import org.hbs.core.kafka.KafkaEmailReferenceBean;
import org.hbs.core.security.resource.IPathBase.EMedia;
import org.hbs.core.util.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.google.gson.Gson;

@ComponentScan({ "org.hbs.core.event.service" })
public abstract class InBoxReaderBase implements InBoxReader {
	private static final long serialVersionUID = 2428143934833300387L;

	@Autowired
	GenericKafkaProducer gKafkaProducer;

	ConfigurationEmail config;

	@Autowired
	KafkaEmailReferenceBean kafkaEmailRef;

	public InBoxReaderBase() {
		super();
	}

	protected Store authenticateMailAndConnect(ConfigurationEmail config)
			throws NoSuchProviderException, MessagingException {
		this.config = config;
		try {
			Store store = EmailConnectionHandler.getInstance().getStore(config.getProducerId() + config.getFromId());
			if (store != null) {
				return store;
			} else {
				Properties props = new Properties();
				Map<String, String[]> map = config.getSource().props();
				props.setProperty("mail.host", config.getHostAddress());
				props.setProperty("mail.port", config.getPort());
				props.setProperty("mail.store.protocol", config.getProtocol());
				/* Get the Session object for specific Mail Property. */
				Session session = Session.getInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(config.getUserName().trim(), config.getPassword());
					}
				});
				/* Get a Store object that implements the specified protocol. */
				store = session.getStore(config.getProtocol().trim());
				/*
				 * Connect to the current host using the specified Email id and Password.
				 */
				store.connect();
				Folder[] folderArr = store.getDefaultFolder().list();
				for (Folder folder : folderArr) {
					System.out.println(config.getFromId().trim() + " Inbox Connected :: Unread Message Count :: "
							+ folder + " ---- " + folder.getUnreadMessageCount());
					if (folder.getName().equalsIgnoreCase("inbox")) {
						break;
					}
				}
				EmailConnectionHandler.getInstance().putStore(config.getProducerId() + config.getFromId(), store);
				return EmailConnectionHandler.getInstance().getStore(config.getProducerId() + config.getFromId());
			}
		} finally {
		}
	}

	protected void pushToQueue(String producerId, UIDFolder _UIDFolder, Message... messages) throws Exception {

		List<String> errorList = new ArrayList<String>(0);
		KafkaEmailReferenceBean kafkaEmailRef = null;
		for (Message message : messages) {
			try {
				kafkaEmailRef = new KafkaEmailReferenceBean(message.getMessageNumber(), message.getSentDate(), config);

				gKafkaProducer.send(ETopic.DataExtract, EPartition.Message_In, EMedia.Email,
						new Gson().toJson(kafkaEmailRef));
			} catch (Exception ex) {
				errorList.add(message.getMessageNumber() + "");
			}
		}
		
		if(CommonValidator.isListFirstNotEmpty(errorList))
			throw new Exception(errorList.toString());
	}

}