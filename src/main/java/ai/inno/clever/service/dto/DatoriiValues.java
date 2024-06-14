package ai.inno.clever.service.dto;

public class DatoriiValues {
    private String FaraObligatiiRestante;
    private DatoriiData DatoriiDinAsigurariDeSomaj;
    private DatoriiData DatoriiDinAsigurariSociale;
    private DatoriiData DatoriiLaBugetulDeStat;
    private DatoriiData DatoriiDinAsigurariDeSanatate;

    private String CUI;
    private String isReport;

    public String getFaraObligatiiRestante() {
        return FaraObligatiiRestante;
    }

    public void setFaraObligatiiRestante(String faraObligatiiRestante) {
        FaraObligatiiRestante = faraObligatiiRestante;
    }

    public DatoriiData getDatoriiDinAsigurariDeSomaj() {
        return DatoriiDinAsigurariDeSomaj;
    }

    public void setDatoriiDinAsigurariDeSomaj(DatoriiData datoriiDinAsigurariDeSomaj) {
        DatoriiDinAsigurariDeSomaj = datoriiDinAsigurariDeSomaj;
    }

    public DatoriiData getDatoriiDinAsigurariSociale() {
        return DatoriiDinAsigurariSociale;
    }

    public void setDatoriiDinAsigurariSociale(DatoriiData datoriiDinAsigurariSociale) {
        DatoriiDinAsigurariSociale = datoriiDinAsigurariSociale;
    }

    public DatoriiData getDatoriiLaBugetulDeStat() {
        return DatoriiLaBugetulDeStat;
    }

    public void setDatoriiLaBugetulDeStat(DatoriiData datoriiLaBugetulDeStat) {
        DatoriiLaBugetulDeStat = datoriiLaBugetulDeStat;
    }

    public String getCUI() {
        return CUI;
    }

    public void setCUI(String CUI) {
        this.CUI = CUI;
    }

    public String getIsReport() {
        return isReport;
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport;
    }

    public DatoriiData getDatoriiDinAsigurariDeSanatate() {
        return DatoriiDinAsigurariDeSanatate;
    }

    public void setDatoriiDinAsigurariDeSanatate(DatoriiData datoriiDinAsigurariDeSanatate) {
        DatoriiDinAsigurariDeSanatate = datoriiDinAsigurariDeSanatate;
    }

    @Override
    public String toString() {
        return "DatoriiValues{" +
            "FaraObligatiiRestante='" + FaraObligatiiRestante + '\'' +
            ", DatoriiDinAsigurariDeSomaj=" + DatoriiDinAsigurariDeSomaj +
            ", DatoriiDinAsigurariSociale=" + DatoriiDinAsigurariSociale +
            ", DatoriiLaBugetulDeStat=" + DatoriiLaBugetulDeStat +
            ", CUI='" + CUI + '\'' +
            ", isReport='" + isReport + '\'' +
            ", DatoriiDinAsigurariDeSanatate=" + DatoriiDinAsigurariDeSanatate +
            '}';
    }

    public static class DatoriiData {
        private Row row;

        public Row getRow() {
            return row;
        }

        public void setRow(Row row) {
            this.row = row;
        }

        @Override
        public String toString() {
            return "DatoriiData{" +
                "row=" + row +
                '}';
        }

        public static class Row {
            private String Luna;
            private String Anul;
            private String ObligatiiFiscaleRestantePrincipale;
            private String ObligatiiFiscaleAccesorii;
            private String TotalObligatiiFiscale;
            private String ObligatiiFiscaleNecontestate;
            private String ObligatiiFiscaleContestate;
            private String Observatii;

            public String getLuna() {
                return Luna;
            }

            public void setLuna(String luna) {
                Luna = luna;
            }

            public String getAnul() {
                return Anul;
            }

            public void setAnul(String anul) {
                Anul = anul;
            }

            public String getObligatiiFiscaleRestantePrincipale() {
                return ObligatiiFiscaleRestantePrincipale;
            }

            public void setObligatiiFiscaleRestantePrincipale(String obligatiiFiscaleRestantePrincipale) {
                ObligatiiFiscaleRestantePrincipale = obligatiiFiscaleRestantePrincipale;
            }

            public String getObligatiiFiscaleAccesorii() {
                return ObligatiiFiscaleAccesorii;
            }

            public void setObligatiiFiscaleAccesorii(String obligatiiFiscaleAccesorii) {
                ObligatiiFiscaleAccesorii = obligatiiFiscaleAccesorii;
            }

            public String getTotalObligatiiFiscale() {
                return TotalObligatiiFiscale;
            }

            public void setTotalObligatiiFiscale(String totalObligatiiFiscale) {
                TotalObligatiiFiscale = totalObligatiiFiscale;
            }

            public String getObligatiiFiscaleNecontestate() {
                return ObligatiiFiscaleNecontestate;
            }

            public void setObligatiiFiscaleNecontestate(String obligatiiFiscaleNecontestate) {
                ObligatiiFiscaleNecontestate = obligatiiFiscaleNecontestate;
            }

            public String getObligatiiFiscaleContestate() {
                return ObligatiiFiscaleContestate;
            }

            public void setObligatiiFiscaleContestate(String obligatiiFiscaleContestate) {
                ObligatiiFiscaleContestate = obligatiiFiscaleContestate;
            }

            public String getObservatii() {
                return Observatii;
            }

            public void setObservatii(String observatii) {
                Observatii = observatii;
            }

            @Override
            public String toString() {
                return "Row{" +
                    "Luna='" + Luna + '\'' +
                    ", Anul='" + Anul + '\'' +
                    ", ObligatiiFiscaleRestantePrincipale='" + ObligatiiFiscaleRestantePrincipale + '\'' +
                    ", ObligatiiFiscaleAccesorii='" + ObligatiiFiscaleAccesorii + '\'' +
                    ", TotalObligatiiFiscale='" + TotalObligatiiFiscale + '\'' +
                    ", ObligatiiFiscaleNecontestate='" + ObligatiiFiscaleNecontestate + '\'' +
                    ", ObligatiiFiscaleContestate='" + ObligatiiFiscaleContestate + '\'' +
                    ", Observatii='" + Observatii + '\'' +
                    '}';
            }
        }
    }
}
