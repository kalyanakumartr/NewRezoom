package org.hbs.core.admin;

import org.hbs.core.bean.PasswordFormBean;
import org.hbs.core.bean.UserFormBean;
import org.hbs.core.bean.path.IPathAdmin;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IPasswordController extends IPathAdmin
{
	@PostMapping
	@RequestMapping(value = CHANGE_PASSWORD)
	ResponseEntity<?> changePassword(PasswordFormBean password);

	@PostMapping
	@RequestMapping(value = FORGOT_PASSWORD)
	ResponseEntity<?> forgotPassword(UserFormBean user);

	@PostMapping
	@RequestMapping(value = RESET_PASSWORD, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> resetPassword(PasswordFormBean pfBean);

}