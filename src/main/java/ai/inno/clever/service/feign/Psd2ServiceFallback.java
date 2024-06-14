package ai.inno.clever.service.feign;

import ai.inno.clever.service.dto.aisp.AispDTO;
import org.springframework.stereotype.Component;

@Component
public class Psd2ServiceFallback implements Psd2Service {


    @Override
    public AispDTO getAisp(String microserviceKey, String requestId) {
        return new AispDTO();
    }
}
