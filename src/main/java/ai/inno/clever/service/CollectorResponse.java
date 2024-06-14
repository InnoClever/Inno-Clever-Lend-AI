package ai.inno.clever.service;

import ai.inno.clever.service.dto.TaskResult;

import java.util.ArrayList;
import java.util.List;

public class CollectorResponse {

    private List<TaskResult> collections = new ArrayList<>();

    public List<TaskResult> getCollections() {
        return collections;
    }

    public void setCollections(List<TaskResult> collections) {
        this.collections = collections;
    }

    public void addCollection(TaskResult taskResult){
        this.collections.add(taskResult);
    }
}
