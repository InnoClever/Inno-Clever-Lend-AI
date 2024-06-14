package ai.inno.clever.service.flowable;

import ai.inno.clever.service.dto.TaskDetails;
import ai.inno.clever.service.flowable.entities.*;
import ai.inno.clever.service.lock.PessimisticLock;

public interface FlowableService {
    FlowableTaskDTO getTask(String taskId) throws Exception;

    FlowableTaskListDTO getTasksForProcess(String processId, boolean includeTaskLocalVariables);

    FlowableTaskListDTO listTaskInstances(String processDefinitionKey, String candidateOrAssigned, String category, Pagination pagination);

    Variables getTaskVariables(String taskId) throws Exception;

    FlowableProcessInstanceListDTO listAllHistoricProcessInstances(
        String processDefinitionKey,
        boolean includeProcessVariables,
        Pagination pagination
    );

    FlowableTaskDTO getProcessInstanceTaskWithKey(
        String processInstanceId,
        String taskDefinitionKey,
        boolean waitForTask);

    FlowableProcessInstanceListDTO listAllHistoricProcessInstancesWithTasks(
        String processDefinitionKey,
        boolean includeProcessVariables,
        Pagination pagination
    );

    FlowableProcessInstanceDTO startProcessInstanceByKey(String processInstanceKey, Variables variables) throws Exception;

    @PessimisticLock(keyExpression = "#processId", type = Variables.class)
    Variables updateProcessInstanceVariables(String processId, Variables variables) throws Exception;

    void completeTaskWithVariables(String taskId, Variables variables);

    void claimTask(String taskId, String login);

    void approveTask(String taskId, Variables variables);

    void rejectTask(String taskId, Variables variables);

    void completeTask(String taskId);

    void completeTask( String processId, String taskId) throws Exception ;



    FlowableStatusDTO getProcessStatus(String processInstanceId);

    TaskDetails getTaskImportanceDetails(String processDefinitionId, String taskId);
}
