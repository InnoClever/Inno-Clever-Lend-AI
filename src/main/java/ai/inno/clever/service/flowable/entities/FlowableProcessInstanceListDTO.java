package ai.inno.clever.service.flowable.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlowableProcessInstanceListDTO {

    @SerializedName("data")
    private List<FlowableProcessInstanceDTO> data;

    @SerializedName("total")
    private Long total;

    @SerializedName("start")
    private Long start;

    @SerializedName("order")
    private String order;

    @SerializedName("size")
    private Long size;

    public List<FlowableProcessInstanceDTO> getData() {
        return data;
    }

    public void setData(List<FlowableProcessInstanceDTO> data) {
        this.data = data;
    }

    public void addData(FlowableProcessInstanceDTO item) {
        this.data.add(item);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return (
            "ProcessInstanceListDTO{" +
            "data=" +
            data +
            ", total=" +
            total +
            ", start=" +
            start +
            ", order='" +
            order +
            '\'' +
            ", size=" +
            size +
            '}'
        );
    }
}
