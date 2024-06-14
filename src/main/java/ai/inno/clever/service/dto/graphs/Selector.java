package ai.inno.clever.service.dto.graphs;

import java.util.ArrayList;
import java.util.List;

public class Selector {
    private String argumentName;
    private String label;
    private List<String> options = new ArrayList<>();

    public String getArgumentName() {
        return argumentName;
    }

    public void setArgumentName(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Selector{" +
            "argumentName='" + argumentName + '\'' +
            ", label='" + label + '\'' +
            ", options=" + options +
            '}';
    }
}
