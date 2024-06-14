package ai.inno.clever.service.dto.graphs;

public class Graph {
    private String name;
    private GraphData data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GraphData getData() {
        return data;
    }

    public void setData(GraphData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Graph{" +
            "name='" + name + '\'' +
            ", data=" + data +
            '}';
    }
}
