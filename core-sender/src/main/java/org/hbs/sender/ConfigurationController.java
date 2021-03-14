package org.hbs.sender;

import org.hbs.core.beans.ConfigurationFormBean;
import org.hbs.core.beans.model.ProducersProperty;
import org.hbs.core.util.CommonValidator;
import org.hbs.sender.bo.ConfigurationBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController implements IConfigurationController
{
	private static final long	serialVersionUID	= -3967646593883532521L;

	@Autowired
	ConfigurationBo				configurationBo;

	private final Logger		logger				= LoggerFactory.getLogger(ConfigurationController.class);

	@Override
	public ResponseEntity<?> addConfiguration(Authentication auth, @RequestBody ConfigurationFormBean cfBean)
	{
		try
		{
			logger.info("ConfigurationController addConfiguration starts ::: ");
			configurationBo.saveConfiguration(auth, cfBean);
			logger.info("addConfiguration ends ::: ");
			return new ResponseEntity<>(cfBean, HttpStatus.OK);
		}
		catch (Exception excep)
		{
			cfBean.clearForm();
			cfBean.messageCode = excep.getMessage();
			logger.error("Exception in ConfigurationController addConfiguration ::: ", excep);
			return new ResponseEntity<>(cfBean, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> blockConfiguration(Authentication auth, @RequestBody ConfigurationFormBean cfBean)
	{
		try
		{
			return new ResponseEntity<>(configurationBo.blockConfiguration(auth, cfBean).name(), HttpStatus.OK);
		}
		catch (InvalidRequestException ex)
		{
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

	}
	
	@Override
	public ResponseEntity<?> checkConfiguration(Authentication auth, @PathVariable("groupName") String groupName)
	{
		try
		{
			return new ResponseEntity<>(configurationBo.checkConfigurationExists(auth, groupName).name(), HttpStatus.OK);
		}
		catch (InvalidRequestException ex)
		{
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

	}

	@Override
	public ResponseEntity<?> deleteConfiguration(Authentication auth, @RequestBody ConfigurationFormBean cfBean)
	{
		try
		{
			return new ResponseEntity<>(configurationBo.deleteConfiguration(auth, cfBean).name(), HttpStatus.OK);
		}
		catch (InvalidRequestException ex)
		{
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>("Configuration NOT available. It may be deleted recently.", HttpStatus.EXPECTATION_FAILED);
		}

	}

	@Override
	public ResponseEntity<?> getConfiguration(Authentication auth, @RequestBody ConfigurationFormBean cfBean)
	{
		try
		{
			logger.info("ConfigurationController getConfiguration starts ::: ");
			if (CommonValidator.isNotNullNotEmpty(cfBean))
			{
				ProducersProperty _PP = configurationBo.getConfigurationByAutoId(auth, cfBean);
				return new ResponseEntity<ProducersProperty>(_PP, HttpStatus.OK);
			}
			throw new InvalidRequestException(INVALID_REQUEST_PARAMETERS);

		}
		catch (Exception excep)
		{
			cfBean.clearForm();
			cfBean.messageCode = excep.getMessage();
			logger.error("Exception in ConfigurationController getConfiguration ::: ", excep);
			return new ResponseEntity<>(cfBean, HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<?> searchConfiguration(Authentication auth, @RequestBody ConfigurationFormBean cfBean)
	{
		try
		{
			return new ResponseEntity<>(configurationBo.getConfigurationList(auth, cfBean), HttpStatus.OK);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}

	}

	@Override
	public ResponseEntity<?> updateConfiguration(Authentication auth, @RequestBody ConfigurationFormBean cfBean)
	{
		try
		{
			logger.info("Inside ConfigurationController updateConfiguration ::: ");
			if (CommonValidator.isNotNullNotEmpty(cfBean, cfBean.producerProperty))
			{
				configurationBo.updateConfiguration(auth, cfBean);
				return new ResponseEntity<>(cfBean, HttpStatus.OK);
			}
			throw new InvalidRequestException(INVALID_REQUEST_PARAMETERS);

		}
		catch (Exception excep)
		{
			cfBean.clearForm();
			cfBean.messageCode = excep.getMessage();
			logger.error("Exception in ConfigurationController updateConfiguration ::: ", excep);
			return new ResponseEntity<>(cfBean, HttpStatus.BAD_REQUEST);
		}
	}
}
