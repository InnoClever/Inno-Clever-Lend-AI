package ai.inno.clever.service.dto.utils;

import java.util.Arrays;
import java.util.List;

public class AccountEligibilityConstants {
    //Company Payment Incidents Tip Values
    public static String MOTIVE_MAJORE = "- motive majore";
    public static String MOTIVE_MINORE = "- motive minore";
    public static String TOTAL_SUMA_REFUZATA = "total suma refuzata din care:";
    public static String TOTAL_NR_INSTRUMENTE = "total nr.instrumente din care:";
    public static String TOTAL_SUMA_DE_PLATA = "total suma de plata din care:";

    //Outstanding debts to tax authorities
    public static double euroComparedAmount = 50000D;
    public static double euroValueInLei = 5D;


    public static List<String> getCompanyPaymentIncidentsTipValues(){
        return Arrays.asList(
            MOTIVE_MAJORE,
            MOTIVE_MINORE,
            TOTAL_SUMA_REFUZATA,
            TOTAL_NR_INSTRUMENTE,
            TOTAL_SUMA_DE_PLATA
        );
    }

    public static double getOutstandingDebtsMaxAmount(){
        return euroComparedAmount*euroValueInLei;
    }
}
