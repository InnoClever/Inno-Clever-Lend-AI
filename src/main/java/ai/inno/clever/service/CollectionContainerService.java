package ai.inno.clever.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import ai.inno.clever.domain.CollectionContainer;
import ai.inno.clever.domain.FactoringRequest;
import ai.inno.clever.repository.CollectionContainerRepository;
import ai.inno.clever.repository.search.CollectionContainerSearchRepository;
import ai.inno.clever.service.dto.AdherentEligibilityChecks;
import ai.inno.clever.service.dto.DatoriiValues;
import ai.inno.clever.service.dto.core.AdherentDTO;
import ai.inno.clever.service.dto.utils.AccountEligibilityConstants;
import ai.inno.clever.service.feign.CoreService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import ai.innoclever.config.ApplicationProperties;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import ai.innoclever.service.mail.EmailService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link CollectionContainer}.
 */
@Service
public class CollectionContainerService {

    private final Logger log = LoggerFactory.getLogger(CollectionContainerService.class);

    private final CollectionContainerRepository collectionContainerRepository;

    private final CollectionContainerSearchRepository collectionContainerSearchRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Autowired
    private FactoringRequestService factoringRequestService;

    @Autowired
    private CoreService coreService; //restart

    public CollectionContainerService(
        CollectionContainerRepository collectionContainerRepository,
        CollectionContainerSearchRepository collectionContainerSearchRepository
    ) {
        this.collectionContainerRepository = collectionContainerRepository;
        this.collectionContainerSearchRepository = collectionContainerSearchRepository;
    }

    /**
     * Save a collectionContainer.
     *
     * @param collectionContainer the entity to save.
     * @return the persisted entity.
     */
    public CollectionContainer save(CollectionContainer collectionContainer) {
        log.debug("Request to save CollectionContainer : {}", collectionContainer);
        if (collectionContainer.getId() == null) {
            collectionContainer.setCreated(Instant.now());
        }
        collectionContainer.setUpdated(Instant.now());
        if (null != collectionContainer.getFactoringRequest()) {
            collectionContainer.getFactoringRequest().getDebtors().forEach(debtorDTO -> {
                if (null != debtorDTO.getCompanyDetailsJSON()) {
                    debtorDTO.setCompanyDetailsJSON(null);
                }
            });
        }
        CollectionContainer result = collectionContainerRepository.save(collectionContainer);
        collectionContainerSearchRepository.save(result);
        return result;
    }

    public List<CollectionContainer> saveAll(List<CollectionContainer> collectionContainerlist) {
        log.debug("Request to save all CollectionContainer list : {}", collectionContainerlist);
        collectionContainerlist = collectionContainerlist.stream().map(collectionContainer -> {
                if (collectionContainer.getId() == null) {
                    collectionContainer.setCreated(Instant.now());
                }
                collectionContainer.setUpdated(Instant.now());
                collectionContainer.getFactoringRequest().getDebtors().forEach(debtorDTO -> {
                    debtorDTO.setCompanyDetailsJSON(null);
                });
                return collectionContainer;
            })
            .collect(Collectors.toList());

        List<CollectionContainer> result = collectionContainerRepository.saveAll(collectionContainerlist);
        collectionContainerSearchRepository.saveAll(result);
        return result;
    }
    //restart

    /**
     * Partially update a collectionContainer.
     *
     * @param collectionContainer the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CollectionContainer> partialUpdate(CollectionContainer collectionContainer) {
        log.debug("Request to partially update CollectionContainer : {}", collectionContainer);

        return collectionContainerRepository
            .findById(collectionContainer.getId())
            .map(
                existingCollectionContainer -> {
                    if (collectionContainer.getType() != null) {
                        existingCollectionContainer.setType(collectionContainer.getType());
                    }

                    return existingCollectionContainer;
                }
            )
            .map(collectionContainerRepository::save)
            .map(
                savedCollectionContainer -> {
                    savedCollectionContainer.setUpdated(Instant.now());
                    collectionContainerSearchRepository.save(savedCollectionContainer);

                    return savedCollectionContainer;
                }
            );
    }

    /**
     * Get all the collectionContainers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<CollectionContainer> findAll(Pageable pageable) {
        log.debug("Request to get all CollectionContainers");
        return collectionContainerRepository.findAll(pageable);
    }

    /**
     * Get one collectionContainer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CollectionContainer> findOne(String id) {
        log.debug("Request to get CollectionContainer : {}", id);
        return collectionContainerRepository.findById(id);
    }

    public List<CollectionContainer> findByFactoringRequestId(String factoringRequestId) {
        log.debug("Request to get CollectionContainer : {}", factoringRequestId);

        BoolQueryBuilder queryBuilder = boolQuery().must(queryStringQuery("factoringRequest.id:" + factoringRequestId));

        org.springframework.data.elasticsearch.core.query.Query
            query = (new NativeSearchQueryBuilder()).withQuery(queryBuilder).build();

        return collectionContainerSearchRepository.search(query).getContent();

    }
    //
    //    public List<CollectionContainer> findByFieldNameAndFieldValue(String fieldName, String fieldValue) {
    //        log.debug("Request to get CollectionContainer for fieldName {} and fieldValue {}", fieldName, fieldValue);
    //        return collectionContainerRepository.findByValuesFieldInAndValuesValueIn(fieldName, fieldValue);
    //    }

    public List<CollectionContainer> findByFieldNameAndFieldValue(Map<String, String> queryParams) {
        log.debug("Request to get CollectionContainer for fields {}", queryParams);

        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();
        boolean onlyByTypeAndRequestId =  queryParams.size() == 2 && queryParams.containsKey("type") && queryParams.containsKey("requestId");
        if (onlyByTypeAndRequestId) {
            return  findByFactoringRequestIdAndType(queryParams.get("requestId"), queryParams.get("type"));
        }

        boolean onlyByTypeAndRequestIdAnCui =  queryParams.size() == 3
            && queryParams.containsKey("type")
            && queryParams.containsKey("requestId")
            && queryParams.containsKey("CUI");

        if (onlyByTypeAndRequestIdAnCui) {
            return  findByFactoringRequestIdAndTypeAndCUI(queryParams.get("requestId"), queryParams.get("type"), queryParams.get("CUI"));
        }


        boolean onlyByTypeAndRequestIdAnCuiDebtor =  queryParams.size() == 3
            && queryParams.containsKey("type")
            && queryParams.containsKey("requestId")
            && queryParams.containsKey("cui_debtor");

        if (onlyByTypeAndRequestIdAnCuiDebtor) {
            return  findByFactoringRequestIdAndTypeAndCUI(queryParams.get("requestId"), queryParams.get("type"), queryParams.get("cui_debtor"));
        }

        if (queryParams.get("type") != null) {
            Criteria crt = Criteria.where("type").is(queryParams.get("type"));
            criteriaList.add(crt);
            queryParams.remove("type");
        }
        if (queryParams.get("requestId") != null) {
            Criteria crt = Criteria.where("factoringRequest.id").is(queryParams.get("requestId"));
            criteriaList.add(crt);
            queryParams.remove("requestId");
        }
        queryParams.forEach((key, value) -> {
            Criteria crt = Criteria.where(String.format("values.%s", key)).is(value);
            criteriaList.add(crt);
        });

        query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        return mongoOps.find(query, CollectionContainer.class);
    }

    public List<CollectionContainer> findByFactoringRequestIdAndType(String factoringRequestId, String type) {
        log.debug("Request to get CollectionContainer : {}", factoringRequestId);
        BoolQueryBuilder queryBuilder = boolQuery().must(queryStringQuery("factoringRequest.id:" + factoringRequestId))
            .must(queryStringQuery("type:" + type));

        org.springframework.data.elasticsearch.core.query.Query
            query = (new NativeSearchQueryBuilder()).withQuery(queryBuilder).build();

        return collectionContainerSearchRepository.search(query).getContent();

    }

    public List<CollectionContainer> findByFactoringRequestIdAndTypeAndCUI(String factoringRequestId, String type, String cui) {
        log.debug("findByFactoringRequestIdAndTypeAndCUI : {}-{}-{}.", factoringRequestId, type, cui);
        BoolQueryBuilder queryBuilder = boolQuery().must(queryStringQuery("factoringRequest.id:" + factoringRequestId))
            .must(queryStringQuery("type:" + type))
            .must(queryStringQuery("values.CUI:" + cui));

        org.springframework.data.elasticsearch.core.query.Query
            query = (new NativeSearchQueryBuilder()).withQuery(queryBuilder).build();

        return collectionContainerSearchRepository.search(query).getContent();

    }

    public List<CollectionContainer> findByType(String type) {
        log.debug("Request to get CollectionContainer by Type: {}", type);
        return collectionContainerRepository.findAllByType(type);

    }

    public List<CollectionContainer> findByFactoringRequestIdAndTaskId(String factoringRequestId, String taskId) {
        log.debug("Request to get CollectionContainer : {}", factoringRequestId);

        BoolQueryBuilder queryBuilder = boolQuery().must(queryStringQuery("factoringRequest.id:" + factoringRequestId))
            .must(queryStringQuery("taskId:" + taskId));

        org.springframework.data.elasticsearch.core.query.Query
            query = (new NativeSearchQueryBuilder()).withQuery(queryBuilder).build();

        return collectionContainerSearchRepository.search(query).getContent();


    }

    /**
     * Delete the collectionContainer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CollectionContainer : {}", id);
        collectionContainerRepository.deleteById(id);
        collectionContainerSearchRepository.deleteById(id);
    }

    public void deleteAll(List<CollectionContainer> collectionContainerList) {
        log.debug("Request to delete CollectionContainer list : {}", collectionContainerList);
        collectionContainerRepository.deleteAll(collectionContainerList);
        collectionContainerSearchRepository.deleteAll(collectionContainerList);
    }

    //    public void deleteAllByFactoringRequestIdAndTaskId(String requestId, String taskId) {
    //        log.debug("Request to delete CollectionContainer : {}", id);
    //        collectionContainerRepository.deleteById(id);
    //        collectionContainerSearchRepository.deleteById(id);
    //    }

    /**
     * Search for the collectionContainer corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<CollectionContainer> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CollectionContainers for query {}", query);
        return collectionContainerSearchRepository.search(queryStringQuery(query), pageable);
    }

    public AdherentEligibilityChecks getAdherentEligibilityChecks(String factoringRequestId, String cui) {
        FactoringRequest factoringRequest = factoringRequestService.findOne(factoringRequestId).orElseThrow();
        AdherentDTO adherent = coreService.getAdherentV1(factoringRequest.getAdherentId());
        JsonObject jsonObject = new Gson().fromJson(adherent.getCompanyDetailsJSON(), JsonObject.class);
        String status = jsonObject.get("Status").getAsString();
        status = status.replaceAll(";", "");
        AdherentEligibilityChecks eligibilityChecks = new AdherentEligibilityChecks();
        eligibilityChecks.setCompanyStatus(status);
        List<CollectionContainer> companyPaymentIncidents =
            collectionContainerRepository.findAllByFactoringRequestIdAndType(factoringRequestId,
                "COMPANY_PAYMENT_INCIDENTS");
        List<CollectionContainer> companyInsolvencies =
            collectionContainerRepository.findAllByFactoringRequestIdAndType(factoringRequestId,
                "COMPANY_INSOLVENCIES");
        List<CollectionContainer> companyStateDebts =
            collectionContainerRepository.findAllByFactoringRequestIdAndType(factoringRequestId,
                "COMPANY_STATE_DEBTS_NEW");
        List<CollectionContainer> positiveFinancial =
            collectionContainerRepository.findAllByFactoringRequestIdAndType(factoringRequestId, "FINANCIAL");

        //      log.info("FINANCIAL: {}", positiveFinancial);
        try {
            eligibilityChecks.setPositivePaymentIncidents(
                hasCollectionCompanyPaymentIncident(companyPaymentIncidents, cui));
            //          log.info("Check 1");
            eligibilityChecks.setUnderInsolvencyProceedingsBpi(hasCompanyInsolvencies(companyInsolvencies, cui));
            //           log.info("Check 2");
            eligibilityChecks.setOutstandingDebts(hasCompanyStateDebts(companyStateDebts, cui));
            //         log.info("Check 3");
            eligibilityChecks.setPositiveEquity(hasPositiveEquity(positiveFinancial, cui));
            //         log.info("Check 4");
            eligibilityChecks.setPositiveNetProfitYm1(hasPositiveNetProfit(positiveFinancial, cui));
            //         log.info("Check 5");
        } catch (Exception ex) {
            log.info("getAdherentEligibilityChecks Exception: {}", ex.getMessage());
            //   ex.printStackTrace();
        }
        return eligibilityChecks;
    }

    private boolean hasCollectionCompanyPaymentIncident(List<CollectionContainer> collectionContainers, String cui) {
        List<CollectionContainer> newCollectionCotainers = getCollectionContainersWithACui(collectionContainers, cui);
        if (newCollectionCotainers == null)
            return false;
        for (CollectionContainer collectionContainer : newCollectionCotainers) {
            if (!companyPaymentIncidentWithTotalZeroSolvingMap(collectionContainer.getValues())) {
                return false;
            }
        }
        return true;
    }

    private boolean companyPaymentIncidentWithTotalZeroSolvingMap(Map<String, Object> values) {
        if (values.containsKey("root") && values.get("root") instanceof Iterable) {
            List<String> allowedTipValues = AccountEligibilityConstants.getCompanyPaymentIncidentsTipValues();

            for (Object obj : (Iterable<?>) values.get("root")) {
                if (obj instanceof Map) {
                    Map<?, ?> map = (Map<?, ?>) obj;
                    if (map.containsKey("Tip")) {
                        String tipValue = map.get("Tip").toString().toLowerCase(Locale.ROOT);
                        if (allowedTipValues.contains(tipValue)) {
                            if (!checkValueIsZero(map, "Total") ||
                                !checkValueIsZero(map, "CEC") ||
                                !checkValueIsZero(map, "Cambii") ||
                                !checkValueIsZero(map, "BileteOrdin")) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean checkValueIsZero(Map<?, ?> map, String key) {
        Object value = map.get(key);
        log.debug("Check Value Zero: {}, {}", key, value);
        return "0.00".equalsIgnoreCase(value.toString());
    }

    private boolean hasCompanyStateDebts(List<CollectionContainer> collectionContainers, String cui) {
        List<CollectionContainer> newCollectionCotainers = getCollectionContainersWithACui(collectionContainers, cui);
        List<DatoriiValues> datoriiValues = new ArrayList<>();
        //     log.info("newCollectionCotainers {}",newCollectionCotainers);
        for (CollectionContainer collectionContainer : newCollectionCotainers) {
            //          log.info("CollectionContainer collectionContainer : newCollectionCotainers {}", collectionContainer);
            if (null != collectionContainer.getValues() && !collectionContainer.getValues().isEmpty()) {
                //            log.info("collectionContainer.getValues() {}",collectionContainer.getValues());
                Gson gson = new Gson();
                String json = gson.toJson(collectionContainer.getValues());
                Type listType = new TypeToken<DatoriiValues>() {

                }.getType();

                List<DatoriiValues> values = new ArrayList<>();
                values.add(gson.fromJson(json, listType));
                datoriiValues.addAll(values);
            }
        }
        if (datoriiValues.isEmpty()) {
            return false;
        }
        //    log.info("Total Datorii: {}", (solveDatoriiDinAsigurariDeSomaj(datoriiValues) + solveDatoriiDinAsigurariSociale(datoriiValues)
        //      + solveDatoriiLaBugetulDeStat(datoriiValues) + solveDatoriiDinAsigurariDeSanatate(datoriiValues)));
        return (solveDatoriiDinAsigurariDeSomaj(datoriiValues) + solveDatoriiDinAsigurariSociale(datoriiValues)
            + solveDatoriiLaBugetulDeStat(datoriiValues) + solveDatoriiDinAsigurariDeSanatate(datoriiValues)) >
            AccountEligibilityConstants.getOutstandingDebtsMaxAmount();
    } //restart

    private double solveDatoriiDinAsigurariDeSomaj(List<DatoriiValues> datoriiValues) {
        int mostRecentIndex = 0;
        int lastYear = 0;
        int lastMonth = 0;
        for (int i = 0; i < datoriiValues.size(); i++) {
            if (datoriiValues.get(i).getDatoriiDinAsigurariDeSomaj() != null) {
                if (Integer.parseInt(datoriiValues.get(i).getDatoriiDinAsigurariDeSomaj().getRow().getAnul()) > lastYear
                    || (Integer.parseInt(
                    datoriiValues.get(i).getDatoriiDinAsigurariDeSomaj().getRow().getAnul()) == lastYear
                    && Integer.parseInt(
                    datoriiValues.get(i).getDatoriiDinAsigurariDeSomaj().getRow().getLuna()) > lastMonth)) {
                    lastYear =
                        Integer.parseInt(datoriiValues.get(i).getDatoriiDinAsigurariDeSomaj().getRow().getAnul());
                    lastMonth =
                        Integer.parseInt(datoriiValues.get(i).getDatoriiDinAsigurariDeSomaj().getRow().getLuna());
                    mostRecentIndex = i;
                }
            }
        }

        return Double.parseDouble(
            datoriiValues.get(mostRecentIndex).getDatoriiDinAsigurariDeSomaj().getRow().getTotalObligatiiFiscale());
    }

    private Double solveDatoriiDinAsigurariSociale(List<DatoriiValues> datoriiValues) {
        int mostRecentIndex = 0;
        int lastYear = 0;
        int lastMonth = 0;
        for (int i = 0; i < datoriiValues.size(); i++) {
            if (datoriiValues.get(i).getDatoriiDinAsigurariSociale() != null) {
                if (Integer.parseInt(datoriiValues.get(i).getDatoriiDinAsigurariSociale().getRow().getAnul()) > lastYear
                    || (Integer.parseInt(
                    datoriiValues.get(i).getDatoriiDinAsigurariSociale().getRow().getAnul()) == lastYear
                    && Integer.parseInt(
                    datoriiValues.get(i).getDatoriiDinAsigurariSociale().getRow().getLuna()) > lastMonth)) {
                    lastYear =
                        Integer.parseInt(datoriiValues.get(i).getDatoriiDinAsigurariSociale().getRow().getAnul());
                    lastMonth =
                        Integer.parseInt(datoriiValues.get(i).getDatoriiDinAsigurariSociale().getRow().getLuna());
                    mostRecentIndex = i;
                }
            }
        }
        return Double.parseDouble(
            datoriiValues.get(mostRecentIndex).getDatoriiDinAsigurariSociale().getRow().getTotalObligatiiFiscale());
    }

    private Double solveDatoriiLaBugetulDeStat(List<DatoriiValues> datoriiValues) {
        int mostRecentIndex = 0;
        int lastYear = 0;
        int lastMonth = 0;
        for (int i = 0; i < datoriiValues.size(); i++) {
            if (datoriiValues.get(i).getDatoriiLaBugetulDeStat() != null) {
                if (Integer.parseInt(datoriiValues.get(i).getDatoriiLaBugetulDeStat().getRow().getAnul()) > lastYear
                    || (Integer.parseInt(
                    datoriiValues.get(i).getDatoriiLaBugetulDeStat().getRow().getAnul()) == lastYear
                    && Integer.parseInt(
                    datoriiValues.get(i).getDatoriiLaBugetulDeStat().getRow().getLuna()) > lastMonth)) {
                    lastYear = Integer.parseInt(datoriiValues.get(i).getDatoriiLaBugetulDeStat().getRow().getAnul());
                    lastMonth = Integer.parseInt(datoriiValues.get(i).getDatoriiLaBugetulDeStat().getRow().getLuna());
                    mostRecentIndex = i;
                }
            }
        }
        return Double.parseDouble(
            datoriiValues.get(mostRecentIndex).getDatoriiLaBugetulDeStat().getRow().getTotalObligatiiFiscale());

    }

    private Double solveDatoriiDinAsigurariDeSanatate(List<DatoriiValues> datoriiValues) {
        int mostRecentIndex = 0;
        int lastYear = 0;
        int lastMonth = 0;
        for (int i = 0; i < datoriiValues.size(); i++) {
            if (datoriiValues.get(i).getDatoriiDinAsigurariDeSanatate() != null) {
                if (Integer.parseInt(
                    datoriiValues.get(i).getDatoriiDinAsigurariDeSanatate().getRow().getAnul()) > lastYear
                    || (Integer.parseInt(
                    datoriiValues.get(i).getDatoriiDinAsigurariDeSanatate().getRow().getAnul()) == lastYear
                    && Integer.parseInt(
                    datoriiValues.get(i).getDatoriiDinAsigurariDeSanatate().getRow().getLuna()) > lastMonth)) {
                    lastYear =
                        Integer.parseInt(datoriiValues.get(i).getDatoriiDinAsigurariDeSanatate().getRow().getAnul());
                    lastMonth =
                        Integer.parseInt(datoriiValues.get(i).getDatoriiDinAsigurariDeSanatate().getRow().getLuna());
                    mostRecentIndex = i;
                }
            }
        }
        return Double.parseDouble(
            datoriiValues.get(mostRecentIndex).getDatoriiDinAsigurariDeSanatate().getRow().getTotalObligatiiFiscale());

    }

    private boolean hasPositiveEquity(List<CollectionContainer> collectionContainers, String cui) {
        List<CollectionContainer> newCollectionCotainers = getCollectionContainersWithACui(collectionContainers, cui);
        for (CollectionContainer collectionContainer : newCollectionCotainers) {
            Map<String, Object> values = collectionContainer.getValues();
            Map<String, Object> mostRecentRecord = findMostRecentRecord(values);

            //      log.info("Collection Values: {}",values);

            //       log.info("mostRecentRecord: {}",mostRecentRecord);

            if (mostRecentRecord != null) {
                if (!isPositiveCapital(mostRecentRecord)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean hasPositiveNetProfit(List<CollectionContainer> collectionContainers, String cui) {
        List<CollectionContainer> newCollectionCotainers = getCollectionContainersWithACui(collectionContainers, cui);
        for (CollectionContainer collectionContainer : newCollectionCotainers) {
            Map<String, Object> values = collectionContainer.getValues();
            Map<String, Object> mostRecentRecord = findMostRecentRecord(values);

            if (mostRecentRecord != null) {
                if (!isPositiveNewProfit(mostRecentRecord)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public Map<String, Object> findMostRecentRecord(Map<String, Object> values) {
        List<Map<String, Object>> records = (List<Map<String, Object>>) values.get("root");

        Map<String, Object> mostRecentRecord = null;
        for (Map<String, Object> record : records) {
            if (mostRecentRecord == null || isNewerRecord(record, mostRecentRecord)) {
                mostRecentRecord = record;
            }
        }

        return mostRecentRecord;
    }

    private boolean isNewerRecord(Map<String, Object> record, Map<String, Object> mostRecentRecord) {
        String dateString1 = (String) record.get("An");
        String dateString2 = (String) mostRecentRecord.get("An");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            Date date1 = dateFormat.parse(dateString1);
            Date date2 = dateFormat.parse(dateString2);

            return date1.after(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isPositiveCapital(Map<String, Object> record) {
        String capitalSocial = (String) record.get("CapitalSocial");
        if (capitalSocial != null) {
            double capital = Double.parseDouble(capitalSocial);
            log.info("Capital: {}", capital);
            return capital >= 0;
        }
        return false;
    }

    private boolean isPositiveNewProfit(Map<String, Object> record) {
        String capitalSocial = (String) record.get("ProfitNet");
        if (capitalSocial != null) {
            double capital = Double.parseDouble(capitalSocial);
            log.info("Profit Net: {}", capital);
            return capital >= 0;
        }
        return false;
    }

    private boolean hasCompanyInsolvencies(List<CollectionContainer> collectionContainers, String cui) {
        List<CollectionContainer> newCollectionCotainers = getCollectionContainersWithACui(collectionContainers, cui);
        for (CollectionContainer collectionContainer : newCollectionCotainers) {
            Map<String, Object> values = collectionContainer.getValues();
            if (!areAllFieldsNull(values)) {
                return false;
            }
        }
        return true;
    }

    private boolean areAllFieldsNull(Map<String, Object> data) {
        if (data == null || data.isEmpty()) {
            return true;
        }
        if (data.containsKey("values")) {
            Map<String, Object> values = (Map<String, Object>) data.get("values");
            if (values.containsKey("root")) {
                Object root = values.get("root");
                if (root instanceof Map) {
                    Map<String, Object> rootObject = (Map<String, Object>) root;
                    for (String key : rootObject.keySet()) {
                        if (rootObject.get(key) != null) {
                            log.info("Root key: {}, Root Object: {}", key, rootObject.get(key));
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private List<CollectionContainer> getCollectionContainersWithACui(List<CollectionContainer> collectionContainers,
        String cui) {
        List<CollectionContainer> newCollectionCotainers = new ArrayList<>();
        if (collectionContainers == null) {
            return null;
        }
        for (CollectionContainer collectionContainer : collectionContainers) {
            if (collectionContainer.getValues().get("CUI").equals(cui)) {
                newCollectionCotainers.add(collectionContainer);
            }
        }
        if (newCollectionCotainers.isEmpty()) {
            return null;
        }
        return newCollectionCotainers;
    }

/*    private static boolean companyPaymentIncidentWithTotalZeroSolvingMap(Map<String, Object> values) {
        if (values.containsKey("root") && values.get("root") instanceof Iterable) {
            for (Object obj : (Iterable<?>) values.get("root")) {
                if (obj instanceof Map) {
                    Map<?, ?> map = (Map<?, ?>) obj;
                    if (map.containsKey("Tip")) {
                        if("- motive majore".equalsIgnoreCase(map.get("Tip").toString())) {
                            if (!"0.00".equalsIgnoreCase(map.get("Total").toString()) || !"0.00".equalsIgnoreCase(map.get("CEC").toString())
                                || !"0.00".equalsIgnoreCase(map.get("Cambii").toString()) || !"0.00".equalsIgnoreCase(map.get("BileteOrdin").toString())) {
                                return false;
                            }
                        }
                        if("- motive minore".equalsIgnoreCase(map.get("Tip").toString())) {
                            if (!"0.00".equalsIgnoreCase(map.get("Total").toString()) || !"0.00".equalsIgnoreCase(map.get("CEC").toString())
                                || !"0.00".equalsIgnoreCase(map.get("Cambii").toString()) || !"0.00".equalsIgnoreCase(map.get("BileteOrdin").toString())) {
                                return false;
                            }
                        }
                        if("Total suma refuzata din care:".equalsIgnoreCase(map.get("Tip").toString())) {
                            if (!"0.00".equalsIgnoreCase(map.get("Total").toString()) || !"0.00".equalsIgnoreCase(map.get("CEC").toString())
                                || !"0.00".equalsIgnoreCase(map.get("Cambii").toString()) || !"0.00".equalsIgnoreCase(map.get("BileteOrdin").toString())) {
                                return false;
                            }
                        }
                        if("Total nr.instrumente din care:".equalsIgnoreCase(map.get("Tip").toString())) {
                            if (!"0.00".equalsIgnoreCase(map.get("Total").toString()) || !"0.00".equalsIgnoreCase(map.get("CEC").toString())
                                || !"0.00".equalsIgnoreCase(map.get("Cambii").toString()) || !"0.00".equalsIgnoreCase(map.get("BileteOrdin").toString())) {
                                return false;
                            }
                        }
                        if("Total suma de plata din care:".equalsIgnoreCase(map.get("Tip").toString())) {
                            if (!"0.00".equalsIgnoreCase(map.get("Total").toString()) || !"0.00".equalsIgnoreCase(map.get("CEC").toString())
                                || !"0.00".equalsIgnoreCase(map.get("Cambii").toString()) || !"0.00".equalsIgnoreCase(map.get("BileteOrdin").toString())) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }*/
}
