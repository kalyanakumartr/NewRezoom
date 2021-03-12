package org.hbs.v7.data.view;

import java.io.Serializable;

import org.hbs.core.security.resource.EnumInterfaceRolePath;
import org.hbs.core.security.resource.IERole;

public interface IPathView extends Serializable
{
	// Road Data
	public String	GET_INCOMING_DATA				= "/getIncomingData";
	public String	GET_OPERATIONAL_DATA			= "/getOperationalData";
	public String	GET_TASK						= "/getTask/{taskType}";
	public String	UPDATE_TASK_STATUS				= "/updateTaskStatus";
	public String	VIEW_TASK_AUDIT					= "/viewTaskAudit/{fromDateTime}/{toDateTime}";
	public String	VIEW_GROUP_WISE					= "/viewProgressGroup";
	public String	VIEW_DEPLOY_WISE				= "/viewProgressDeploy";
	public String	GET_ROAD_TYPE_LIST				= "/getRoadTypeList";
	public String	GET_STATUS_LIST					= "/getStatusList/**";
	public String	GET_REASON_LIST					= "/getReasonList/{statusId}";
	public String	GET_RESOURCE_GROUP_LIST			= "/getGroupList/**";
	public String	GET_RESOURCE_DEPLOYMENT_LIST	= "/getDeploymentsList/**";
	public String	INVALID_REQUEST_PARAMETERS		= "invalid.request.parameters";

	public enum EVRole implements IERole
	{
		ProjectManager, ProjectLead, TeamLead, Production, QualityCheck, Trainee, Training;

	}

	public enum EPathView implements EnumInterfaceRolePath
	{
		// Operational Data
		GetIncomingData(GET_INCOMING_DATA), // , EVRole.ProjectManager, EVRole.ProjectLead
		GetRoadData(GET_OPERATIONAL_DATA), //, EVRole.ProjectManager, EVRole.ProjectLead, EVRole.TeamLead, EVRole.Production, EVRole.QualityCheck, EVRole.Trainee, EVRole.Training
		GetTask(GET_TASK, EVRole.ProjectManager, EVRole.ProjectLead, EVRole.TeamLead, EVRole.Production, EVRole.QualityCheck, EVRole.Trainee, EVRole.Training), //
		UpdateTaskStatus(UPDATE_TASK_STATUS, EVRole.ProjectManager, EVRole.ProjectLead, EVRole.TeamLead, EVRole.Production, EVRole.QualityCheck, EVRole.Trainee, EVRole.Training), //
		ViewTaskAudit(VIEW_TASK_AUDIT, EVRole.ProjectManager, EVRole.ProjectLead, EVRole.TeamLead), //
		ViewGroupPerformance(VIEW_GROUP_WISE, EVRole.ProjectManager, EVRole.ProjectLead, EVRole.TeamLead), //
		ViewDeployPerformance(VIEW_DEPLOY_WISE, EVRole.ProjectManager, EVRole.ProjectLead, EVRole.TeamLead); //

		EPathView(String path, IERole... roles)
		{
			this.path = path;
			this.roles = roles;
		}

		String	path;

		IERole	roles[];

		@Override
		public String getPath()
		{
			return this.path;
		}

		@Override
		public IERole[] getRoles()
		{
			return this.roles;
		}
	}

}
