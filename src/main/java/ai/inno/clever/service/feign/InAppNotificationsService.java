package ai.inno.clever.service.feign;

import ai.inno.clever.domain.NotificationsDTO;
import ai.inno.clever.domain.NotificationsFacadeDTO;
import ai.innoclever.client.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Set;

@AuthorizedFeignClient(name = "inappnotifications")
public interface InAppNotificationsService {

    @PostMapping(value = "/api/send-notifications")
//    @Async
    NotificationsDTO sendNotification(@Valid @RequestBody NotificationsFacadeDTO NotificationsFacadeDTO, @RequestParam("channels") Set<String> channels);
}
