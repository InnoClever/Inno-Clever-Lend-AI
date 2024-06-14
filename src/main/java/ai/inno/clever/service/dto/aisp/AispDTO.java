package ai.inno.clever.service.dto.aisp;

import java.util.ArrayList;
import java.util.List;

public class AispDTO {

    private List<Bank> banks = new ArrayList<>();

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }

    public List<Bank> addBank(Bank bank){
        this.banks.add(bank);
        return this.banks;
    }


    public List<Bank> addBanks(List<Bank> banks){
        this.banks.addAll(banks);
        return this.banks;
    }


}
