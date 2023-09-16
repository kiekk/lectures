package shop.mtcoding.bank.config.jwt;

/**
 * SECRET 은 노출되면 안되기 때문에 환경변수, 클라우드 같은 곳에 저장해야 한다.
 */
public interface JwtVO {
    String SECRET = "SOONO"; // HS256 (대칭키)
    int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER = "Authorization";
}
