package ma.sig.events.service.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpHeaders;

public final class AccreditationHeaderUtil {

    public static HttpHeaders createEntityMassUpdateAlert(
        String applicationName,
        boolean enableTranslation,
        String entityName,
        String param
    ) {
        String message = enableTranslation
            ? applicationName + "." + entityName + ".massUpdated"
            : param + "" + entityName + "s are mass updated";
        return createAlert(applicationName, message, param);
    }

    public static HttpHeaders createAlert(String applicationName, String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-alert", message);

        try {
            headers.add("X-" + applicationName + "-params", URLEncoder.encode(param, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException var5) {}

        return headers;
    }
}
