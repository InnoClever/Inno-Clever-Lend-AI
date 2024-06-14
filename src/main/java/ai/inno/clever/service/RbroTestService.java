package ai.inno.clever.service;

import ai.inno.clever.domain.rbrotest.*;
import ai.inno.clever.domain.rbrotest.mapper.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.okhttp3.*;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RbroTestService {

    private final Logger log = LoggerFactory.getLogger(RbroTestService.class);

//    private String url = "https://rbro-dev.ifactor.ai";

    @Value("${application.rbroUrl}")
    private String url;
    private String API_KEY = "-79zY34HU6eFYSPPE5kEc2UNqpCM0V1rM3L5v3RwD";

    private ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, CollectionContainer[]> createFactoring(AdherentDTO adherentDTO) throws IOException {


        log.info("Adherent is: {}", adherentDTO);

        if (adherentDTO.getDebtors() != null) {

            adherentDTO.getDebtors().stream().forEach(e -> {
                log.info("Debtor is: {}", e);
            });
        }
        try {
            Response res = getAdherentByTaxCode(adherentDTO.getTaxCode());
            if (res.code() != 404) {
                log.info("Tax code {} allready exists!", adherentDTO.getTaxCode());
                return null;
            }
            res.close();

            String adherentInput = objectMapper.writeValueAsString(adherentDTO);
            log.info(adherentInput);

            String aisp = adherentDTO.getAisp();

            String decodedAisp = null;

            if (aisp != null) {
                decodedAisp = new String(Base64.getDecoder().decode(aisp));
            }

            Map<String, DebtorDTO> debtorsMap = new HashMap<>();

            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            Set<DebtorDTO> debtors = adherentDTO.getDebtors();
            debtors.forEach(entry -> {
                debtorsMap.put(entry.getTaxCode(), entry);
            });
            adherentDTO.setDebtors(null);


            log.info("First request: {}", adherentDTO);
            String response = startTestFlow(adherentDTO);
            AdherentDTOWrapper aw = objectMapper.readValue(response, AdherentDTOWrapper.class);
            log.info("FactoringRequestID: " + aw.getFactoringRequest().getId());
            log.info("CUI: " + aw.getAdherent().getTaxCode());
            FactoringRequest factoringRequest = aw.getFactoringRequest();
            String factoringRequestID = factoringRequest.getId();
            factoringRequest.setValue(adherentDTO.getValue());
            factoringRequest.setDebtors(debtors);
            factoringRequest.setCompany(null);
            factoringRequest.setSeqId(null);
//        factoringRequest.setStatus(Status.IN_PROGRESS);
            log.info("Second request: {}", adherentDTO);
            Response responseStatus = addValue(factoringRequestID, factoringRequest);
            if (responseStatus.code() != 200) {
                responseStatus.close();
                throw new IOException("Exception in addValue while getting " + responseStatus.code() + " code. Message: " + responseStatus.message());
            }
            responseStatus.close();
            adherentDTO = aw.getAdherent();
            adherentDTO.setDebtors(debtors);
            String entityId = String.valueOf(factoringRequest.getAdherentId());
            response = getAllFilesOfAdherent(entityId);
            FileInfoDTO[] files = objectMapper.readValue(response, FileInfoDTO[].class);

            if (files.length > 0) {
                aw.setAispType("UPLOAD_AISP");
            }
            else if (files.length == 0 && decodedAisp !=null) {
                aw.setAispType("UPLOAD_AISP");
                try {
                    uploadFiles(entityId, decodedAisp);
                }
                catch (IOException  E) {
                    log.error("No AISP File presented...");
                }
            }
            else {
                aw.setAispType("NO_DATA");
            }
            aw.setFactoringRequest(null);

            response = completeSurveyWithData(factoringRequestID, aw);
            log.info("Response is: {}", response);
//        FactoringRequest factoringRequest = aw.getFactoringRequest();
//        String factoringRequestID = factoringRequest.getId();
//        factoringRequest.setValue(adherentDTO.getValue());
//        factoringRequest.setDebtors(debtors);
        }
        catch (Exception e) {
            log.error("Error while creating new Factroing Request: {}", e);
        }
        return null;
    }


    public Map<String, CollectionContainer[]> start(AdherentDTO adherentDTO) throws IOException, InterruptedException {

        String adherentInput = objectMapper.writeValueAsString(adherentDTO);
        log.info(adherentInput);

        String aisp = adherentDTO.getAisp();

        String decodedAisp = null;

        if (aisp != null) {
            decodedAisp = new String(Base64.getDecoder().decode(aisp));
        }

        Map<String, DebtorDTO> debtorsMap = new HashMap<>();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Set<DebtorDTO> debtors = adherentDTO.getDebtors();
        debtors.forEach(entry-> {
            debtorsMap.put(entry.getTaxCode(), entry);
        });
        adherentDTO.setDebtors(null);

        String response = startTestFlow(adherentDTO);
        AdherentDTOWrapper aw = objectMapper.readValue(response, AdherentDTOWrapper.class);
        log.info("FactoringRequestID: " + aw.getFactoringRequest().getId());
        log.info("CUI: " + aw.getAdherent().getTaxCode());
        FactoringRequest factoringRequest = aw.getFactoringRequest();
        String factoringRequestID = factoringRequest.getId();
        factoringRequest.setValue(adherentDTO.getValue());
        factoringRequest.setDebtors(debtors);
        factoringRequest.setCompany(null);
        factoringRequest.setSeqId(null);
//        factoringRequest.setStatus(Status.IN_PROGRESS);
        Response responseStatus = addValue(factoringRequestID, factoringRequest);
        if (responseStatus.code() != 200) {
            responseStatus.close();
            throw new IOException("Exception in addValue while getting " + responseStatus.code() + " code. Message: " + responseStatus.message());
        }
        responseStatus.close();
        adherentDTO = aw.getAdherent();
        adherentDTO.setDebtors(debtors);
        String entityId = String.valueOf(factoringRequest.getAdherentId());
        response = getAllFilesOfAdherent(entityId);
        FileInfoDTO[] files = objectMapper.readValue(response, FileInfoDTO[].class);

        if (files.length > 0) {
            aw.setAispType("UPLOAD_AISP");
        }
        else if (files.length == 0 && decodedAisp !=null) {
            aw.setAispType("UPLOAD_AISP");
            try {
                uploadFiles(entityId, decodedAisp);
            }
            catch (IOException  E) {
                log.error("No AISP File presented...");
            }
        }
        else {
            aw.setAispType("NO_DATA");
        }
        aw.setFactoringRequest(null);


        response = completeSurveyWithData(factoringRequestID, aw);
        Status status = null;
        int totalTime = 0;
        while (totalTime < 30) {
            log.info(String.valueOf(totalTime));
            try {
                responseStatus = status(factoringRequestID);
            }
            catch (Exception e) {
                log.error("Exception in getting Status: " + e.getMessage());
                TimeUnit.SECONDS.sleep(60);
                totalTime++;
                continue;
            }
            if (responseStatus.code() != 200) {
                log.info("Status code from status is: " + responseStatus.code());
                TimeUnit.SECONDS.sleep(60);
                totalTime++;
                continue;
            }
            response = responseStatus.body().string();
            aw = objectMapper.readValue(response, AdherentDTOWrapper.class);
            status = aw.getFactoringRequest().getStatus();
            log.info(String.valueOf(status));
            if (status.equals(Status.NEW) || status.equals(Status.IN_PROGRESS)) {
                TimeUnit.SECONDS.sleep(60);
                totalTime++;
                continue;
            }
            else {
                log.info(String.valueOf(status));
                break;
            }
        }

        Map<String, CollectionContainer[]> map = new HashMap<>();
        response = getSpecificCollection(factoringRequestID, "FRAUD_FLAGS");
        map.put("FRAUD_FLAGS", objectMapper.readValue(response, CollectionContainer[].class));
        log.info("FRAUD_FLAGS: " + response);
        response = getSpecificCollection(factoringRequestID, "TOTAL_ELIGIBILITY_CHECK");
        map.put("TOTAL_ELIGIBILITY_CHECK", objectMapper.readValue(response, CollectionContainer[].class));
        log.info("TOTAL_ELIGIBILITY_CHECK: " + response);
        response = getSpecificCollection(factoringRequestID, "LIMITS");
        map.put("LIMITS", objectMapper.readValue(response, CollectionContainer[].class));
        log.info("LIMITS: " + response);
        response = getSpecificCollection(factoringRequestID, "SUBLIMITS");
        map.put("SUBLIMITS", objectMapper.readValue(response, CollectionContainer[].class));
        log.info("SUBLIMITS: " + response);
        response = getSpecificCollection(factoringRequestID, "PIPELINE_ERROR");
        map.put("PIPELINE_ERROR", objectMapper.readValue(response, CollectionContainer[].class));
        log.info("PIPELINE_ERROR: " + response);

        responseStatus.close();
        return map;
    }

    private String startTestFlow(AdherentDTO adherentDTO) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        MediaType mediaType = MediaType.parse("application/json");
        String bod = objectMapper.writeValueAsString(adherentDTO);
        RequestBody body = RequestBody.create(mediaType, bod);
        Request request = new Request.Builder()
//                .url(url + "/services/core/api/v1/adherents/actions/start-test-flow")
            .url(url + "/services/core/api/flow-test/adherents/actions/start-test-flow")

            .method("POST", body)
            .addHeader("api_key", API_KEY)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        }
        catch (Exception e) {
            log.info("Error in: " + e.getMessage());
            response.close();
        }
        String r = response.body().string();
        response.close();
        return r;
    }

    public Response addValue(String factoringRequestID, FactoringRequest factoringRequest) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        MediaType mediaType = MediaType.parse("application/json");
        String factoringRequestString = objectMapper.writeValueAsString(factoringRequest);
        RequestBody body = RequestBody.create(mediaType, factoringRequestString);
        Request request = new Request.Builder()
//                .url(url + "/services/rbro/api/factoring-requests/microservice/" + factoringRequestID)
            .url(url + "/services/core/api/flow-test/factoring-requests/" + factoringRequestID)
            .method("PUT", body)
            .addHeader("api_key", API_KEY)
//                .addHeader("microservice_key", API_KEY)
            .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        }
        catch (Exception e) {
            log.info("Error in: " + e.getMessage());
            response.close();
        }
        return response;
    }

    public String getAllFilesOfAdherent(String entityId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
//                .url(url + "/services/core/api/files/microservice/file-infos?entityType=ADHERENT&entityId=" + entityId + "&bizType=BANK_TRANSACTIONS")
            .url(url + "/services/core/api/flow-test/file-infos?entityType=ADHERENT&entityId=" + entityId + "&bizType=BANK_TRANSACTIONS")
            .method("GET", null)
            .addHeader("api_key", API_KEY)
//                .addHeader("microservice_key", API_KEY)
            .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public Response getAdherentByTaxCode(String taxCode) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
//                .url(url + "/services/core/api/files/microservice/file-infos?entityType=ADHERENT&entityId=" + entityId + "&bizType=BANK_TRANSACTIONS")
            .url(url + "/services/core/api/flow-test/adherents/taxCode/" + taxCode)
            .method("GET", null)
            .addHeader("api_key", API_KEY)
//                .addHeader("microservice_key", API_KEY)
            .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public String completeSurveyWithData(String factoringRequestID, AdherentDTOWrapper adherentDTOWrapper) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
        MediaType mediaType = MediaType.parse("application/json");
        String adherentDTOString = objectMapper.writeValueAsString(adherentDTOWrapper);
        log.info(adherentDTOString);
        RequestBody body = RequestBody.create(mediaType, adherentDTOString);
        Request request = new Request.Builder()
//                .url(url + "/services/core/api/v1/adherents/microservice/actions/complete-survey/" + factoringRequestID)
            .url(url + "/services/core/api/flow-test/adherents/actions/complete-survey/" + factoringRequestID)
            .method("POST", body)
            .addHeader("api_key", API_KEY)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = client.newCall(request).execute();
        String r = response.body().string();
        return r;
    }

    public Response status(String factoringRequestID) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
            .url(url + "/services/rbro/api/pipeline/microservice/status/" + factoringRequestID)
            .method("GET", null)
            .addHeader("api_key", API_KEY)
            .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public String getSpecificCollection(String factoringRequestID, String collectionType) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        Request request = new Request.Builder()
            .url(url + "/services/rbro/api/data-collector/collection-containers-filter?requestId=" + factoringRequestID + "&type=" + collectionType)
            .method("GET", null)
            .addHeader("api_key", API_KEY)
            .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public void uploadFiles(String entityId, String aisp) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("file", aisp)
            .addFormDataPart("entityId",entityId)
            .addFormDataPart("entityType","ADHERENT")
            .addFormDataPart("bizType","BANK_TRANSACTIONS")
            .addFormDataPart("data", "{\"id\":null,\"bank\":\"Banca UPLOAD\",\"country\":\"Romania\",\"notes\":\"ro\"}")
            .build();
        Request request = new Request.Builder()
//                .url(url + "/services/core/api/files/upload?entityType=ADHERENT&entityId=" + entityId)
            .url(url + "/services/core/api/flow-test/file-infos/upload?entityType=ADHERENT&entityId=" + entityId)
            .method("POST", body)
            .addHeader("api_key", API_KEY)
            .build();
        Response response = client.newCall(request).execute();
    }
}
