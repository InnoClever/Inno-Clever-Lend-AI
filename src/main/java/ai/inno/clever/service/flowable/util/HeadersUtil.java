package ai.inno.clever.service.flowable.util;

import ai.inno.clever.service.UserService;
import ai.innoclever.config.FlowableConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HeadersUtil {

    private static final Logger log = LoggerFactory.getLogger(HeadersUtil.class);

    private static FlowableConfig flowableConfig;

    public HeadersUtil(FlowableConfig flowableConfig) {
        HeadersUtil.flowableConfig = flowableConfig;
    }

    public static HttpHeaders createHeaders(UserService userService) {
//        JwtAuthenticationToken authToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        AdminUserDTO userFromAuthentication = userService.getUserFromAuthentication(authToken);
//        String username = userFromAuthentication.getEmail();
//        if (username != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("admin", ".parola123"); // TODO: Remove ahrdcoded
            headers.setContentType(MediaType.APPLICATION_JSON);
            return headers;
//        }
//        return null;
    }
}
