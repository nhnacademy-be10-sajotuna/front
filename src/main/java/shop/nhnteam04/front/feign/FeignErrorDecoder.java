package shop.nhnteam04.front.feign;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.util.List;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        return defaultDecoder.decode(methodKey, response);
    }
}
