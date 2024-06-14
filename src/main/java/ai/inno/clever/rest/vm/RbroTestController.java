package ai.inno.clever.rest.vm;

import ai.inno.clever.domain.rbrotest.AdherentDTO;
import ai.inno.clever.domain.rbrotest.CollectionContainer;
import ai.inno.clever.service.RbroTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/rbro")
public class RbroTestController {

    @Autowired
    private RbroTestService rbroTestService;

    @PostMapping("/test")
    public Map<String, CollectionContainer[]> testRbro(@RequestHeader(name = "api_key") String apiKey, @RequestBody AdherentDTO adherentDTO, @RequestParam(required = false) Boolean createOnly) throws IOException, InterruptedException {
        if (!apiKey.equals("64724713-bc79-4777-bdbd-458bd8cc5818")) {
            throw new AuthenticationException();
        }
        if (createOnly != null && createOnly) {
            return rbroTestService.createFactoring(adherentDTO);
        }
        return rbroTestService.start(adherentDTO);
    }
}
