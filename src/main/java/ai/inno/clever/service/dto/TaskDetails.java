package ai.inno.clever.service.dto;

import ai.inno.clever.service.flowable.entities.TaskImportance;

public class TaskDetails {
    private TaskImportance importance;

    public TaskImportance getImportance() {
        return importance;
    }

    public void setImportance(TaskImportance importance) {
        this.importance = importance;
    }

    @Override
    public String toString() {
        return "TaskDetails{" +
            "importance=" + importance +
            '}';
    }
}
