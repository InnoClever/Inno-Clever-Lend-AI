package ai.inno.clever.service.flowable.services;

import ai.inno.clever.service.flowable.entities.*;
import ai.inno.clever.service.flowable.util.FlowableUtil;
import ai.inno.clever.service.flowable.util.RequestBodyBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends FlowableClient {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    public TaskService(Gson gson) {
        super(gson);
    }

    public FlowableTaskListDTO listTaskInstances(
        String processDefinitionKey,
        String candidateGroup,
        String category,
        Pagination pagination
    ) {
        if(pagination == null){
            pagination = new Pagination(100);
        }

        Map<String, Object> body = new RequestBodyBuilder()
            .setProcessDefinitionKey(processDefinitionKey)
            .add("candidateOrAssigned", candidateGroup)
            .add("category", category)
            .includeProcessVariables()
            .setPagination(pagination)
            .addOrder(new Order(Order.CREATE_TIME, Order.ORDER_DESC))
            .build();

        FlowableTaskListDTO taskList = restClient.queryTasks(body);
        formatAllTasksVariables(taskList);
        return taskList;
    }

    public FlowableTaskDTO getTask(String taskId) {
        FlowableTaskDTO task = restClient.getTask(taskId);
        requestAndAppendFormattedVariables(task);
        return task;
    }

    public FlowableTaskListDTO getHistoricTasksForProcess(String processInstanceId, Boolean includeTaskLocalVariables) {
        Map<String, Object> body = new RequestBodyBuilder()
            .setProcessInstanceId(processInstanceId)
            .includeTaskLocalVariables(includeTaskLocalVariables)
            .addOrder(new Order(Order.START_TIME, Order.ORDER_DESC))
            .setPagination(new Pagination(100))
            .build();
        FlowableTaskListDTO taskList = restClient.queryHistoricTasks(body);

        formatAllTasksVariables(taskList);
        return taskList;
    }

    public Variables getHistoricTaskVariables(String taskId) {
        FlowableTaskDTO task = getQueryHistoricTaskFromServer(taskId);
        return new Variables(task.getVariables());
    }

    private FlowableTaskDTO getQueryHistoricTaskFromServer(String taskId) {
        Map<String, Object> body = new RequestBodyBuilder().setTaskId(taskId).includeProcessVariables().build();
        return restClient.queryHistoricTasks(body).getData().get(0);
    }

    private void requestAndAppendFormattedVariables(FlowableTaskDTO task) {
        Variables variables = restClient.getAllVariablesForTask(task.getId(), null);
        Map<String, Object> variablesFormatted = new HashMap<>();
        variables
            .asList()
            .forEach(
                variable -> {
                    variablesFormatted.put(variable.getName(), variable.getValue());
                }
            );
        task.setVariables(null);
        task.setVariablesFormatted(variablesFormatted);
    }

    private void formatAllTasksVariables(FlowableTaskListDTO taskList) {
        taskList.getData().forEach(this::formatTaskVariable);
    }

    private void formatTaskVariable(FlowableTaskDTO task) {
        task.setVariablesFormatted(FlowableUtil.formatVariables(task.getVariables()));
        task.setVariables(null);
    }
}
