package ai.inno.clever.service.flowable.entities;

public class FlowableProcessDefinitionModel {
    private FlowableMainProcessDTO mainProcess;

    public FlowableMainProcessDTO getMainProcess() {
        return mainProcess;
    }

    public void setMainProcess(FlowableMainProcessDTO mainProcess) {
        this.mainProcess = mainProcess;
    }

    @Override
    public String toString() {
        return "FlowableProcessDefinitionModel{" +
            "mainProcess=" + mainProcess +
            '}';
    }
}
