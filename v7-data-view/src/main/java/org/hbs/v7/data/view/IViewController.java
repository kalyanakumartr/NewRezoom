package org.hbs.v7.data.view;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public interface IViewController extends IPathView
{
	@PostMapping
	@RequestMapping(value = GET_INCOMING_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getIncomingData(Authentication auth, ViewFormBean vfBean);

	@PostMapping
	@RequestMapping(value = GET_OPERATIONAL_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOperationalData(Authentication auth, ViewFormBean vfBean);

	@PostMapping
	@RequestMapping(value = GET_TASK, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOperationalTask(Authentication auth, ViewFormBean vfBean);

	@PostMapping
	@RequestMapping(value = UPDATE_TASK_STATUS, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTaskStatus(Authentication auth, ViewFormBean vfBean);

	@PostMapping
	@RequestMapping(value = VIEW_TASK_AUDIT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewTaskAudit(Authentication auth, ViewFormBean vfBean);

	@PostMapping
	@RequestMapping(value = VIEW_GROUP_WISE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewProgressGroup(Authentication auth, ViewFormBean vfBean);

	@PostMapping
	@RequestMapping(value = VIEW_DEPLOY_WISE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> viewProgressDeploy(Authentication auth, ViewFormBean vfBean);

	@PostMapping
	@RequestMapping(value = GET_ROAD_TYPE_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRoadTypeList(Authentication auth);

	@PostMapping
	@RequestMapping(value = GET_STATUS_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStatusList(Authentication auth);

	@PostMapping
	@RequestMapping(value = GET_REASON_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getReasonList(Authentication auth, @PathVariable("statusId") String statusId);

	@PostMapping
	@RequestMapping(value = GET_RESOURCE_GROUP_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getGroupList();

	@PostMapping
	@RequestMapping(value = GET_RESOURCE_DEPLOYMENT_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDeploymentsList();

}