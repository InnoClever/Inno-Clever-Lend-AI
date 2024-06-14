package ai.inno.clever.domain.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "rbro-configuration")
public class RbroConfigurationProperties {
    private boolean aispConnectionsHidden;

    private boolean bankConnectionsHidden;

    private boolean restrictiveGroup;

    private List<String> eligibilityChecksNonRecourseNeeded;

    private List<String> eligibilityChecksRecourseNeeded;

    private List<String> criticalMails;

    private boolean aispChecks;

    private boolean showReports;

    //restart
    public boolean isAispConnectionsHidden() {
        return aispConnectionsHidden;
    }

    public void setAispConnectionsHidden(boolean aispConnectionsHidden) {
        this.aispConnectionsHidden = aispConnectionsHidden;
    }

    public boolean isBankConnectionsHidden() {
        return bankConnectionsHidden;
    }

    public void setBankConnectionsHidden(boolean bankConnectionsHidden) {
        this.bankConnectionsHidden = bankConnectionsHidden;
    }

    public boolean isRestrictiveGroup() {
        return restrictiveGroup;
    }

    public void setRestrictiveGroup(boolean restrictiveGroup) {
        this.restrictiveGroup = restrictiveGroup;
    }

    public List<String> getEligibilityChecksNonRecourseNeeded() {
        return eligibilityChecksNonRecourseNeeded;
    }

    public void setEligibilityChecksNonRecourseNeeded(List<String> eligibilityChecksNonRecourseNeeded) {
        this.eligibilityChecksNonRecourseNeeded = eligibilityChecksNonRecourseNeeded;
    }

    public List<String> getEligibilityChecksRecourseNeeded() {
        return eligibilityChecksRecourseNeeded;
    }

    public void setEligibilityChecksRecourseNeeded(List<String> eligibilityChecksRecourseNeeded) {
        this.eligibilityChecksRecourseNeeded = eligibilityChecksRecourseNeeded;
    }

    public List<String> getCriticalMails() {
        return criticalMails;
    }

    public void setCriticalMails(List<String> criticalMails) {
        this.criticalMails = criticalMails;
    }

    public boolean isAispChecks() {
        return aispChecks;
    }

    public void setAispChecks(boolean aispChecks) {
        this.aispChecks = aispChecks;
    }

    public boolean isShowReports() {
        return showReports;
    }

    public void setShowReports(boolean showReports) {
        this.showReports = showReports;
    }

    @Override
    public String toString() {
        return "RbroConfigurationProperties{" +
            "aispConnectionsHidden=" + aispConnectionsHidden +
            ", bankConnectionsHidden=" + bankConnectionsHidden +
            ", restrictiveGroup=" + restrictiveGroup +
            ", eligibilityChecksNonRecourseNeeded=" + eligibilityChecksNonRecourseNeeded +
            ", eligibilityChecksRecourseNeeded=" + eligibilityChecksRecourseNeeded +
            '}';
    }
}
