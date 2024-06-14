package ai.inno.clever.service.flowable.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlowableTaskListDTO {

    @SerializedName("data")
    @Expose
    private List<FlowableTaskDTO> data = null;

    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("start")
    @Expose
    private Integer start;

    @SerializedName("sort")
    @Expose
    private String sort;

    @SerializedName("order")
    @Expose
    private String order;

    @SerializedName("size")
    @Expose
    private Integer size;

    public List<FlowableTaskDTO> getData() {
        return data;
    }

    public void setData(List<FlowableTaskDTO> data) {
        this.data = data;
    }

    public void addData(FlowableTaskDTO item) {
        this.data.add(item);
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return (
            "TaskListDTO{" +
            "data=" +
            data +
            ", total=" +
            total +
            ", start=" +
            start +
            ", sort='" +
            sort +
            '\'' +
            ", order='" +
            order +
            '\'' +
            ", size=" +
            size +
            '}'
        );
    }
}
