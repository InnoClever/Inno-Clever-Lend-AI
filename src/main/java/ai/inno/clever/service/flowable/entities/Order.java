package ai.inno.clever.service.flowable.entities;

public class Order {

    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";

    public static final String START_TIME = "startTime";
    public static final String CREATE_TIME = "createTime";

    private String order;
    private String sort;

    public Order() {}

    public Order(String sort, String order) {
        this.sort = sort;
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
