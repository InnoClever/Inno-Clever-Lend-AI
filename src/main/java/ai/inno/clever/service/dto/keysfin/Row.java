package ai.inno.clever.service.dto.keysfin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Row implements Serializable {

    @SerializedName("Nume")
    @Expose
    private String nume;

    @SerializedName("DataSart")
    @Expose
    private String dataSart;

    @SerializedName("DataStart")
    @Expose
    private String dataStart;

    @SerializedName("DataStop")
    @Expose
    private String dataStop;

    @SerializedName("Tara")
    @Expose
    private String tara;

    @SerializedName("Localitate")
    @Expose
    private String localitate;

    @SerializedName("Strada")
    @Expose
    private String strada;

    @SerializedName("Numarul")
    @Expose
    private String numarul;

    @SerializedName("Bloc")
    @Expose
    private String bloc;

    @SerializedName("Scara")
    @Expose
    private String scara;

    @SerializedName("Etaj")
    @Expose
    private String etaj;

    @SerializedName("Apartament")
    @Expose
    private String apartament;

    @SerializedName("Sector")
    @Expose
    private String sector;

    @SerializedName("CodPostal")
    @Expose
    private String codPostal;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDataSart() {
        return dataSart;
    }

    public void setDataSart(String dataSart) {
        this.dataSart = dataSart;
    }

    public String getDataStart() {
        return dataStart;
    }

    public void setDataStart(String dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataStop() {
        return dataStop;
    }

    public void setDataStop(String dataStop) {
        this.dataStop = dataStop;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNumarul() {
        return numarul;
    }

    public void setNumarul(String numarul) {
        this.numarul = numarul;
    }

    public String getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public String getScara() {
        return scara;
    }

    public void setScara(String scara) {
        this.scara = scara;
    }

    public String getEtaj() {
        return etaj;
    }

    public void setEtaj(String etaj) {
        this.etaj = etaj;
    }

    public String getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    @Override
    public String toString() {
        return (
            "Row{" +
            "nume='" +
            nume +
            '\'' +
            ", dataSart='" +
            dataSart +
            '\'' +
            ", dataStart='" +
            dataStart +
            '\'' +
            ", dataStop='" +
            dataStop +
            '\'' +
            ", tara='" +
            tara +
            '\'' +
            ", localitate='" +
            localitate +
            '\'' +
            ", strada='" +
            strada +
            '\'' +
            ", numarul='" +
            numarul +
            '\'' +
            ", bloc=" +
            bloc +
            ", scara=" +
            scara +
            ", etaj=" +
            etaj +
            ", apartament=" +
            apartament +
            ", sector=" +
            sector +
            ", codPostal=" +
            codPostal +
            '}'
        );
    }
}
