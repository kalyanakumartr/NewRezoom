package org.hbs.core.security.resource;

import org.hbs.core.kafka.IKAFKAPartition;
import org.hbs.core.util.EnumInterface;
import org.hbs.core.util.IConstProperty;

public interface IPathBase extends IConstProperty
{
	public enum EFormAction implements EnumInterface
	{
		Default, Add, Update, Search, SoftDelete, PermanentDelete, ChangePassword, ForgotPassword, Verify, TokenExpired, OTP_Generate
	}

	public enum EMedia implements EnumInterface, IKAFKAPartition
	{
		Email, SMS, WhatsApp, Manual, WebUpload;

		@Override
		public int getPartition()
		{
			return this.ordinal();
		}
	}

	public enum EMediaMode implements EnumInterface
	{
		NoReply, Internal, External;
	}

	public enum EMediaType implements EnumInterface
	{
		Primary, Secondary, Alternate
	}

	public enum EReturn implements EnumInterface
	{
		Success, Failure, Exists, Not_Exists
	}

	public enum ERole implements IERole
	{
		Administrator, Consumer, Dummy, Employee, Producer, SuperAdminRole;
	}

}