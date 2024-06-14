package ai.inno.clever.service.utils;

import ai.inno.clever.service.dto.aisp.Account;
import ai.inno.clever.service.dto.aisp.AispDTO;
import ai.inno.clever.service.dto.aisp.Bank;
import ai.inno.clever.service.dto.aisp.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

public class CSVToAISP {


//    public static AispDTO csvToAISP(String csvStrinbg) {
//             CSVParser csvParser = new CSVParser(csvStrinbg,
//                 CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withAllowMissingColumnNames());
//
//            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
//
//
//            Bank bank = new Bank();
//
//            HashMap<Account, List<Transaction>> testMap = new HashMap<>();
//
//            Set<String> headerNames = new HashSet<>(csvParser.getHeaderNames());
//
//            int i = 0;
//            for (CSVRecord csvRecord : csvRecords) {
//                i++;
//                bank = csvRecordToBank(csvRecord);
//                Transaction transaction = csvRecordToTransaction(csvRecord, headerNames);
//                Account account = csvRecordToAccount(csvRecord);
//
//                List<Transaction> transactionsSoFar = testMap.getOrDefault(account, new ArrayList<>());
//                transactionsSoFar.add(transaction);
//
//                testMap.put(account, transactionsSoFar);
//            }
//
//            AispDTO aispDTO = new AispDTO();
//            aispDTO.addBank(bank);
//
//            for (Map.Entry<Account, List<Transaction>> pair : testMap.entrySet()) {
//                Account acc = pair.getKey();
//                acc.setTransactions(pair.getValue());
//                bank.addAccount(acc);
//            }
//
//            aispDTO.setBanks(Collections.singletonList(bank));
//
//            return aispDTO;
//    }



    public static AispDTO csvToAISP(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                 CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withAllowMissingColumnNames())) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();


            Bank bank = new Bank();

            HashMap<Account, List<Transaction>> testMap = new HashMap<>();

            Set<String> headerNames = new HashSet<>(csvParser.getHeaderNames());

            int i = 0;
            for (CSVRecord csvRecord : csvRecords) {
                i++;
                bank = csvRecordToBank(csvRecord);
                Transaction transaction = csvRecordToTransaction(csvRecord, headerNames);
                Account account = csvRecordToAccount(csvRecord);

                List<Transaction> transactionsSoFar = testMap.getOrDefault(account, new ArrayList<>());
                transactionsSoFar.add(transaction);

                testMap.put(account, transactionsSoFar);
            }

            AispDTO aispDTO = new AispDTO();
            aispDTO.addBank(bank);

            for (Map.Entry<Account, List<Transaction>> pair : testMap.entrySet()) {
                Account acc = pair.getKey();
                acc.setTransactions(pair.getValue());
                bank.addAccount(acc);
            }

            aispDTO.setBanks(Collections.singletonList(bank));

            return aispDTO;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }




    private static Account csvRecordToAccount(CSVRecord csvRecord) {
        Account account = new Account();
        if (csvRecord.get("account__id").length() != 0) {
            account.setId(csvRecord.get("account__id"));
        }
        if (csvRecord.get("account__name").length() != 0) {
            account.setName(csvRecord.get("account__name"));
        }
        if (csvRecord.get("account__balance").length() != 0) {
            account.setBalance(BigDecimal.valueOf(Double.parseDouble(csvRecord.get("account__balance"))));
        }
        if (csvRecord.get("account__currency").length() != 0) {
            account.setCurrency(csvRecord.get("account__currency"));
        }
        if (csvRecord.get("account__accountHolderType").length() != 0) {
            account.setAccountHolderType(csvRecord.get("account__accountHolderType"));
        }
        if (csvRecord.get("account__accountType").length() != 0) {
            account.setAccountType(csvRecord.get("account__accountType"));
        }
        if (csvRecord.get("account__connectionId").length() != 0) {
            account.setConnectionId(csvRecord.get("account__connectionId"));
        }
        if (csvRecord.get("account__aliasName").length() != 0) {
            account.setAliasName(csvRecord.get("account__aliasName"));
        }
        if (csvRecord.get("account__holderName").length() != 0) {
            account.setHolderName(csvRecord.get("account__holderName"));
        }
        if (csvRecord.get("account__accountNumber").length() != 0) {
            account.setAccountNumber(csvRecord.get("account__accountNumber"));
        }
        if (csvRecord.get("account__providerName").length() != 0) {
            account.setProviderName(csvRecord.get("account__providerName"));
        }
        if (csvRecord.get("account__iban").length() != 0) {
            account.setIban(csvRecord.get("account__iban"));
        }
        if (csvRecord.get("account__sortCode").length() != 0) {
            account.setSortCode(csvRecord.get("account__sortCode"));
        }
        if (csvRecord.get("account__accountName").length() != 0) {
            account.setAccountName(csvRecord.get("account__accountName"));
        }
        return account;
    }

    private static Transaction csvRecordToTransaction(CSVRecord csvRecord, Set<String> headerNames) {
        Transaction transaction = new Transaction();
        if (csvRecord.get("id").length() != 0) {
            transaction.setId(csvRecord.get("id"));
        }
        if (csvRecord.get("accountId").length() != 0) {
            transaction.setAccountId(csvRecord.get("accountId"));
        }
        if (csvRecord.get("duplicated").length() != 0) {
            transaction.setDuplicated(Boolean.parseBoolean(csvRecord.get("duplicated")));
        }
        if (csvRecord.get("mode").length() != 0) {
            transaction.setMode(csvRecord.get("mode"));
        }
        if (csvRecord.get("madeOn").length() != 0) {
            transaction.setMadeOn(csvRecord.get("madeOn"));
        }
        if (csvRecord.get("description").length() != 0) {
            transaction.setDescription(csvRecord.get("description"));
        }
        if (csvRecord.get("category").length() != 0) {
            transaction.setCategory(csvRecord.get("category"));
        }
        if (csvRecord.get("amount").length() != 0) {
            transaction.setAmount(csvRecord.get("amount"));
        }
        if (csvRecord.get("status").length() != 0) {
            transaction.setStatus(csvRecord.get("status"));
        }
        if (csvRecord.get("currencyCode").length() != 0) {
            transaction.setCurrencyCode(csvRecord.get("currencyCode"));
        }
        if (csvRecord.get("bookingDateTime").length() != 0) {
            transaction.setBookingDateTime(csvRecord.get("bookingDateTime"));
        }
        if (csvRecord.get("creditDebitIndicator").length() != 0) {
            transaction.setCreditDebitIndicator(csvRecord.get("creditDebitIndicator"));
        }
        if (csvRecord.get("createdAt").length() != 0) {
            transaction.setCreatedAt(csvRecord.get("createdAt"));
        }
        if (csvRecord.get("updatedAt").length() != 0) {
            transaction.setUpdatedAt(csvRecord.get("updatedAt"));
        }


        Map<String, String> extras = new HashMap<>();
        if (headerNames.contains("extra__account_balance_snapshot") && csvRecord.get("extra__account_balance_snapshot").length() != 0) {
            extras.put("account_balance_snapshot", csvRecord.get("extra__account_balance_snapshot"));
        }
        if (headerNames.contains("extra__account_number") && csvRecord.get("extra__account_number").length() != 0) {
            extras.put("account_number", csvRecord.get("extra__account_number"));
        }
        if (headerNames.contains("extra__additional") && csvRecord.get("extra__additional").length() != 0) {
            extras.put("additional", csvRecord.get("extra__additional"));
        }
        if (headerNames.contains("extra__asset_amount") && csvRecord.get("extra__asset_amount").length() != 0) {
            extras.put("asset_amount", csvRecord.get("extra__asset_amount"));
        }
        if (headerNames.contains("extra__asset_code") && csvRecord.get("extra__asset_code").length() != 0) {
            extras.put("asset_code", csvRecord.get("extra__asset_code"));
        }
        if (headerNames.contains("extra__categorization_confidence") && csvRecord.get("extra__categorization_confidence").length() != 0) {
            extras.put("categorization_confidence", csvRecord.get("extra__categorization_confidence"));
        }
        if (headerNames.contains("extra__check_number") && csvRecord.get("extra__check_number").length() != 0) {
            extras.put("check_number", csvRecord.get("extra__check_number"));
        }
        if (headerNames.contains("extra__closing_balance") && csvRecord.get("extra__closing_balance").length() != 0) {
            extras.put("closing_balance", csvRecord.get("extra__closing_balance"));
        }
        if (headerNames.contains("extra__constant_code") && csvRecord.get("extra__constant_code").length() != 0) {
            extras.put("constant_code", csvRecord.get("extra__constant_code"));
        }
        if (headerNames.contains("extra__convert") && headerNames.contains("extra__convert") && csvRecord.get("extra__convert").length() != 0) {
            extras.put("convert", csvRecord.get("extra__convert"));
        }
        if (headerNames.contains("extra__customer_category_name") && csvRecord.get("extra__customer_category_name").length() != 0) {
            extras.put("customer_category_name", csvRecord.get("extra__customer_category_name"));
        }
        if (headerNames.contains("extra__exchange_rate") && csvRecord.get("extra__exchange_rate").length() != 0) {
            extras.put("exchange_rate", csvRecord.get("extra__exchange_rate"));
        }
        if (headerNames.contains("extra__id") && csvRecord.get("extra__id").length() != 0) {
            extras.put("id", csvRecord.get("extra__id"));
        }
        if (headerNames.contains("extra__end_to_end_id") && csvRecord.get("extra__end_to_end_id").length() != 0) {
            extras.put("end_to_end_id", csvRecord.get("extra__end_to_end_id"));
        }
        if (headerNames.contains("extra__information") && csvRecord.get("extra__information").length() != 0) {
            extras.put("information", csvRecord.get("extra__information"));
        }
        if (headerNames.contains("extra__mcc") && csvRecord.get("extra__mcc").length() != 0) {
            extras.put("mcc", csvRecord.get("extra__mcc"));
        }
        if (headerNames.contains("extra__merchant_id") && csvRecord.get("extra__merchant_id").length() != 0) {
            extras.put("merchant_id", csvRecord.get("extra__merchant_id"));
        }
        if (headerNames.contains("extra__opening_balance") && csvRecord.get("extra__opening_balance").length() != 0) {
            extras.put("opening_balance", csvRecord.get("extra__opening_balance"));
        }
        if (headerNames.contains("extra__installment_debt_amount") && csvRecord.get("extra__installment_debt_amount").length() != 0) {
            extras.put("installment_debt_amount", csvRecord.get("extra__installment_debt_amount"));
        }
        if (headerNames.contains("extra__original_amount") && csvRecord.get("extra__original_amount").length() != 0) {
            extras.put("original_amount", csvRecord.get("extra__original_amount"));
        }
        if (headerNames.contains("extra__original_category") && csvRecord.get("extra__original_category").length() != 0
        ) {
            extras.put("original_category", csvRecord.get("extra__original_category"));
        }
        if (headerNames.contains("extra__original_currency_code") && csvRecord.get("extra__original_currency_code").length() != 0) {
            extras.put("original_currency_code", csvRecord.get("extra__original_currency_code"));
        }
        if (headerNames.contains("extra__original_subcategory") && csvRecord.get("extra__original_subcategory").length() != 0) {
            extras.put("original_subcategory", csvRecord.get("extra__original_subcategory"));
        }
        if (headerNames.contains("extra__payee") && csvRecord.get("extra__payee").length() != 0 && headerNames.contains("")) {
            extras.put("original_payee", csvRecord.get("extra__payee"));
        }
        if (headerNames.contains("extra__payee_information") && csvRecord.get("extra__payee_information").length() != 0) {
            extras.put("payee_information", csvRecord.get("extra__payee_information"));
        }
        if (headerNames.contains("extra__payer") && csvRecord.get("extra__payer").length() != 0 && headerNames.contains("")) {
            extras.put("payer", csvRecord.get("extra__payer"));
        }
        if (headerNames.contains("extra__payer_information") && csvRecord.get("extra__payer_information").length() != 0) {
            extras.put("payer_information", csvRecord.get("extra__payer_information"));
        }
        if (headerNames.contains("extra__possible_duplicate") && csvRecord.get("extra__possible_duplicate").length() != 0) {
            extras.put("possible_duplicate", csvRecord.get("extra__possible_duplicate"));
        }
        if (headerNames.contains("extra__posting_date") && csvRecord.get("extra__posting_date").length() != 0) {
            extras.put("posting_date", csvRecord.get("extra__posting_date"));
        }
        if (headerNames.contains("extra__posting_time") && csvRecord.get("extra__posting_time").length() != 0) {
            extras.put("posting_time", csvRecord.get("extra__posting_time"));
        }
        if (headerNames.contains("extra__record_number") && csvRecord.get("extra__record_number").length() != 0) {
            extras.put("record_number", csvRecord.get("extra__record_number"));
        }
        if (headerNames.contains("extra__specific_code") && csvRecord.get("extra__specific_code").length() != 0) {
            extras.put("specific_code", csvRecord.get("extra__specific_code"));
        }
        if (headerNames.contains("extra__tags") && csvRecord.get("extra__tags").length() != 0) {
            extras.put("tags", csvRecord.get("extra__tags"));
        }
        if (headerNames.contains("extra__time") && csvRecord.get("extra__time").length() != 0) {
            extras.put("time", csvRecord.get("extra__time"));
        }
        if (headerNames.contains("extra__transfer_account_name") && csvRecord.get("extra__transfer_account_name").length() != 0) {
            extras.put("transfer_account_name", csvRecord.get("extra__transfer_account_name"));
        }
        if (headerNames.contains("extra__type") && csvRecord.get("extra__type").length() != 0) {
            extras.put("type", csvRecord.get("extra__type"));
        }
        if (headerNames.contains("extra__unit_price") && csvRecord.get("extra__unit_price").length() != 0) {
            extras.put("unit_price", csvRecord.get("extra__unit_price"));
        }
        if (headerNames.contains("extra__units") && csvRecord.get("extra__units").length() != 0) {
            extras.put("units", csvRecord.get("extra__units"));
        }
        if (headerNames.contains("extra__variable_code") && csvRecord.get("extra__variable_code").length() != 0) {
            extras.put("variable_code", csvRecord.get("extra__variable_code"));
        }
        transaction.setExtra(extras);
        return transaction;
    }

    private static Bank csvRecordToBank(CSVRecord csvRecord) {
        Bank bank = new Bank();
        if (csvRecord.get("bank__bankId").length() != 0) {
            bank.setBankId(csvRecord.get("bank__bankId"));
        }
        if (csvRecord.get("bank__bankName").length() != 0) {
            bank.setBankName(csvRecord.get("bank__bankName"));
        }
        if (csvRecord.get("bank__country").length() != 0) {
            bank.setCountry(csvRecord.get("bank__country"));
        }
        return bank;
    }

}
