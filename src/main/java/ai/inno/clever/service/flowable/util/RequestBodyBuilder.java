package ai.inno.clever.service.flowable.util;

import ai.inno.clever.service.flowable.entities.FlowableVariableDTO;
import ai.inno.clever.service.flowable.entities.Order;
import ai.inno.clever.service.flowable.entities.Pagination;
import ai.inno.clever.service.flowable.entities.Variables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestBodyBuilder {

    private Map<String, Object> body;

    public RequestBodyBuilder(Map<String, Object> body) {
        this.body = body;
    }

    public RequestBodyBuilder() {
        body = new HashMap<>();
    }

    public RequestBodyBuilder add(String key, Object value) {
        body.put(key, value);
        return this;
    }

    public RequestBodyBuilder setProcessInstanceId(String processInstanceId) {
        body.put("processInstanceId", processInstanceId);
        return this;
    }

    public RequestBodyBuilder setTaskId(String taskId) {
        body.put("taskId", taskId);
        return this;
    }

    public RequestBodyBuilder setVariables(List<FlowableVariableDTO> variables) {
        body.put("variables", variables);
        return this;
    }

    public RequestBodyBuilder setVariables(Variables variables) {
        body.put("variables", variables.asList());
        return this;
    }

    public RequestBodyBuilder setProcessDefinitionKey(String processDefinitionKey) {
        body.put("processDefinitionKey", processDefinitionKey);
        return this;
    }

    public RequestBodyBuilder setTaskDefinitionKey(String taskDefinitionKey) {
        body.put("taskDefinitionKey", taskDefinitionKey);
        return this;
    }

    public RequestBodyBuilder includeProcessVariables(Boolean includeProcessVariables) {
        body.put("includeProcessVariables", includeProcessVariables);
        return this;
    }

    public RequestBodyBuilder setReturnVariables() {
        body.put("returnVariables", true);
        return this;
    }

    public RequestBodyBuilder includeProcessVariables() {
        body.put("includeProcessVariables", true);
        return this;
    }

    public RequestBodyBuilder includeTaskLocalVariables(Boolean includeTaskLocalVariables) {
        body.put("includeTaskLocalVariables", includeTaskLocalVariables);
        return this;
    }

    public RequestBodyBuilder includeTaskLocalVariables() {
        body.put("includeTaskLocalVariables", true);
        return this;
    }

    public RequestBodyBuilder setPagination(Pagination pagination) {
        body.put("start", pagination.getStart());
        body.put("size", pagination.getSize());
        return this;
    }

    public RequestBodyBuilder addOrder(Order order) {
        body.put("sort", order.getSort());
        body.put("order", order.getOrder());
        return this;
    }

    public Map<String, Object> build() {
        return body;
    }
}
