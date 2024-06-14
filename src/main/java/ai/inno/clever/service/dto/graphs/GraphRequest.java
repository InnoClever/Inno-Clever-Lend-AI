package ai.inno.clever.service.dto.graphs;

public class GraphRequest {

    private String debtorCUI;
    private String requestId;
    private String callbackUrl;
    private String pathId;

    public String getDebtorCUI() {
        return debtorCUI;
    }

    public void setDebtorCUI(String debtorCUI) {
        this.debtorCUI = debtorCUI;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }
}
