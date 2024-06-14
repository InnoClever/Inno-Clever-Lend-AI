package ai.inno.clever.service.flowable.entities;

public class Pagination {

    private int start;
    private int size;

    public Pagination() {}

    public Pagination(int start, int size) {
        this.start = start;
        this.size = size;
    }

    public Pagination(int size) {
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
