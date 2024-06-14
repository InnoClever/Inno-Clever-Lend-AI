package ai.inno.clever.service.feign;

import ai.inno.clever.service.dto.core.AdherentDTO;
import ai.inno.clever.service.dto.core.FileInfoDTO;
import ai.inno.clever.service.dto.core.enumeration.EntityType;
import ai.innoclever.client.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@AuthorizedFeignClient(name = "core", fallback = CoreServiceFallback.class)
public interface CoreService {
    @GetMapping(value = "/api/files/microservice/file-infos")
    List<FileInfoDTO> getFileInfos(@RequestHeader("microservice_key") String microserviceKey,
                                   @RequestParam(value = "entityType", required = false) EntityType entityType,
                                   @RequestParam(value = "entityId", required = false) Long entityId,
                                   @RequestParam(value = "bizType", required = false) String bizType);

    @GetMapping(value = "/api/v1/file-infos")
    List<FileInfoDTO> getFileInfos(@RequestParam(value = "entityType", required = false) EntityType entityType,
                                   @RequestParam(value = "entityId", required = false) Long entityId);

    @GetMapping(value = "/api/openapi/company-details")
    HashMap<String, Object> getCompanyDetails(@RequestParam(value = "taxCode") String companyCui,
                                              @RequestParam(value = "keysfin", required = false) Boolean keysfin,
                                              @RequestParam(value = "factoringRequestId", required = false) String factoringRequestId);


    @GetMapping("/api/v1/file-infos/{id}")
    FileInfoDTO getFileInfo(@PathVariable("id") Long id);

    @GetMapping("/api/adherents/{id}")
    AdherentDTO getAdherent(@PathVariable("id") Long id);

    @GetMapping("/api/v1/adherents/rbro/{id}")
    AdherentDTO getAdherentV1(@PathVariable("id") Long id);

    @GetMapping("/api/v1/token/get-token")
    String getToken();
}

