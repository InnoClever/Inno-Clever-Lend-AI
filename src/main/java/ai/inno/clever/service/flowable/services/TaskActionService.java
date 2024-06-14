package ai.inno.clever.service.flowable.services;

import ai.inno.clever.service.flowable.entities.FlowableVariableDTO;
import ai.inno.clever.service.flowable.entities.Variables;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static ai.innoclever.service.flowable.util.Constants.*;

@Component
public class TaskActionService extends FlowableClient {

    private final Logger log = LoggerFactory.getLogger(TaskActionService.class);

    public TaskActionService(Gson gson) {
        super(gson);
    }

    public void claimTask(String taskId, String assignee) {
        Map<String, Object> body = new HashMap<>();
        body.put(TASK_ACTION, TASK_ACTION_CLAIM);
        body.put(TASK_ASSIGNEE, assignee);
        restClient.taskActions(taskId, body);
    }

    public void completeTask(String taskId) {
        Map<String, Object> body = new HashMap<>();
        body.put(TASK_ACTION, TASK_ACTION_COMPLETE);
        restClient.taskActions(taskId, body);
    }

    public void completeTask(String taskId, Variables variables) {
        addLocalVariablesToTask(taskId, variables);

        Map<String, Object> body = new HashMap<>();
        body.put(TASK_ACTION, TASK_ACTION_COMPLETE);
        body.put(TASK_VARIABLES, variables.asList());
        restClient.taskActions(taskId, body);
    }


    public void approveTask(String taskId, Variables variables) {
        variables.add(createResponseVariable(TASK_RESPONSE_APPROVE));
        variables.add(createOpinionVariable(TASK_RESPONSE_APPROVE));
        completeTask(taskId, variables);
    }

    public void rejectTask(String taskId, Variables variables) {
        variables.add(createResponseVariable(TASK_RESPONSE_REJECT));
        variables.add(createOpinionVariable(TASK_RESPONSE_REJECT));
        completeTask(taskId, variables);
    }

    private void addLocalVariablesToTask(String taskId, Variables variables) {
        // Create request body containing the variables
        Variables formattedVariables = prepareVariablesForTask(variables);
        restClient.addTaskVariables(taskId, formattedVariables);
    }

    private FlowableVariableDTO createResponseVariable(String responseValue) {
        FlowableVariableDTO var = new FlowableVariableDTO();
        var.setName(TASK_RESPONSE_VARIABLE);
        var.setValue(responseValue);
        return var;
    }

    private FlowableVariableDTO createOpinionVariable(String responseValue) {
        FlowableVariableDTO var = new FlowableVariableDTO();
        var.setName(TASK_OPINION_VARIABLE);
        var.setValue(responseValue);
        return var;
    }

    private Variables prepareVariablesForTask(Variables variables) {
        return new Variables(
            variables
                .asList()
                .stream()
                .map(
                    variable -> {
                        FlowableVariableDTO newVar = new FlowableVariableDTO();
                        newVar.setName("task_" + variable.getName());
                        newVar.setValue(String.valueOf(variable.getValue()));
                        newVar.setScope("local");
                        return newVar;
                    }
                )
                .collect(Collectors.toList())
        );
    }
}
