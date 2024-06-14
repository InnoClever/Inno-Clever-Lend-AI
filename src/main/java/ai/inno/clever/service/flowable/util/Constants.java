package ai.inno.clever.service.flowable.util;

public class Constants {

    // TODO: 03/11/2020 Move to application yaml. how many years back to look and add the financial info for
    public static int LOOK_YEARS_BACK = 2;

    public static final String PROCESS_INSTANCE_ENDPOINT = "/process-api/runtime/process-instances";
    public static final String PROCESS_DEFINITIONS_ENDPOINT = "/process-api/repository/process-definitions";
    public static final String HISTORIC_PROCESS_INSTANCES = "/process-api/history/historic-process-instances";
    public static final String QUERY_PROCESS_INSTANCES = "/process-api/query/process-instances";
    public static final String QUERY_HISTORIC_PROCESS_INSTANCES = "/process-api/query/historic-process-instances";

    public static final String TASK_ENDPOINT = "/process-api/runtime/tasks";
    public static final String HISTORIC_TASKS = "/process-api/history/historic-task-instances";
    public static final String QUERY_TASKS = "/process-api/query/tasks";
    public static final String QUERY_HISTORIC_TASKS = "/process-api/query/historic-task-instances";

    public static final String CONTENT_SERVICE_ENDPOINT = "/content-api/content-service/content-items";
    public static final String USERS_ENDPOINT = "/process-api/identity/users";
    public static final String GROUPS_ENDPOINT = "/process-api/identity/groups";

    public static final String TASK_ACTION = "action";
    public static final String TASK_ACTION_CLAIM = "claim";
    public static final String TASK_ACTION_COMPLETE = "complete";
    public static final String TASK_VARIABLES = "variables";
    public static final String TASK_ASSIGNEE = "assignee";

    public static final String TASK_RESPONSE_VARIABLE = "previewResponse";
    public static final String TASK_OPINION_VARIABLE = "task_opinion";
    public static final String TASK_RESPONSE_APPROVE = "Approve";
    public static final String TASK_RESPONSE_REJECT = "Reject";
}
