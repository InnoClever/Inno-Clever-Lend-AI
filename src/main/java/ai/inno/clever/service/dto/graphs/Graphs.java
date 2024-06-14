package ai.inno.clever.service.dto.graphs;

import java.util.ArrayList;
import java.util.List;

public class Graphs {
    List<Graph> graphs = new ArrayList<>();

    public List<Graph> getGraphs() {
        return graphs;
    }

    public void setGraphs(List<Graph> graphs) {
        this.graphs = graphs;
    }

    @Override
    public String toString() {
        return "Graphs{" +
            "graphs=" + graphs +
            '}';
    }
}
