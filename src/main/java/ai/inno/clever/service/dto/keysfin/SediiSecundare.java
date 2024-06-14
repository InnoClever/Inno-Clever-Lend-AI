package ai.inno.clever.service.dto.keysfin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SediiSecundare implements Serializable {

    @SerializedName("row")
    @Expose
    private List<Row> row = null;

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }

    public void addRow(Row e) {
        if (row == null) {
            row = new ArrayList<>();
        }
        this.row.add(e);
    }

    @Override
    public String toString() {
        return "SediiSecundare{" + "row=" + row + '}';
    }
}
