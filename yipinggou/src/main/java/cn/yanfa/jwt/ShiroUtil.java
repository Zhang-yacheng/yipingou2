package cn.yanfa.jwt;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ShiroUtil 存放当前用户信息
 *
 */
@Slf4j
public class ShiroUtil {
	private static TransmittableThreadLocal<ConcurrentHashMap<String, String>> idLocal = new TransmittableThreadLocal<>();

	/**
	 * 获取当前用户ID
	 * 
	 * @return
	 */
	public static String getUserId() {
		String userId = idLocal.get() == null ? null : idLocal.get().get("userId");
		if (StringUtils.isBlank(userId)) {
			log.info("userId为空-未登录");
		}
		return userId;
	}

	/**
	 * 设置当前用户ID
	 * 
	 * @param userId
	 */
	// public static void setUserId(String userId){
	// idLocal.set(userId);
	// }

	/**
	 * 设置token解析的用户信息
	 * 
	 * @param map
	 */
	public static void set(ConcurrentHashMap<String, String> map) {
		idLocal.set(map);
	}

	public static ConcurrentHashMap getMap() {
		return idLocal.get();
	}

	/**
	 * 获取当前用户等级1:企业 2政府 0超级管理员
	 * 
	 * @return
	 */
	public static String getUserGrade() {
		String userGrade = idLocal.get() == null ? null : idLocal.get().get("userGrade");
		if (StringUtils.isBlank(userGrade)) {
			log.info("userGrade为空-未登录");
		}
		return userGrade;
	}

}
