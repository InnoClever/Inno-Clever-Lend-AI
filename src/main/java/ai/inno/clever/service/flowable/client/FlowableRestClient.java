package ai.inno.clever.service.flowable.client;

import ai.inno.clever.service.UserService;
import ai.inno.clever.service.events.error.exceptions.ServerException;
import ai.inno.clever.service.flowable.entities.*;
import ai.inno.clever.service.flowable.util.HeadersUtil;
import ai.inno.clever.service.flowable.util.RequestBodyBuilder;
import ai.innoclever.config.FlowableConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static ai.innoclever.service.flowable.util.Constants.*;

@Component
public class FlowableRestClient {

    private final Logger log = LoggerFactory.getLogger(FlowableRestClient.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Gson gson;

    @Autowired
    UserService userService;

    @Autowired
    private FlowableConfig flowableConfig;

    protected String baseUrl;

    @PostConstruct
    public void configureClient() {
        this.baseUrl = "http://" + flowableConfig.getHost() + ":" + flowableConfig.getPort() + "/flowable-ui";
    }

    // PROCESS INSTANCES
    public FlowableProcessInstanceDTO getProcessInstance(String processInstanceId) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + PROCESS_INSTANCE_ENDPOINT + "/" + processInstanceId;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested resource could not be fetched!");
        }

        Type type = new TypeToken<FlowableProcessInstanceDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public FlowableTaskListDTO queryHistoricTasksForProcess(String processInstanceId,
        Boolean includeTaskLocalVariables) {
        Map<String, Object> body = new RequestBodyBuilder()
            .setProcessInstanceId(processInstanceId)
            .includeTaskLocalVariables(includeTaskLocalVariables)
            .add("size", 100)
            .addOrder(new Order(Order.START_TIME, Order.ORDER_DESC))
            .build();

        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + QUERY_HISTORIC_TASKS;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(body), headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableTaskListDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public void deleteProcessInstance(String processInstanceId) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + PROCESS_INSTANCE_ENDPOINT + "/" + processInstanceId;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.DELETE, request, String.class);

        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new ServerException("The requested resource could not be deleted!");
        }
    }

    public void activateOrSuspendProcessInstance(String processInstanceId, Map<String, ?> body) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + PROCESS_INSTANCE_ENDPOINT + "/" + processInstanceId;
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(body), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }
    }

    public FlowableProcessInstanceDTO startProcessInstance(Map<String, ?> body) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + PROCESS_INSTANCE_ENDPOINT;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(body), headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new ServerException("The requested action could not be performed");
        }
        //restart
        Type type = new TypeToken<FlowableProcessInstanceDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public FlowableProcessInstanceListDTO listOfProcessInstances(Map<String, ?> queryParams) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
            params.add(entry.getKey(), String.valueOf(entry.getValue()));
        }

        String url = baseUrl + PROCESS_INSTANCE_ENDPOINT;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParams(params);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableProcessInstanceListDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public FlowableProcessInstanceListDTO queryProcessInstances(Map<String, ?> body) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + QUERY_PROCESS_INSTANCES;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(body), headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableProcessInstanceListDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public Variables listOfVariablesForProcessInstance(String processInstanceId) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + PROCESS_INSTANCE_ENDPOINT + "/" + processInstanceId + "/variables";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<List<FlowableVariableDTO>>() {

        }.getType();
        return new Variables(gson.fromJson(response.getBody(), type));
    }

    public Variables updateProcessInstanceVariables(String processInstanceId, Variables variables) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + PROCESS_INSTANCE_ENDPOINT + "/" + processInstanceId + "/variables";
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(variables.asList()), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new ServerException("The requested action could not be performed!");
        }
        Type type = new TypeToken<List<FlowableVariableDTO>>() {

        }.getType();
        return new Variables(gson.fromJson(response.getBody(), type));
    }

    // TASKS
    public FlowableTaskDTO getTask(String taskId) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + TASK_ENDPOINT + "/" + taskId;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested resource could not be fetched!");
        }

        Type type = new TypeToken<FlowableTaskDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public FlowableTaskListDTO listOfTasks(Map<String, ?> queryParams) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
            params.add(entry.getKey(), String.valueOf(entry.getValue()));
        }

        String url = baseUrl + TASK_ENDPOINT;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParams(params);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableTaskListDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public FlowableTaskListDTO queryTasks(Map<String, ?> body) {
        log.debug("Query tasks with body: \n" + body.toString());
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + QUERY_TASKS;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(body), headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableTaskListDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public void updateTask(String taskId, Map<String, ?> body) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + TASK_ENDPOINT + "/" + taskId;
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(body), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }
    }

    public Variables addTaskVariables(String taskId, Variables variables) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + TASK_ENDPOINT + "/" + taskId + "/variables";
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(variables.asList()), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        Type type = new TypeToken<List<FlowableVariableDTO>>() {

        }.getType();
        return new Variables(gson.fromJson(response.getBody(), type));
    }

    public Variables addProcessInstanceVariables(String processInstanceId, Variables variables) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + PROCESS_INSTANCE_ENDPOINT + "/" + processInstanceId + "/variables";
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(variables.asList()), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        Type type = new TypeToken<List<FlowableVariableDTO>>() {

        }.getType();
        return new Variables(gson.fromJson(response.getBody(), type));
    }

    public void taskActions(String taskId, Map<String, ?> body) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + TASK_ENDPOINT + "/" + taskId;
        HttpEntity<String> request = new HttpEntity<>(gson.toJson(body), headers);

        int repeats = 0;

        Exception lastError = null;
        while (repeats < 3) {
            try {
                log.info("[{}]: taskActions {}-{}: {}", repeats, taskId, body.get(TASK_ACTION), url);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
                log.info("[{}]: Response {}", repeats, response.getStatusCode());

                if (response.getStatusCode() != HttpStatus.OK) {
                    throw new ServerException("The requested action could not be performed");
                }
                break;
            } catch (Exception ex) {
                repeats++;
                lastError = ex;

                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (Exception ex2) {
                    //ignore
                }
            }
        }

        if (lastError != null) {
            log.error("The requested action could not be performed", lastError);
            throw new ServerException(lastError.getMessage());
        }

    }

    public Variables getAllVariablesForTask(String taskId, @Nullable String scope) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + TASK_ENDPOINT + "/" + taskId + "/variables";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        if (scope != null) {
            uriBuilder.queryParam("scope", scope);
        }

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<List<FlowableVariableDTO>>() {

        }.getType();
        return new Variables(gson.fromJson(response.getBody(), type));
    }

    public FlowableVariableDTO updateTaskVariable(String taskId, FlowableVariableDTO variable) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + TASK_ENDPOINT + "/" + taskId + "/variables/" + variable.getName();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(variable), headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.PUT, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableVariableDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    // HISTORY
    public FlowableProcessInstanceDTO getHistoricProcessInstance(String processInstanceId) {
        try {
            HttpHeaders headers = HeadersUtil.createHeaders(userService);

            String url = baseUrl + HISTORIC_PROCESS_INSTANCES + "/" + processInstanceId;
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> response =
                restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ServerException("The requested resource could not be fetched!");
            }

            Type type = new TypeToken<FlowableProcessInstanceDTO>() {

            }.getType();
            return gson.fromJson(response.getBody(), type);
        } catch (Exception e) {
            return null;
        }
    }

    public FlowableTaskListDTO getHistoricTasks(String processInstanceId, boolean completedTasksOnly) {
        try {
            HttpHeaders headers = HeadersUtil.createHeaders(userService);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("finished", String.valueOf(completedTasksOnly));
            params.add("processInstanceId", processInstanceId);
            params.add("size", String.valueOf(100));

            String url = baseUrl + HISTORIC_TASKS;
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParams(params);

            HttpEntity<String> request = new HttpEntity<>(headers);
            ResponseEntity<String> response =
                restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ServerException("The requested resource could not be fetched!");
            }

            Type type = new TypeToken<FlowableTaskListDTO>() {

            }.getType();
            return gson.fromJson(response.getBody(), type);
        } catch (Error error) {
            return null;
        }
    }

    public FlowableProcessInstanceListDTO listOfHistoricProcessInstances(Map<String, ?> queryParams) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
            params.add(entry.getKey(), String.valueOf(entry.getValue()));
        }

        String url = baseUrl + QUERY_HISTORIC_PROCESS_INSTANCES;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParams(params);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableProcessInstanceListDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public FlowableProcessInstanceListDTO queryHistoricProcessInstances(Map<String, ?> body) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + QUERY_HISTORIC_PROCESS_INSTANCES;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(body), headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableProcessInstanceListDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public FlowableTaskDTO getHistoricTask(String taskId) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + HISTORIC_TASKS + "/" + taskId;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested resource could not be fetched!");
        }

        Type type = new TypeToken<FlowableTaskDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public FlowableTaskListDTO listOfHistoricTasks(Map<String, ?> queryParams) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
            params.add(entry.getKey(), String.valueOf(entry.getValue()));
        }

        String url = baseUrl + QUERY_HISTORIC_TASKS;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParams(params);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableTaskListDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public FlowableTaskListDTO queryHistoricTasks(Map<String, ?> body) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + QUERY_HISTORIC_TASKS;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(body), headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableTaskListDTO>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }

    public byte[] getProcessDiagramImage(String processInstanceId) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + PROCESS_INSTANCE_ENDPOINT + "/" + processInstanceId + "/diagram";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<byte[]> response =
                restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, byte[].class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ServerException("The requested action could not be performed");
            }

            return response.getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }

    }

    public FlowableProcessDefinitionModel getDefinitionModel(String processDefinitionId) {
        HttpHeaders headers = HeadersUtil.createHeaders(userService);

        String url = baseUrl + PROCESS_DEFINITIONS_ENDPOINT + "/" + processDefinitionId + "/model";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response =
            restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ServerException("The requested action could not be performed");
        }

        Type type = new TypeToken<FlowableProcessDefinitionModel>() {

        }.getType();
        return gson.fromJson(response.getBody(), type);
    }
}
