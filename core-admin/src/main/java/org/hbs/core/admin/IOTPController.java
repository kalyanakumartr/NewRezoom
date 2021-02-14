package org.hbs.core.admin;

import org.hbs.core.bean.OTPFormBean;
import org.hbs.core.bean.path.IPathAdmin;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IOTPController extends IPathAdmin
{
	@PostMapping
	@RequestMapping(value = GENERATE_OTP, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> generateOTP(@RequestBody OTPFormBean otpForm);

	@PostMapping
	@RequestMapping(value = VALIDATE_OTP, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> validateOTP(@RequestBody OTPFormBean otpForm);

}