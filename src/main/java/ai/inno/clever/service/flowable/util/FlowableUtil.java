package ai.inno.clever.service.flowable.util;

import ai.inno.clever.service.flowable.entities.FlowableVariableDTO;
import ai.inno.clever.service.flowable.entities.Variables;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlowableUtil {

    // Format variables from custom object to Key<->Value
    public static Map<String, Object> formatVariables(List<FlowableVariableDTO> variables) {
        Map<String, Object> variablesFormatted = new HashMap<>();
        if (variables != null) {
            variables.forEach(
                variable -> {
                    variablesFormatted.put(variable.getName(), variable.getValue());
                }
            );
        }
        return variablesFormatted;
    }


    public static Map<String, Object> formatVariables(Variables variables) {
        Map<String, Object> variablesFormatted = new HashMap<>();
        variables
            .asList()
            .forEach(
                variable -> {
                    variablesFormatted.put(variable.getName(), variable.getValue());
                }
            );
        return variablesFormatted;
    }

    public static Variables createFlowableVariables(Gson gson, String actionBody) throws Exception {
        Variables variables = new Variables();
        if (actionBody != null) {
            Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
            try {
                HashMap<String, Object> requestVariables = gson.fromJson(actionBody, type);
                List<FlowableVariableDTO> requestFlowableVariables = requestVariables
                    .entrySet()
                    .stream()
                    .map(e -> new FlowableVariableDTO(e.getKey(), String.valueOf(e.getValue())))
                    .collect(Collectors.toList());

                variables.addAll(requestFlowableVariables);
            } catch (JsonSyntaxException e) {
                throw new Exception("Flowable variables may not be send to the flowable engine: " + e.toString());
            }
        }
        return variables;
    }
}
