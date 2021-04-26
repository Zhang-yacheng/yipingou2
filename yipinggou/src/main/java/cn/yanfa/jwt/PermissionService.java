package cn.yanfa.jwt;

import cn.yanfa.common.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 权限类
 */
public class PermissionService {

	private static ThreadLocal<String> idLocal = new ThreadLocal<String>();
	
	@Autowired
	private RedisUtil redisUtils;

//	public static String getSystemCode() {
//		return systemCode;
//	}
//
//	public static void setSystemCode(String systemCode) {
//		PermissionService.systemCode = systemCode;
//	}
//
//	private static String systemCode = null;

	/**
	 * 获取用户ID
	 * 
	 * @return
	 */
	public static String getUserId() {
		return idLocal.get();
	}

	/**
	 * 设置用户ID
	 * 
	 * @param userId
	 */
	public static void setUserId(String userId) {
		idLocal.set(userId);
	}

	/**
	 * 判断当前用户是否有该方法的权限
	 * @param accessName
	 * @return
	 */
	public static Boolean isAllowed(String accessName)
	{
		try{
			accessName = accessName.toLowerCase();
			String key = "access:userId:" + getUserId();
			
			//return redisUtils.setHasValue(key, accessName);
			return null;
		}
		catch(Exception ex){
			return false;
		}

	}

}
