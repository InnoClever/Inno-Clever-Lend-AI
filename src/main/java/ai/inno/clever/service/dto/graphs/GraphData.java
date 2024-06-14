package ai.inno.clever.service.dto.graphs;

import java.util.ArrayList;
import java.util.List;

public class GraphData {
    private List<GraphDebtor> debtors = new ArrayList<>();

    public List<GraphDebtor> getDebtors() {
        return debtors;
    }

    public void setDebtors(List<GraphDebtor> debtors) {
        this.debtors = debtors;
    }

    @Override
    public String toString() {
        return "GraphData{" +
            "debtors=" + debtors +
            '}';
    }
}
