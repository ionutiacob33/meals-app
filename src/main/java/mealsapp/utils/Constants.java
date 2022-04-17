package mealsapp.utils;

public class Constants {
    public static final int JWT_EXPIRY_MILLIS = 240 * 60 * 1000;
    public static final int REFRESH_TOKEN_EXPIRY_MILLIS = 480 * 60 * 1000;
    public static final String JWT_SECRET = "secret";
    public static final String ACCESS_TOKEN_HEADER = "access_token";
    public static final String REFRESH_TOKEN_HEADER = "refresh_token";
}
