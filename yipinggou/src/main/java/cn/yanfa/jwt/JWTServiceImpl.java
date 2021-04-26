package cn.yanfa.jwt;

import cn.yanfa.common.DateUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT生成相关服务实现
 *
 */
@Service("jwtService")
public class JWTServiceImpl implements JWTService {

	/**
	 * 有效期限 天
	 */
	private static final int expire = 1;

	/**
	 * 存储 token
	 */
	private static Map<String, Object> header = new HashMap<String, Object>();

	static {
		header.put("typ", "JWT");
		header.put("alg", "HS512");
	}

	public String generateToken(String userId, String secret) {
		Date nowDate = new Date();
		return Jwts.builder().setHeaderParams(header)
				// 后续获取 subject 是 userid
				.setSubject(userId).setIssuedAt(nowDate)
				// userGrade 用户级别
				//.claim("userGrade", userGrade)
				// 过期时间
				//.setExpiration(DateUtil.addDays(nowDate, expire))
				.setExpiration(DateUtil.addDays(nowDate, expire))
				// HS512 算法
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
