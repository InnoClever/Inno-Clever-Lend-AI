package ai.inno.clever.rest.vm;

import ai.innoclever.service.RnpmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/rnpm")
public class RnpmController {

    @Autowired
    private RnpmService rnpmService;

    @GetMapping("/company-from-rnpm")
    public List<List<List<Object>>> companyFromRnpm(@RequestParam String cui, @RequestHeader(name = "api_key") String apiKey) throws InterruptedException, IOException {
        if (!apiKey.equals("c478dff6-8d29-4c8c-9a28-baddcf8cacaf")) {
            throw new AuthenticationException();
        }
        return rnpmService.getCompanyInfo(cui);
    }
}
