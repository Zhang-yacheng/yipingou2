package cn.yanfa.jwt;

/**
 * JWT生成相关服务
 *
 */
public interface JWTService {
	String generateToken(String userId, String secret);
	
}
