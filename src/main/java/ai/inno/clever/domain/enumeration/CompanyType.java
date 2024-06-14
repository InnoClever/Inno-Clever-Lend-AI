package ai.inno.clever.domain.enumeration;

/**
 * The CompanyType enumeration.
 */
public enum CompanyType {
    II("II"),
    IF("IF"),
    PFA("PFA"),
    SCA("SCA"),
    SCS("SCS"),
    SNC("SNC"),
    SRL("SRL"),
    SRLD("SRLD"),
    SA("SA");

    private String text;

    CompanyType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static CompanyType fromString(String text) {
        for (CompanyType b : CompanyType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
