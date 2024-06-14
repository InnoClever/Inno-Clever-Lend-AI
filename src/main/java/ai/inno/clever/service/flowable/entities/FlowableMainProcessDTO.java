package ai.inno.clever.service.flowable.entities;

import java.util.Map;

public class FlowableMainProcessDTO {
    private String id;
    private String name;
    private Map<String, FlowElementDTO> flowElementMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, FlowElementDTO> getFlowElementMap() {
        return flowElementMap;
    }

    public void setFlowElementMap(Map<String, FlowElementDTO> flowElementMap) {
        this.flowElementMap = flowElementMap;
    }

    @Override
    public String toString() {
        return "FlowableMainProcessDTO{" +
            "name='" + name + '\'' +
            ", flowElementMap=" + flowElementMap +
            '}';
    }
}
