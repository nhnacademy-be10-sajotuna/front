package shop.nhnteam04.front.threadLocal;

public class TokenHolder {
    private static final ThreadLocal<String> accessToken = new ThreadLocal<>();
    private static final ThreadLocal<String> refreshToken = new ThreadLocal<>();

    public static void setAccessToken(String value) {
        accessToken.set(value);
    }

    public static String getAccessToken() {
        return accessToken.get();
    }

    public static void setRefreshToken(String value) {
        refreshToken.set(value);
    }

    public static String getRefreshToken() {
        return refreshToken.get();
    }

    public static void clear() {
        accessToken.remove();
        refreshToken.remove();
    }
}
