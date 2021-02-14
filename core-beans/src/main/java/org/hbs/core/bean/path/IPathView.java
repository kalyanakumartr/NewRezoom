package org.hbs.core.bean.path;

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
		deleteNotification(DELETE_NOTIFICATION, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getRecentActivities(GET_RECENT_ACTIVITIES, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getDashboardList(GET_DASHBOARD_LIST, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getDailyReport(GET_DAILY_REPORT, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getWeeklyReport(GET_WEEKLY_REPORT, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getMonthlyReport(GET_MONTHLY_REPORT, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getYearlyReport(GET_YEARLY_REPORT, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getCustomReport(GET_CUSTOM_REPORT, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getNotifications(GET_NOTIFICATIONS, ERole.Administrator, ERole.Supervisor, ERole.User), //
		acknowledgeNotifications(ACK_NOTIFICATIONS, ERole.Administrator, ERole.Supervisor, ERole.User), //
		notAckNotifications(NOT_ACK_NOTIFICATIONS, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getByMessageId(GET_BY_ID, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getByMessageStatus(GET_BY_STATUS, ERole.Administrator, ERole.Supervisor, ERole.User), //
		getAttachmentById(GET_ATTACHMENT, ERole.Administrator, ERole.Supervisor, ERole.User);

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
