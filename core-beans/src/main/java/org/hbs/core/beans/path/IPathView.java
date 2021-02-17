package org.hbs.core.beans.path;

import org.hbs.core.security.resource.EnumResourceInterface;
import org.hbs.core.security.resource.IPath;

public interface IPathView extends IPath
{

	public String	GET_RECENT_ACTIVITIES	= "/getRecentActivities";
	public String	GET_DASHBOARD_LIST		= "/getDashboardList";
	public String	GET_DAILY_REPORT		= "/getDailyReport";
	public String	GET_WEEKLY_REPORT		= "/getWeeklyReport";
	public String	GET_MONTHLY_REPORT		= "/getMonthlyReport";
	public String	GET_YEARLY_REPORT		= "/getYearlyReport";
	public String	GET_CUSTOM_REPORT		= "/getCustomReport";
	public String	GET_NOTIFICATIONS		= "/getNotifications";
	public String	ACK_NOTIFICATIONS		= "/acknowledgeNotification";
	public String	NOT_ACK_NOTIFICATIONS	= "/notAcknowledgeNotification";
	public String	DELETE_NOTIFICATION		= "/deleteNotification";
	public String	GET_BY_ID				= "/getMessageById";
	public String	GET_BY_STATUS			= "/getMessageByStatus";
	public String	GET_ATTACHMENT			= "/getAttachmentById";

	public enum EPathView implements EnumResourceInterface
	{
		deleteNotification(DELETE_NOTIFICATION, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getRecentActivities(GET_RECENT_ACTIVITIES, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getDashboardList(GET_DASHBOARD_LIST, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getDailyReport(GET_DAILY_REPORT, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getWeeklyReport(GET_WEEKLY_REPORT, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getMonthlyReport(GET_MONTHLY_REPORT, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getYearlyReport(GET_YEARLY_REPORT, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getCustomReport(GET_CUSTOM_REPORT, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getNotifications(GET_NOTIFICATIONS, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		acknowledgeNotifications(ACK_NOTIFICATIONS, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		notAckNotifications(NOT_ACK_NOTIFICATIONS, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getByMessageId(GET_BY_ID, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getByMessageStatus(GET_BY_STATUS, ERole.Administrator, ERole.Administrator, ERole.Employee), //
		getAttachmentById(GET_ATTACHMENT, ERole.Administrator, ERole.Administrator, ERole.Employee);

		String	path;

		ERole	roles[];

		EPathView(String path, ERole... roles)
		{
			this.path = path;
			this.roles = roles;
		}

		public String getPath()
		{
			return this.path;
		}

		public ERole[] getRoles()
		{
			return this.roles;
		}

	}
}
