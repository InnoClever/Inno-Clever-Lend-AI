package ai.inno.clever.service.feign;

import ai.inno.clever.service.dto.core.AdherentDTO;
import ai.inno.clever.service.dto.core.FileInfoDTO;
import ai.inno.clever.service.dto.core.enumeration.EntityType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CoreServiceFallback implements CoreService {

    @Override
    public List<FileInfoDTO> getFileInfos(String microserviceKey, EntityType entityType, Long entityId, String bizType) {
        return null;
    }

    @Override
    public List<FileInfoDTO> getFileInfos(EntityType entityType, Long entityId) {
        return null;
    }

    @Override
    public HashMap<String, Object> getCompanyDetails(String companyCui, Boolean keysfin, String factoringRequestId) {
        return null;
    }
    @Override
    public FileInfoDTO getFileInfo(Long id) {
        return null;
    }

    @Override
    public AdherentDTO getAdherent(Long id) {
        return null;
    }

    @Override
    public AdherentDTO getAdherentV1(Long id) {
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }
}
