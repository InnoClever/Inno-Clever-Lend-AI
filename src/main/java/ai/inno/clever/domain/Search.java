package ai.inno.clever.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {

    private Integer pagesTotal;

    List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }
}
