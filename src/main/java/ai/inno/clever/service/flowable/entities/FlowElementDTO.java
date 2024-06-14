package ai.inno.clever.service.flowable.entities;

public class FlowElementDTO {
    private String id;
    private String formKey;
    private String documentation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    @Override
    public String toString() {
        return "FlowElementDTO{" +
            "id='" + id + '\'' +
            ", formKey='" + formKey + '\'' +
            ", documentation='" + documentation + '\'' +
            '}';
    }
}
