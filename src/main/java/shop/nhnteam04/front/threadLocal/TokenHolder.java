package shop.nhnteam04.front.threadLocal;

public class TokenHolder {
    private static final ThreadLocal<String> token = new ThreadLocal<>();

    public static void set(String value) {
        token.set(value);
    }

    public static String get() {
        return token.get();
    }

    public static void clear() {
        token.remove();
    }
}
