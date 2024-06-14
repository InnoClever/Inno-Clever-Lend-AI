package ai.inno.clever.service.feign;


import ai.innoclever.client.AuthorizedFeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@AuthorizedFeignClient(name = "dataextraction", fallback = DataExtractionServiceFallback.class)
public interface DataExtractionService {

    @PostMapping(value = "/api/extraction-controller/extract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String extractData(@RequestPart("file") MultipartFile file, @RequestParam("templateName") String templateName);
}
