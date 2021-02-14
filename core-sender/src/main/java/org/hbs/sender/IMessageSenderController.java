package org.hbs.sender;

import javax.servlet.http.HttpServletResponse;

import org.hbs.core.bean.ConfigurationFormBean;
import org.hbs.core.bean.OTPFormBean;
import org.hbs.core.bean.UserFormBean;
import org.hbs.core.bean.model.Messages;
import org.hbs.core.bean.model.channel.IChannelMessages;
import org.hbs.core.bean.model.clickatell.SMSCallBackFormBean;
import org.hbs.core.bean.path.IPathSender;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

public interface IMessageSenderController extends IPathSender
{
	@PostMapping
	@RequestMapping(value = CREATE_USER_MAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(HAS_ALL_AUTHORITY)
	public ResponseEntity<?> createUserEmail(Authentication auth, String token, UserFormBean userFormBean);

	@GetMapping
	@RequestMapping(value = DOWNLOAD_ATTACHMENT, produces = MEDIA_TYPE_ZIP)
	@PreAuthorize(HAS_ALL_AUTHORITY)
	public ResponseEntity<?> downloadAttachment(Authentication auth, Long attachmentId, HttpServletResponse response);

	@PostMapping
	@RequestMapping(value = PASSWORD_RESET_MAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> passwordResetMail(Authentication auth, String token, UserFormBean userFormBean);

	@PostMapping
	@RequestMapping(value = SAVE_MESSAGE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(HAS_ALL_AUTHORITY)
	public ResponseEntity<?> saveMessageAndSendToMedia(Authentication auth, String token, String formBean, MultipartFile[] files);

	@PostMapping
	@RequestMapping(value = SEND_MAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(HAS_ALL_AUTHORITY)
	public ResponseEntity<?> sendEmail(Authentication auth, String token, Messages message);

	@PostMapping
	@RequestMapping(value = SEND_SMS, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(HAS_ALL_AUTHORITY)
	public ResponseEntity<?> sendSMS(Authentication auth, IChannelMessages message);

	@PostMapping
	@RequestMapping(value = SEND_SMS_OTP, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendSMS_OTP(Authentication auth, OTPFormBean otpForm);

	@PostMapping
	@RequestMapping(value = SEND_USER_BLOCK_MAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendUserBlockMail(Authentication auth, String token, UserFormBean userFormBean);

	@PostMapping
	@RequestMapping(value = SMS_STATUS_CALLBACK, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> smsCallBackStatus(SMSCallBackFormBean callBackFormBean);

	@PostMapping
	@RequestMapping(value = TEST_CONFIGURATION, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(HAS_AUTHORITY_ADMINISTRATOR)
	ResponseEntity<?> testConfiguration(Authentication auth, ConfigurationFormBean cfgFormBean);

	@PostMapping
	@RequestMapping(value = ACKNOWLEDGE_MESSAGE_STATUS, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize(HAS_ALL_AUTHORITY)
	public ResponseEntity<?> updateMessageStatus(Authentication auth, long messageId, String messageStatus);

}