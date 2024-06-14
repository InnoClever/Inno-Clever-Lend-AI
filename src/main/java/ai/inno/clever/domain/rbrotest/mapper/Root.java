package ai.inno.clever.domain.rbrotest.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
@JsonIgnoreProperties(ignoreUnknown = true)
class FactoringRequest{
    public String id;
    public int seqId;
    public Date date;
    public String status;
    public Object progress;
    public String msg;
    public String processId;
    public int adherentId;
    public Object value;
    private String ownerLogin;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getProgress() {
        return progress;
    }

    public void setProgress(Object progress) {
        this.progress = progress;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public int getAdherentId() {
        return adherentId;
    }

    public void setAdherentId(int adherentId) {
        this.adherentId = adherentId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class SediiSecundare{
    public Object row;

    public Object getRow() {
        return row;
    }

    public void setRow(Object row) {
        this.row = row;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Values{
    @JsonProperty("Judet")
    public String judet;
    @JsonProperty("FormaLegala")
    public String formaLegala;
    @JsonProperty("Email")
    public Object email;
    @JsonProperty("FiscalCode")
    public String fiscalCode;
    @JsonProperty("NumarAngajati")
    public String numarAngajati;
    public String sediu_dataAct;
    public String sediu_tipAct;
    public String sediu_tipDurataAct;
    public Object sediu_nrAct;
    @JsonProperty("Tip_Capital")
    public String tip_Capital;
    @JsonProperty("NumarInregistrare")
    public String numarInregistrare;
    @JsonProperty("Oras")
    public String oras;
    @JsonProperty("CAEN_Descr")
    public String cAEN_Descr;
    @JsonProperty("CodPostal")
    public String codPostal;
    @JsonProperty("Numarul")
    public String numarul;
    @JsonProperty("CUI")
    public String cUI;
    @JsonProperty("Strada")
    public String strada;
    @JsonProperty("CAEN")
    public String cAEN;
    @JsonProperty("Etaj")
    public String etaj;
    @JsonProperty("DataExpirareSediu_PanaLa")
    public String dataExpirareSediu_PanaLa;
    @JsonProperty("FormaProprietate")
    public Object formaProprietate;
    @JsonProperty("Status")
    public String status;
    @JsonProperty("Scara")
    public Object scara;
    @JsonProperty("Bloc")
    public Object bloc;
    @JsonProperty("Apartament")
    public String apartament;
    @JsonProperty("NumarTelefon")
    public String numarTelefon;
    @JsonProperty("SemnaleFirma")
    public String semnaleFirma;
    @JsonProperty("DataActualizare")
    public String dataActualizare;
    @JsonProperty("Capital")
    public String capital;
    @JsonProperty("NumeCompanie")
    public String numeCompanie;
    @JsonProperty("Web")
    public Object web;
    @JsonProperty("SediiSecundare")
    public SediiSecundare sediiSecundare;
    @JsonProperty("DataInfiintarii")
    public String dataInfiintarii;
    @JsonProperty("DataExpirareSediu_DeLa")
    public String dataExpirareSediu_DeLa;
    @JsonProperty("VerActivPrinc")
    public String verActivPrinc;
    @JsonProperty("DataActConstitutiv")
    public String dataActConstitutiv;

    @Override
    public String toString() {
        return "Values{" +
            "judet='" + judet + '\'' +
            ", formaLegala='" + formaLegala + '\'' +
            ", email=" + email +
            ", fiscalCode='" + fiscalCode + '\'' +
            ", numarAngajati='" + numarAngajati + '\'' +
            ", sediu_dataAct='" + sediu_dataAct + '\'' +
            ", sediu_tipAct='" + sediu_tipAct + '\'' +
            ", sediu_tipDurataAct='" + sediu_tipDurataAct + '\'' +
            ", sediu_nrAct=" + sediu_nrAct +
            ", tip_Capital='" + tip_Capital + '\'' +
            ", numarInregistrare='" + numarInregistrare + '\'' +
            ", oras='" + oras + '\'' +
            ", cAEN_Descr='" + cAEN_Descr + '\'' +
            ", codPostal='" + codPostal + '\'' +
            ", numarul='" + numarul + '\'' +
            ", cUI='" + cUI + '\'' +
            ", strada='" + strada + '\'' +
            ", cAEN='" + cAEN + '\'' +
            ", etaj='" + etaj + '\'' +
            ", dataExpirareSediu_PanaLa='" + dataExpirareSediu_PanaLa + '\'' +
            ", formaProprietate=" + formaProprietate +
            ", status='" + status + '\'' +
            ", scara=" + scara +
            ", bloc=" + bloc +
            ", apartament='" + apartament + '\'' +
            ", numarTelefon='" + numarTelefon + '\'' +
            ", semnaleFirma='" + semnaleFirma + '\'' +
            ", dataActualizare='" + dataActualizare + '\'' +
            ", capital='" + capital + '\'' +
            ", numeCompanie='" + numeCompanie + '\'' +
            ", web=" + web +
            ", sediiSecundare=" + sediiSecundare +
            ", dataInfiintarii='" + dataInfiintarii + '\'' +
            ", dataExpirareSediu_DeLa='" + dataExpirareSediu_DeLa + '\'' +
            ", verActivPrinc='" + verActivPrinc + '\'' +
            ", dataActConstitutiv='" + dataActConstitutiv + '\'' +
            '}';
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getFormaLegala() {
        return formaLegala;
    }

    public void setFormaLegala(String formaLegala) {
        this.formaLegala = formaLegala;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getNumarAngajati() {
        return numarAngajati;
    }

    public void setNumarAngajati(String numarAngajati) {
        this.numarAngajati = numarAngajati;
    }

    public String getSediu_dataAct() {
        return sediu_dataAct;
    }

    public void setSediu_dataAct(String sediu_dataAct) {
        this.sediu_dataAct = sediu_dataAct;
    }

    public String getSediu_tipAct() {
        return sediu_tipAct;
    }

    public void setSediu_tipAct(String sediu_tipAct) {
        this.sediu_tipAct = sediu_tipAct;
    }

    public String getSediu_tipDurataAct() {
        return sediu_tipDurataAct;
    }

    public void setSediu_tipDurataAct(String sediu_tipDurataAct) {
        this.sediu_tipDurataAct = sediu_tipDurataAct;
    }

    public Object getSediu_nrAct() {
        return sediu_nrAct;
    }

    public void setSediu_nrAct(Object sediu_nrAct) {
        this.sediu_nrAct = sediu_nrAct;
    }

    public String getTip_Capital() {
        return tip_Capital;
    }

    public void setTip_Capital(String tip_Capital) {
        this.tip_Capital = tip_Capital;
    }

    public String getNumarInregistrare() {
        return numarInregistrare;
    }

    public void setNumarInregistrare(String numarInregistrare) {
        this.numarInregistrare = numarInregistrare;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getcAEN_Descr() {
        return cAEN_Descr;
    }

    public void setcAEN_Descr(String cAEN_Descr) {
        this.cAEN_Descr = cAEN_Descr;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getNumarul() {
        return numarul;
    }

    public void setNumarul(String numarul) {
        this.numarul = numarul;
    }

    public String getcUI() {
        return cUI;
    }

    public void setcUI(String cUI) {
        this.cUI = cUI;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getcAEN() {
        return cAEN;
    }

    public void setcAEN(String cAEN) {
        this.cAEN = cAEN;
    }

    public String getEtaj() {
        return etaj;
    }

    public void setEtaj(String etaj) {
        this.etaj = etaj;
    }

    public String getDataExpirareSediu_PanaLa() {
        return dataExpirareSediu_PanaLa;
    }

    public void setDataExpirareSediu_PanaLa(String dataExpirareSediu_PanaLa) {
        this.dataExpirareSediu_PanaLa = dataExpirareSediu_PanaLa;
    }

    public Object getFormaProprietate() {
        return formaProprietate;
    }

    public void setFormaProprietate(Object formaProprietate) {
        this.formaProprietate = formaProprietate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getScara() {
        return scara;
    }

    public void setScara(Object scara) {
        this.scara = scara;
    }

    public Object getBloc() {
        return bloc;
    }

    public void setBloc(Object bloc) {
        this.bloc = bloc;
    }

    public String getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
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

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getNumeCompanie() {
        return numeCompanie;
    }

    public void setNumeCompanie(String numeCompanie) {
        this.numeCompanie = numeCompanie;
    }

    public Object getWeb() {
        return web;
    }

    public void setWeb(Object web) {
        this.web = web;
    }

    public SediiSecundare getSediiSecundare() {
        return sediiSecundare;
    }

    public void setSediiSecundare(SediiSecundare sediiSecundare) {
        this.sediiSecundare = sediiSecundare;
    }

    public String getDataInfiintarii() {
        return dataInfiintarii;
    }

    public void setDataInfiintarii(String dataInfiintarii) {
        this.dataInfiintarii = dataInfiintarii;
    }

    public String getDataExpirareSediu_DeLa() {
        return dataExpirareSediu_DeLa;
    }

    public void setDataExpirareSediu_DeLa(String dataExpirareSediu_DeLa) {
        this.dataExpirareSediu_DeLa = dataExpirareSediu_DeLa;
    }

    public String getVerActivPrinc() {
        return verActivPrinc;
    }

    public void setVerActivPrinc(String verActivPrinc) {
        this.verActivPrinc = verActivPrinc;
    }

    public String getDataActConstitutiv() {
        return dataActConstitutiv;
    }

    public void setDataActConstitutiv(String dataActConstitutiv) {
        this.dataActConstitutiv = dataActConstitutiv;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
public class Root{
    public String id;
    public String type;
    public String created;
    public String updated;
    public FactoringRequest factoringRequest;
    public Values values;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public FactoringRequest getFactoringRequest() {
        return factoringRequest;
    }

    public void setFactoringRequest(FactoringRequest factoringRequest) {
        this.factoringRequest = factoringRequest;
    }

    public Values getValues() {
        return values;
    }

    public void setValues(Values values) {
        this.values = values;
    }
}


