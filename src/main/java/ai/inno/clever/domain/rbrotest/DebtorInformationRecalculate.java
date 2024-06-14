package ai.inno.clever.domain.rbrotest;

public class DebtorInformationRecalculate {
    private int cui;
    private double max_perc_assigned;
    private String product;

    public int getCui() {
        return cui;
    }

    public void setCui(int cui) {
        this.cui = cui;
    }

    public double getMax_perc_assigned() {
        return max_perc_assigned;
    }

    public void setMax_perc_assigned(double max_perc_assigned) {
        this.max_perc_assigned = max_perc_assigned;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "DebtorInformationRecalculate{" +
            "cui='" + cui + '\'' +
            ", max_perc_assigned=" + max_perc_assigned +
            ", product='" + product + '\'' +
            '}';
    }
}
