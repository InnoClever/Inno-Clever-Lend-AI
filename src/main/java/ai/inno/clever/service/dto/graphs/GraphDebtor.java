package ai.inno.clever.service.dto.graphs;

import java.util.ArrayList;
import java.util.List;

public class GraphDebtor {
    private String debtorCUI;
    private String debtorName;
    private List<Selector> selectors = new ArrayList<>();

    public String getDebtorCUI() {
        return debtorCUI;
    }

    public void setDebtorCUI(String debtorCUI) {
        this.debtorCUI = debtorCUI;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public List<Selector> getSelectors() {
        return selectors;
    }

    public void setSelectors(List<Selector> selectors) {
        this.selectors = selectors;
    }

    @Override
    public String toString() {
        return "GraphDebtor{" +
            "debtorCUI='" + debtorCUI + '\'' +
            ", debtorName='" + debtorName + '\'' +
            ", selectors=" + selectors +
            '}';
    }
}
