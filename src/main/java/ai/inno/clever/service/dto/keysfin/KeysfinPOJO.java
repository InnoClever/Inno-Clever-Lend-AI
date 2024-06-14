package ai.inno.clever.service.dto.keysfin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KeysfinPOJO implements Serializable {

    @SerializedName("FiscalCode")
    @Expose
    private String fiscalCode;

    @SerializedName("NumeCompanie")
    @Expose
    private String numeCompanie;

    @SerializedName("FormaLegala")
    @Expose
    private String formaLegala;

    @SerializedName("NumarInregistrare")
    @Expose
    private String numarInregistrare;

    @SerializedName("DataInfiintarii")
    @Expose
    private String dataInfiintarii;

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("Capital")
    @Expose
    private String capital;

    @SerializedName("Tip_Capital")
    @Expose
    private String tipCapital;

    @SerializedName("CAEN")
    @Expose
    private String caen;

    @SerializedName("CAEN_Descr")
    @Expose
    private String cAENDescr;

    @SerializedName("VerActivPrinc")
    @Expose
    private String verActivPrinc;

    @SerializedName("NumarAngajati")
    @Expose
    private String numarAngajati;

    @SerializedName("Judet")
    @Expose
    private String judet;

    @SerializedName("Oras")
    @Expose
    private String oras;

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

    @SerializedName("CodPostal")
    @Expose
    private String codPostal;

    @SerializedName("NumarTelefon")
    @Expose
    private String numarTelefon;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Web")
    @Expose
    private String web;

    @SerializedName("FormaProprietate")
    @Expose
    private String formaProprietate;

    @SerializedName("SemnaleFirma")
    @Expose
    private String semnaleFirma;

    @SerializedName("DataActualizare")
    @Expose
    private String dataActualizare;

    @SerializedName("sediu_tipAct")
    @Expose
    private String sediuTipAct;

    @SerializedName("sediu_tipDurataAct")
    @Expose
    private String sediuTipDurataAct;

    @SerializedName("sediu_nrAct")
    @Expose
    private String sediuNrAct;

    @SerializedName("sediu_dataAct")
    @Expose
    private String sediuDataAct;

    @SerializedName("DataExpirareSediu_DeLa")
    @Expose
    private String dataExpirareSediuDeLa;

    @SerializedName("DataExpirareSediu_PanaLa")
    @Expose
    private String dataExpirareSediuPanaLa;

    @SerializedName("DataActConstitutiv")
    @Expose
    private String dataActConstitutiv;

    @SerializedName("SediiSecundare")
    @Expose
    private SediiSecundare sediiSecundare;

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getNumeCompanie() {
        return numeCompanie;
    }

    public void setNumeCompanie(String numeCompanie) {
        this.numeCompanie = numeCompanie;
    }

    public String getFormaLegala() {
        return formaLegala;
    }

    public void setFormaLegala(String formaLegala) {
        this.formaLegala = formaLegala;
    }

    public String getNumarInregistrare() {
        return numarInregistrare;
    }

    public void setNumarInregistrare(String numarInregistrare) {
        this.numarInregistrare = numarInregistrare;
    }

    public String getDataInfiintarii() {
        return dataInfiintarii;
    }

    public void setDataInfiintarii(String dataInfiintarii) {
        this.dataInfiintarii = dataInfiintarii;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getTipCapital() {
        return tipCapital;
    }

    public void setTipCapital(String tipCapital) {
        this.tipCapital = tipCapital;
    }

    public String getCaen() {
        return caen;
    }

    public void setCaen(String caen) {
        this.caen = caen;
    }

    public String getCAENDescr() {
        return cAENDescr;
    }

    public void setCAENDescr(String cAENDescr) {
        this.cAENDescr = cAENDescr;
    }

    public String getVerActivPrinc() {
        return verActivPrinc;
    }

    public void setVerActivPrinc(String verActivPrinc) {
        this.verActivPrinc = verActivPrinc;
    }

    public String getNumarAngajati() {
        return numarAngajati;
    }

    public void setNumarAngajati(String numarAngajati) {
        this.numarAngajati = numarAngajati;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
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

    public Object getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public Object getScara() {
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

    public Object getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public Object getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Object getFormaProprietate() {
        return formaProprietate;
    }

    public void setFormaProprietate(String formaProprietate) {
        this.formaProprietate = formaProprietate;
    }

    public String getSemnaleFirma() {
        return semnaleFirma;
    }

    public void setSemnaleFirma(String semnaleFirma) {
        this.semnaleFirma = semnaleFirma;
    }

    public String getDataActualizare() {
        return dataActualizare;
    }

    public void setDataActualizare(String dataActualizare) {
        this.dataActualizare = dataActualizare;
    }

    public String getSediuTipAct() {
        return sediuTipAct;
    }

    public void setSediuTipAct(String sediuTipAct) {
        this.sediuTipAct = sediuTipAct;
    }

    public String getSediuTipDurataAct() {
        return sediuTipDurataAct;
    }

    public void setSediuTipDurataAct(String sediuTipDurataAct) {
        this.sediuTipDurataAct = sediuTipDurataAct;
    }

    public Object getSediuNrAct() {
        return sediuNrAct;
    }

    public void setSediuNrAct(String sediuNrAct) {
        this.sediuNrAct = sediuNrAct;
    }

    public String getSediuDataAct() {
        return sediuDataAct;
    }

    public void setSediuDataAct(String sediuDataAct) {
        this.sediuDataAct = sediuDataAct;
    }

    public String getDataExpirareSediuDeLa() {
        return dataExpirareSediuDeLa;
    }

    public void setDataExpirareSediuDeLa(String dataExpirareSediuDeLa) {
        this.dataExpirareSediuDeLa = dataExpirareSediuDeLa;
    }

    public String getDataExpirareSediuPanaLa() {
        return dataExpirareSediuPanaLa;
    }

    public void setDataExpirareSediuPanaLa(String dataExpirareSediuPanaLa) {
        this.dataExpirareSediuPanaLa = dataExpirareSediuPanaLa;
    }

    public String getDataActConstitutiv() {
        return dataActConstitutiv;
    }

    public void setDataActConstitutiv(String dataActConstitutiv) {
        this.dataActConstitutiv = dataActConstitutiv;
    }

    public SediiSecundare getSediiSecundare() {
        return sediiSecundare;
    }

    public void setSediiSecundare(SediiSecundare sediiSecundare) {
        this.sediiSecundare = sediiSecundare;
    }

    @Override
    public String toString() {
        return (
            "KeysfinPOJO{" +
            "fiscalCode='" +
            fiscalCode +
            '\'' +
            ", numeCompanie='" +
            numeCompanie +
            '\'' +
            ", formaLegala='" +
            formaLegala +
            '\'' +
            ", numarInregistrare='" +
            numarInregistrare +
            '\'' +
            ", dataInfiintarii='" +
            dataInfiintarii +
            '\'' +
            ", status='" +
            status +
            '\'' +
            ", capital='" +
            capital +
            '\'' +
            ", tipCapital='" +
            tipCapital +
            '\'' +
            ", caen='" +
            caen +
            '\'' +
            ", cAENDescr='" +
            cAENDescr +
            '\'' +
            ", verActivPrinc='" +
            verActivPrinc +
            '\'' +
            ", numarAngajati='" +
            numarAngajati +
            '\'' +
            ", judet='" +
            judet +
            '\'' +
            ", oras='" +
            oras +
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
            ", etaj='" +
            etaj +
            '\'' +
            ", apartament=" +
            apartament +
            ", codPostal=" +
            codPostal +
            ", numarTelefon='" +
            numarTelefon +
            '\'' +
            ", email=" +
            email +
            ", web=" +
            web +
            ", formaProprietate=" +
            formaProprietate +
            ", semnaleFirma='" +
            semnaleFirma +
            '\'' +
            ", dataActualizare='" +
            dataActualizare +
            '\'' +
            ", sediuTipAct='" +
            sediuTipAct +
            '\'' +
            ", sediuTipDurataAct='" +
            sediuTipDurataAct +
            '\'' +
            ", sediuNrAct=" +
            sediuNrAct +
            ", sediuDataAct='" +
            sediuDataAct +
            '\'' +
            ", dataExpirareSediuDeLa='" +
            dataExpirareSediuDeLa +
            '\'' +
            ", dataExpirareSediuPanaLa='" +
            dataExpirareSediuPanaLa +
            '\'' +
            ", dataActConstitutiv='" +
            dataActConstitutiv +
            '\'' +
            ", sediiSecundare=" +
            sediiSecundare +
            '}'
        );
    }
}
