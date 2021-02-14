package org.hbs.core.admin;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.hbs.core.security.resource.IPath;
import org.hbs.core.security.resource.IPath.EReturn;
import org.hbs.core.util.CommonValidator;
import org.hbs.core.util.EnumInterface;
import org.hbs.core.util.IConstProperty.EWrap;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Component
public class OTPService implements IPath
{

	private static final long				serialVersionUID	= 6829306673482521900L;
	private static final String				ALPHAS				= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final Integer			EXPIRE_MINS			= 2;
	private static final String				NUMBERS				= "0123456789";
	private LoadingCache<String, String>	otpCache;

	public OTPService()
	{
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
			@Override
			public String load(String key) throws Exception
			{
				return null;
			}
		});
	}

	public void clear(String key)
	{
		otpCache.invalidate(key);
	}

	public EnumInterface validate(Object _ALPHA_PREFIX, Object _Key, Object _OTP)
	{
		try
		{
			String otpKey = ewrapOtpValues(_ALPHA_PREFIX, _Key), otpValue = ewrapOtpValues(_ALPHA_PREFIX, _OTP);
			// if (CommonValidator.isEqual(otpCache.get(otpKey), otpValue)) // Need to fix OTP Cache
			// Issue
			if (CommonValidator.isEqual(otpKey, otpValue))
			{
				clear(otpKey);
				return EReturn.Success;
			}
		}
		catch (Exception excep)
		{
		}
		return EReturn.Failure;
	}

	public String[] generate(Object _SUFFIX_AS_ID, int length)
	{
		Random random = new Random();

		char[] _PREFIXs = new char[3];

		for (int i = 0; i < 3; i++)
		{
			_PREFIXs[i] = ALPHAS.charAt(random.nextInt(26));
		}
		String _ALPHA_PREFIX = String.valueOf(_PREFIXs);

		char[] _OTPs = new char[length];

		for (int i = 0; i < length; i++)
		{
			_OTPs[i] = NUMBERS.charAt(random.nextInt(length));
		}
		String _OTP = String.valueOf(_OTPs);

		String _OTPKey = ewrapOtpValues(_ALPHA_PREFIX, _SUFFIX_AS_ID);
		String _OTPValue = ewrapOtpValues(_ALPHA_PREFIX, _OTP);

		otpCache.put(_OTPKey, _OTPValue);

		return new String[] { _ALPHA_PREFIX, _OTP };
	}

	private String ewrapOtpValues(Object value1, Object value2)
	{
		return EWrap.Hyphen.append(new Object[] { value1, value2 });

	}
}