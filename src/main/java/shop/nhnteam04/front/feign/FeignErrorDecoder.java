package shop.nhnteam04.front.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import shop.nhnteam04.front.account.exception.ApiClientException;


import java.nio.charset.StandardCharsets;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            HttpStatus httpStatus = HttpStatus.valueOf(response.status());
            if (httpStatus.is5xxServerError()) {
                return new RuntimeException("Server Error");
            }
            String message = feign.Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            return new ApiClientException(message);
        } catch (Exception e) {
            return new RuntimeException("Feign 오류 디코딩 실패", e);
        }
    }
}
