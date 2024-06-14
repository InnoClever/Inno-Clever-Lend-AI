package ai.inno.clever.rest.pipeline;

import ai.inno.clever.service.dto.core.enumeration.EntityType;
import ai.inno.clever.service.feign.CoreService;
import ai.inno.clever.service.feign.DataExtractionService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final Logger log = LoggerFactory.getLogger(TestController.class);


    @Autowired
    DataExtractionService dataExtractionService;

    @Autowired
    CoreService coreService;

    @RequestMapping(
        path = "/upload",
        method = RequestMethod.POST,
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleUpload(
        @RequestParam("entityType") EntityType entityType,
        @RequestParam("entityId") Long entityId) {

        AtomicReference<String> response = new AtomicReference<>("");

        coreService.getFileInfos(entityType, entityId).stream()
            .filter(fi -> fi.getBizType().equalsIgnoreCase("ACCOUNTING"))
            .forEach(fileInfo -> {
                fileInfo = coreService.getFileInfo(fileInfo.getId());


                try {
                    MultipartFile mpFile = new MockMultipartFile(fileInfo.getOriginalFilename(),
                        fileInfo.getOriginalFilename(),
                        fileInfo.getContentType(),
                        fileInfo.getFile());

                    JSONObject jsonObject = new JSONObject(fileInfo.getData());
                    String ERP_TYPE = jsonObject.getString("erpType");


                    String data = dataExtractionService.extractData(mpFile, ERP_TYPE);
                    response.getAndSet(response.get() + data + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


        return ResponseEntity.ok(response.get());
    }


}
