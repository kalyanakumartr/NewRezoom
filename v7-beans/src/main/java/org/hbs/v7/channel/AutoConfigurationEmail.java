package org.hbs.v7.channel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hbs.core.beans.model.channel.ConfigurationEmail;
import org.hbs.core.util.EnumInterface;
import org.hbs.core.util.LabelValueBean;

public class AutoConfigurationEmail extends ConfigurationEmail 
{
	private static final long serialVersionUID = 8643466058697591280L;

	public enum EReadBy implements EnumInterface
	{
		_1_Day(24 * 60), //
		_1_Hour(60), //
		_12_Hours(12 * 60), //
		_120_Days(120 * 24 * 60), //
		_15_Minutes(15), //
		_180_Days(180 * 24 * 60), //s
		_2_Hours(2 * 60), //
		_270_Days(270 * 24 * 60), //
		_3_Days(3 * 24 * 60), //
		_30_Days(30 * 24 * 60), //
		_30_Minutes(30), //
		_365_Days(365 * 24 * 60), //
		_4_Hours(4 * 60), //
		_5_Minutes(5), //
		_6_Hours(6 * 60), //
		_60_Days(60 * 24 * 60), //
		_7_Days(7 * 24 * 60), //
		_90_Days(90 * 24 * 60);

		public static List<LabelValueBean> getReadyByList()
		{
			List<LabelValueBean> lvList = new ArrayList<LabelValueBean>();

			for (EReadBy readBy : EReadBy.values())
			{
				lvList.add(new LabelValueBean(readBy.name(), readBy.name().replaceAll("_", " ").trim()));
			}
			return lvList;
		}

		private float dateTime = 0;

		EReadBy(float day)
		{
			this.dateTime = day;
		}

		public long getDateTime()
		{
			return (long) ((this.dateTime + 1) * 60 * 1000); // AnyTime +1 Minute
		}

		public Date getForwardDate(Date dateTime)
		{
			return new Date((long) (dateTime.getTime() + (this.dateTime * 60 * 1000)));
		}

		public Date getReversedDate(Date dateTime)
		{
			return new Date((long) (dateTime.getTime() - (this.dateTime * 60 * 1000)));
		}

		public boolean isReached(Date initDateTime, Date lastProcessedMessageTime)
		{
			return (initDateTime.getTime() - (this.dateTime * 60 * 1000)) < lastProcessedMessageTime.getTime();
		}
	}

	public EReadBy				readEvery				= EReadBy._5_Minutes;
	public boolean				reverseStart			= false;
	public EReadBy				startDate				= EReadBy._3_Days;

	public EReadBy getReadEvery()
	{
		return readEvery;
	}

	public EReadBy getStartDate()
	{
		return startDate;
	}

	public boolean isReverseStart()
	{
		return reverseStart;
	}

	public void setReadEvery(EReadBy readEvery)
	{
		this.readEvery = readEvery;
	}

	public void setReverseStart(boolean reverseStart)
	{
		this.reverseStart = reverseStart;
	}

	public void setStartDate(EReadBy startDate)
	{
		this.startDate = startDate;
	}
	

}
