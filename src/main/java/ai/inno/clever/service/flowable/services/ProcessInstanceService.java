package ai.inno.clever.service.flowable.services;

import ai.inno.clever.service.dto.TaskDetails;
import ai.inno.clever.service.events.error.exceptions.ResourceNotFoundException;
import ai.inno.clever.service.flowable.entities.*;
import ai.inno.clever.service.flowable.util.FlowableUtil;
import ai.inno.clever.service.flowable.util.RequestBodyBuilder;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProcessInstanceService extends FlowableClient {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceService.class);

    public ProcessInstanceService(Gson gson) {
        super(gson);
    }

    public FlowableProcessInstanceListDTO listAllHistoricProcessInstances(
        @Nullable String processDefinitionKey,
        @Nullable Boolean includeProcessVariables,
        Pagination pagination
    ) {
        Order order = new Order(Order.START_TIME, Order.ORDER_DESC);

        Map<String, Object> body = new RequestBodyBuilder()
            .setProcessDefinitionKey(processDefinitionKey)
            .includeProcessVariables(includeProcessVariables)
            .setPagination(pagination)
            .addOrder(order)
            .build();

        FlowableProcessInstanceListDTO response = restClient.queryHistoricProcessInstances(body);

        response
            .getData()
            .forEach(
                processInstance -> {
                    processInstance.setVariablesFormatted(FlowableUtil.formatVariables(processInstance.getVariables()));
                    processInstance.setVariables(null);
                }
            );

        return response;
    }


    public FlowableTaskListDTO listRunningTasksForInstance(
        @Nullable String processInstanceId
    ) {
        Order order = new Order(Order.START_TIME, Order.ORDER_DESC);

        Pagination pagination = new Pagination();
        pagination.setStart(0);
        pagination.setSize(200);

        Map<String, Object> body = new RequestBodyBuilder()
            .setProcessInstanceId(processInstanceId)

            .setPagination(new Pagination(100))
            .build();




        return restClient.listOfTasks(body);


    }



    public FlowableTaskDTO listAllHistoricProcessInstancesTask(
        @Nullable String processInstanceId,
        @Nullable String taskDefinitionKey
    ) {
        Order order = new Order(Order.START_TIME, Order.ORDER_DESC);

        Pagination pagination = new Pagination();
        pagination.setStart(0);
        pagination.setSize(200);


        Map<String, Object> body = new RequestBodyBuilder()
            .setProcessInstanceId(processInstanceId)
            .setTaskDefinitionKey(taskDefinitionKey)
            .setPagination(pagination)
            .addOrder(order)
            .build();

        FlowableTaskListDTO response = restClient.queryHistoricTasks(body);


        Optional<FlowableTaskDTO> taskOptional = response
            .getData()
            .stream()
            .map(task -> {
                log.debug(task.toString());
                return task;
            })
            .filter(task -> task.getTaskDefinitionKey().equals(taskDefinitionKey))
            .findFirst();

        if (taskOptional.isPresent()) {
            return taskOptional.get();
        }



        log.debug("Existing tasks: ");
        log.debug("========================================");
        response
            .getData()
            .stream()
            .forEach(s -> log.debug("{}", s.toString()));
        log.debug("========================================");

        throw new ResourceNotFoundException(String.format("Process with ID: %s does not have task with ID: %s", processInstanceId, taskDefinitionKey));
    }

    public FlowableProcessInstanceDTO startProcessInstanceByKey(String processDefinitionKey, Variables variables) {
        Map<String, Object> body = new RequestBodyBuilder()
            .setProcessDefinitionKey(processDefinitionKey)
            .setReturnVariables()
            .setVariables(variables)
            .build();
        return restClient.startProcessInstance(body);
    }

    public TaskDetails getTaskImportanceDetails(String processDefinitionId, String taskId) {
        FlowableProcessDefinitionModel definitionModel = restClient.getDefinitionModel(processDefinitionId);
        return definitionModel.getMainProcess().getFlowElementMap().entrySet().stream()
            .filter(elem -> elem.getKey().equalsIgnoreCase(taskId))
            .map(el -> {
                if (el.getValue().getDocumentation() != null) {
                    return gson.fromJson(el.getValue().getDocumentation(), TaskDetails.class);
                } else {
                    TaskDetails taskdetails = new TaskDetails();
                    taskdetails.setImportance(TaskImportance.LOW);
                    return taskdetails;
                }
            })
            .findFirst().orElseGet(() -> {
                TaskDetails taskdetails = new TaskDetails();
                taskdetails.setImportance(TaskImportance.LOW);
                return taskdetails;
            });
    }

    public FlowableStatusDTO getProcessInstanceStatus(String processInstanceId) {
        FlowableProcessInstanceDTO processInstance = restClient.getHistoricProcessInstance(processInstanceId);
        if(processInstance!=null) {
            if (processInstance.getEndTime() != null) {
                FlowableStatusDTO status = new FlowableStatusDTO();
                status.setDiagram(null);
                status.setPercentage(100);
                return status;
            }

            // Get diagram
            byte[] diagram = restClient.getProcessDiagramImage(processInstanceId);

            FlowableTaskListDTO tasks = restClient.queryHistoricTasksForProcess(processInstanceId, false);



            FlowableTaskListDTO runningTasks = listRunningTasksForInstance(processInstanceId);


            String runningTasksString = runningTasks.getData().stream()
                .filter(s-> StringUtils.isNotBlank(s.getName())).map(s->s.getName()).collect(Collectors.joining(", "));
            String messageIndex = runningTasksString;

            int completedTasks = (int) tasks.getData().stream()
                .filter(task -> task.getEndTime() != null)
                .map(FlowableTaskDTO::getTaskDefinitionKey)
                .distinct()
                .count();
            int percentage = 0;


            if (completedTasks != 0) {
                String processDefinitionId = tasks.getData().get(0).getProcessDefinitionId();
                FlowableProcessDefinitionModel definitionModel = restClient.getDefinitionModel(processDefinitionId);

                //TODO: @Radu -- remove this stupid name hardcodings


                Stream<Map.Entry<String, FlowElementDTO>> totalTasksStream = definitionModel.getMainProcess().getFlowElementMap().entrySet().stream()
                    .filter(elem -> !elem.getKey().contains("sid-"))
                    .filter(elem -> elem.getKey().startsWith("wait") || elem.getKey().startsWith("surveyTask"))
                    .distinct();
                long totalTasksCount = totalTasksStream.count();


//            log.info(" --- Completed tasks ---");
//            tasks.getData().stream()
//                .distinct()
//                .forEach(task -> {
//                log.info(task.getTaskDefinitionKey());
//                log.info(task.toString());
//            });

                percentage = Math.round(100 * (float) completedTasks / totalTasksCount);

                log.debug("completedTasks: " + completedTasks);
                log.debug("totalTasks: " + totalTasksCount);
                log.debug("percentage: " + percentage);
            }

            FlowableStatusDTO status = new FlowableStatusDTO();
            status.setDiagram(diagram);
            status.setMessage(messageIndex);
            status.setPercentage(percentage);

            return status;
        }else{
            return null;
        }
    }

    //start

    public Variables updateVariablesToProcessInstance(String processInstanceId, Variables variables) {
        // Create request body containing the variables
        return restClient.updateProcessInstanceVariables(processInstanceId, variables);
    }
}
