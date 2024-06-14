package ai.inno.clever.domain.rbrotest;

public class AdherentLimitsSettings {
    double max_perc_assigned;

    public double getMax_perc_assigned() {
        return max_perc_assigned;
    }

    public void setMax_perc_assigned(double max_perc_assigned) {
        this.max_perc_assigned = max_perc_assigned;
    }

    @Override
    public String toString() {
        return "AdherentLimitsSettings{" +
            "max_perc_assigned=" + max_perc_assigned +
            '}';
    }
}
