package shop.nhnteam04.front.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import shop.nhnteam04.front.exception.ExpiredAccessTokenException;

import java.util.List;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        String errorCode = response.headers()
                .getOrDefault("X-Error-Code", List.of())
                .stream().findFirst().orElse("");

        if ("EXPIRED_TOKEN".equals(errorCode)) {
            return new ExpiredAccessTokenException("Access Token expired");
        }

        return defaultDecoder.decode(methodKey, response);
    }
}
