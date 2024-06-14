package ai.inno.clever.service.flowable.util;

public class FlowableInitialization {
    //    public static Variables initializeProcess(SellerApplicationDTO sellerApplicationDTO, List<OpenApiBalance> balances)
    //        throws IllegalAccessException {
    //        Variables variables = new Variables();
    //        // Add all application fields variables
    //        variables.addClassVariables(sellerApplicationDTO);
    //        // Add financial data to the seller application
    //        addBalancesDataToVariables(balances, variables);
    //        // Add Application ID variable
    //        FlowableVariableDTO var0 = new FlowableVariableDTO("entityId", sellerApplicationDTO.getId().toString());
    //        variables.add(var0);
    //        // Remove ID variable otherwise will clash with Flowable ProcessID
    //        variables.removeIdVariable();
    //
    //        return variables;
    //    }
    //
    //    public static Variables initializeProcess(SellerApplicationDTO sellerApplicationDTO) throws IllegalAccessException {
    //        Variables variables = new Variables();
    //        // Add all application fields variables
    //        variables.addClassVariables(sellerApplicationDTO);
    //        // Add Application ID variable
    //        FlowableVariableDTO var0 = new FlowableVariableDTO("entityId", sellerApplicationDTO.getId().toString());
    //        variables.add(var0);
    //        // Remove ID variable otherwise will clash with Flowable ProcessID
    //        variables.removeIdVariable();
    //
    //        return variables;
    //    }
    //
    //    public static Variables initializeProcess(BuyerApplicationDTO buyerApplicationDTO) throws IllegalAccessException {
    //        Variables variables = new Variables();
    //        variables.addClassVariables(buyerApplicationDTO);
    //        // Add Application ID variable
    //        FlowableVariableDTO var0 = new FlowableVariableDTO("entityId", buyerApplicationDTO.getId().toString());
    //        variables.add(var0);
    //        // Remove ID variable otherwise will clash with Flowable ProcessID
    //        variables.removeIdVariable();
    //        return variables;
    //    }
    //
    //    public static Variables initializeProcess(InvoiceDTO invoice) throws IllegalAccessException {
    //        Variables variables = new Variables();
    //        // Add all application fields variables
    //        variables.addClassVariables(invoice);
    //        // Add Application ID variable
    //        FlowableVariableDTO var0 = new FlowableVariableDTO("entityId", invoice.getId().toString());
    //        variables.add(var0);
    //        // Remove ID variable otherwise will clash with Flowable ProcessID
    //        variables.removeIdVariable();
    //        return variables;
    //    }
    //
    //    public static Variables initializeProcess(AuctionDTO auction) throws IllegalAccessException {
    //        Variables variables = new Variables();
    //        // Add all invoice fields variables
    //        variables.addClassVariables(auction);
    //        // Add Application ID variable
    //        FlowableVariableDTO var0 = new FlowableVariableDTO("entityId", auction.getId().toString());
    //        variables.add(var0);
    //        // Remove ID variable otherwise will clash with Flowable ProcessID
    //        variables.removeIdVariable();
    //        return variables;
    //    }
    //
    //    private static void addBalancesDataToVariables(List<OpenApiBalance> balances, Variables variables) throws IllegalAccessException {
    //        if (balances != null) {
    //            for (int i = 1; i <= balances.size(); i++) {
    //                if (i > LOOK_YEARS_BACK) break;
    //                int yearIndex = balances.size() - i;
    //                OpenApiBalance balanceData = balances.get(yearIndex);
    //                // Create process variables from saved FinancialInfo
    //                String prefix = i == 1 ? "lastFS" : String.format("last%dFS", i);
    //                FinancialInfoDTO financialInfoDTO = OpenAPIMapper.toFinancialInfo(balanceData);
    //                variables.addClassVariables(financialInfoDTO, prefix);
    //            }
    //        }
    //    }
}
