package org.hbs.sender;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.hbs.core.bean.ConfigurationFormBean;
import org.hbs.core.bean.MessageFormBean;
import org.hbs.core.bean.OTPFormBean;
import org.hbs.core.bean.UserFormBean;
import org.hbs.core.bean.model.IConfiguration;
import org.hbs.core.bean.model.IUsers.EUsers;
import org.hbs.core.bean.model.Messages;
import org.hbs.core.bean.model.MessagesBase;
import org.hbs.core.bean.model.channel.ChannelMessages;
import org.hbs.core.bean.model.channel.ConfigurationEmail;
import org.hbs.core.bean.model.channel.IChannelMessages;
import org.hbs.core.bean.model.clickatell.SMSCallBackFormBean;
import org.hbs.core.util.CommonValidator;
import org.hbs.core.util.CustomException;
import org.hbs.sender.bo.ConfigurationBo;
import org.hbs.sender.bo.EmailSenderBo;
import org.hbs.sender.bo.SMSSenderBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

@RestController
public class MessageSenderController implements IMessageSenderController
{
	private static final long	serialVersionUID	= -3967646593883532521L;

	private final Logger		logger				= LoggerFactory.getLogger(MessageSenderController.class);

	@Autowired
	ConfigurationBo				configurationBo;

	@Autowired
	EmailSenderBo				emailSenderBo;

	@Autowired
	SMSSenderBo					smsSenderBo;

	public ResponseEntity<?> createUserEmail(Authentication auth, @RequestHeader(value = "Authorization") String token, @RequestBody UserFormBean userFormBean)
	{
		try
		{
			Messages message = new Messages((EAuth.User.getProducerId(auth) == EUsers.SuperAdmin.name()) ? ETemplate.User_Create_Admin : ETemplate.User_Create_Employee);
			// message.setEmailId(userFormBean.user.getEmailId());
			// message.putFormInDataMap(userFormBean);

			return new ResponseEntity<>(emailSenderBo.sendEmailByMessage(auth, token, message), HttpStatus.OK);
		}
		catch (Exception excep)
		{
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public ResponseEntity<?> downloadAttachment(Authentication auth, Long attachmentId, HttpServletResponse response)
	{
		// Not Yet Implemented
		return new ResponseEntity<>(EMessageStatus.Error.name(), HttpStatus.ACCEPTED);
	}

	public ResponseEntity<?> passwordResetMail(Authentication auth, @RequestHeader(value = "Authorization") String token, @RequestBody UserFormBean userFormBean)
	{
		try
		{
			Messages message = new Messages(ETemplate.User_Reset_Password);
			// message.setEmailId(userFormBean.user.getEmailId());
			// message.putFormInDataMap(userFormBean);

			return new ResponseEntity<>(emailSenderBo.sendEmailByMessage(auth, token, message), HttpStatus.OK);
		}
		catch (Exception excep)
		{
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<?> saveMessageAndSendToMedia(Authentication auth, @RequestHeader(value = "Authorization") String token, @RequestParam("bean") String formBean,
			@RequestParam("file") MultipartFile[] multipartFiles)
	{
		try
		{
			MessageFormBean messageFormBean = new Gson().fromJson(formBean, MessageFormBean.class);
			messageFormBean.multipartFiles = multipartFiles;
			messageFormBean.producerId = EAuth.User.getProducerId(auth);// Mandatory

			switch ( EMedia.valueOf(messageFormBean.messageType) )
			{
				case Email :
				{
					IConfiguration configuration = configurationBo.getConfigurationByType(auth, EMedia.Email, EMediaType.Primary, EMediaMode.Internal);
					messageFormBean.constructBaseMessage(auth, configuration);
					return new ResponseEntity<>(emailSenderBo.sendEmailToUserOrGroup(auth, token, messageFormBean).name(), HttpStatus.OK);
				}
				case SMS :
				{
					IConfiguration configuration = configurationBo.getConfigurationByType(auth, EMedia.SMS, EMediaType.Primary, EMediaMode.Internal);
					messageFormBean.constructBaseMessage(auth, configuration);
					return new ResponseEntity<>(smsSenderBo.sendSMSToUserOrGroup(auth, token, messageFormBean).name(), HttpStatus.OK);
				}
				default :
					break;
			}
		}
		catch (Exception excep)
		{
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>("", HttpStatus.EXPECTATION_FAILED);
	}

	public ResponseEntity<?> sendEmail(Authentication auth, @RequestHeader(value = "Authorization") String token, Messages message)
	{
		try
		{
			message.setMessageId(null); // Reset for User Defined Messages
			return new ResponseEntity<>(emailSenderBo.sendEmailByMessage(auth, token, message).name(), HttpStatus.OK);
		}
		catch (Exception excep)
		{
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}

	}

	public ResponseEntity<?> sendSMS(Authentication auth, @RequestBody IChannelMessages message)
	{
		try
		{
			return new ResponseEntity<>(smsSenderBo.sendSMSByMessage(auth, message), HttpStatus.OK);
		}
		catch (Exception excep)
		{
			excep.printStackTrace();
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}

	}

	public ResponseEntity<?> sendSMS_OTP(Authentication auth, @RequestBody OTPFormBean otpFormBean)
	{
		try
		{
			IConfiguration configuration = configurationBo.getConfigurationByType(otpFormBean.user.getProducer().getProducerId(), EMedia.SMS, EMediaType.Primary, EMediaMode.Internal);

			if (CommonValidator.isNotNullNotEmpty(configuration))
			{

				IChannelMessages message = new ChannelMessages(ETemplate.SMS_OTP);
				message.setRecipients(otpFormBean.user.getMediaToDisplay(EMediaType.Primary).getMobileNo());
				message.setProducer(otpFormBean.user.getProducer());
				message.setConfiguration(configuration);

				// VTL Messages Data
				// message.putFormInDataMap(otpFormBean);

				return new ResponseEntity<>(smsSenderBo.sendSMSByMessage(auth, message), HttpStatus.OK);
			}
			throw new CustomException("IConfiguration NOT found for the given Producer Name : " + EAuth.User.getProducerName(auth));
		}
		catch (Exception excep)
		{
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@Override
	public ResponseEntity<?> sendUserBlockMail(Authentication auth, @RequestHeader(value = "Authorization") String token, @RequestBody UserFormBean userFormBean)
	{
		try
		{
			ConfigurationEmail configuration = (ConfigurationEmail) configurationBo.getConfigurationByType(auth, EMedia.Email, EMediaType.Primary, EMediaMode.Internal);

			Messages message = new Messages(ETemplate.User_Blocked_Admin);
			message.setRecipients(configuration.getFromId());
			message.setConfiguration(configuration);
			// message.putFormInDataMap(userFormBean);

			return new ResponseEntity<>(emailSenderBo.sendEmailByMessage(message), HttpStatus.OK);
		}
		catch (Exception excep)
		{
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}
	}

	public ResponseEntity<?> smsCallBackStatus(@RequestBody SMSCallBackFormBean callBackFormBean)
	{
		try
		{
			if (CommonValidator.isNotNullNotEmpty(callBackFormBean))
			{
				smsSenderBo.updateSMSStatus(callBackFormBean);
			}
		}
		catch (Exception excep)
		{
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}
		return null;
	}

	public ResponseEntity<?> testConfiguration(Authentication auth, @RequestBody ConfigurationFormBean cfgFormBean)
	{
		try
		{
			switch ( cfgFormBean.producerProperty.getMedia() )
			{
				case Email :
				{
					IChannelMessages message = new ChannelMessages(auth, ETemplate.Test_Email_Connection);
					message.setConfiguration(cfgFormBean.configuration);
					message.setRecipients(cfgFormBean.to);
					message.setMedia(EMedia.Email);
					// message.putFormInDataMap(cfgFormBean);

					return new ResponseEntity<>(emailSenderBo.sendEmailByMessage(message), HttpStatus.OK);
				}
				case SMS :
				{
					IChannelMessages message = new ChannelMessages(ETemplate.Test_SMS_Connection);
					message.setConfiguration(cfgFormBean.configuration);
					message.setRecipients(cfgFormBean.to);
					message.setMedia(EMedia.SMS);
					// message.putFormInDataMap(cfgFormBean);
					EMessageStatus status = smsSenderBo.sendSMSByMessage(auth, message).status;
					return new ResponseEntity<>(status.name(), HttpStatus.OK);
				}
				case WhatsApp :
				{
					MessagesBase message = new Messages(ETemplate.Test_WhatsApp_Connection);
					message.setConfiguration(cfgFormBean.configuration);
					message.setRecipients(cfgFormBean.to);
					message.setMedia(EMedia.WhatsApp);
					// message.putFormInDataMap(cfgFormBean);
					// EMessageStatus status = smsSenderBo.sendSMSByTemplate(auth, message).status;
					return new ResponseEntity<>("", HttpStatus.OK);
				}
				default :
					break;
			}

		}
		catch (Exception excep)
		{
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>("Media NOT yet implemented.", HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<?> updateMessageStatus(Authentication auth, @RequestParam("templateId") long messageId, @RequestParam("messageStatus") String messageStatus)
	{
		try
		{
			// emailSenderBo.updateMessageStatus(auth, templateId,
			// EMessageStatus.valueOf(messageStatus));
			// return new ResponseEntity<APIStatus>(createResponseBean("SUCCESS"), HttpStatus.OK);
		}
		catch (Exception excep)
		{
			StringWriter logMessageWriter = new StringWriter();
			excep.printStackTrace(new PrintWriter(logMessageWriter));
			logger.error(logMessageWriter.toString());
			return new ResponseEntity<>(logMessageWriter, HttpStatus.EXPECTATION_FAILED);
		}
		return null;

	}

}
