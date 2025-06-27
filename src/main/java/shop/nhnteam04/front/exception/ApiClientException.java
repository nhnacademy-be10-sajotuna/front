package shop.nhnteam04.front.exception;

public class ApiClientException extends RuntimeException {
  public ApiClientException(String message) {
    super(message);
  }
}
