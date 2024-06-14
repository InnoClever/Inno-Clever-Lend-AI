package ai.inno.clever.service.flowable.entities;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.thymeleaf.util.StringUtils;

public class Variables implements Serializable {

    List<FlowableVariableDTO> variables;

    public Variables(List<FlowableVariableDTO> variables) {
        this.variables = variables;
    }

    public Variables() {
        this.variables = new ArrayList<>();
    }

    public void add(FlowableVariableDTO variable) {
        this.variables.add(variable);
    }

    public void addAll(List<FlowableVariableDTO> variables) {
        this.variables.addAll(variables);
    }

    public void addClassVariables(Object classObject) throws IllegalAccessException {
        addClassVariables(classObject, null);
    }

    public void addClassVariables(Object classObject, String prefix) throws IllegalAccessException {
        // Add all application fields variables
        for (Field field : classObject.getClass().getDeclaredFields()) {
            field.setAccessible(true); // if you want to modify private fields
            if (field.get(classObject) == null) continue;
            FlowableVariableDTO var = new FlowableVariableDTO();
            var.setName(prefix == null ? field.getName() : String.format(prefix + "%s", StringUtils.capitalize(field.getName())));
            var.setValue(String.valueOf(field.get(classObject)));
            this.variables.add(var);
        }
    }

    public void removeIdVariable() {
        this.variables = variables.stream().filter(var -> !var.getName().equalsIgnoreCase("id")).collect(Collectors.toList());
    }

    public void removeVariable(FlowableVariableDTO variable) {
        this.variables = variables.stream().filter(var -> !var.getName().equalsIgnoreCase(variable.getName())).collect(Collectors.toList());
    }

    public void removeVariable(String variableName) {
        this.variables = variables.stream().filter(var -> !var.getName().equalsIgnoreCase(variableName)).collect(Collectors.toList());
    }

    public List<FlowableVariableDTO> asList() {
        return this.variables;
    }
}
