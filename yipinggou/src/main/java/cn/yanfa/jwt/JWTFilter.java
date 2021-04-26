package cn.yanfa.jwt;

import cn.yanfa.common.RedisUtil;
import cn.yanfa.common.SystemConstant;
import cn.yanfa.model.enumPackage.CodeEnum;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * JWT权限过滤器
 * 
 */
@Component
@Slf4j
public class JWTFilter extends FormAuthenticationFilter {

	RedisUtil redisUtil;
	private String filterEr = "/common/unauthorized";
	/**
	 * logger
	 */
	Logger log = Logger.getLogger(JWTFilter.class.getName());

	/**
	 * shiro权限拦截核心方法 返回true允许访问resource，
	 *
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return 返回 false 将进入onAccessDenied
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = getRequestToken(httpRequest);

		// ShiroUtil.setUserId(null);
		// System.out.print(" request.getContextPath() +"+

		ShiroUtil.set(null);
		if (token == null || token.equals("")) {
			responseError(request, response, CodeEnum.OAUTH_UN_LOAD.getCode(), CodeEnum.OAUTH_UN_LOAD.getMsg());
			return false;
		}
		CheckResult result = null;
		try {

			// 加载顺序redis还没初始化 所以先
			ServletContext context = request.getServletContext();
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
			redisUtil = ctx.getBean(RedisUtil.class);

			result = checkToken(token);
		} catch (Exception ex) {
			// throw new BizException(SystemConstant.NOT_LOGIN, "未登录");
			responseError(request, response, CodeEnum.OAUTH_UN_LOAD.getCode(), CodeEnum.OAUTH_UN_LOAD.getMsg());
			return false;
		}
		if (result.getCode().equals("100")) {
			// ShiroUtil.setUserId(result.getUserId());
			ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
			map.put("userId", result.getUserId());
			map.put("userGrade", result.getUserGrade());
			ShiroUtil.set(map);
			return true;
		} else if (result.getCode().equals("101") || result.getCode().equals("103")) {
			responseError(request, response, CodeEnum.OAUTH_EXPIRED.getCode(), CodeEnum.OAUTH_EXPIRED.getMsg());
			return false;
		} else {
			responseError(request, response, CodeEnum.OAUTH_INVALID.getCode(), CodeEnum.OAUTH_INVALID.getMsg());
			return false;
		}

	}

	/**
	 * 将非法请求跳转到 /unauthorized/**
	 */
	private void responseError(ServletRequest req, ServletResponse resp, Integer code, String message) {

		HttpServletRequest request = (HttpServletRequest) req;
		try {
			// 设置编码，否则中文字符在重定向时会变为空字符串
			message = URLEncoder.encode(message, "UTF-8");
			request.getRequestDispatcher(filterEr + "/" + code + "/" + message).forward(req, resp);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	/**
	 * 当访问拒绝时是否已经处理了； 如果返回true表示需要继续处理； 如果返回false表示该拦截器实例已经处理完成了，将直接返回即可。
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		return false;
	}

	/**
	 * 获取请求的token
	 */
	private String getRequestToken(HttpServletRequest httpRequest) {
		// 从header中获取token
		String token = httpRequest.getHeader("authorization");

		// 如果header中不存在token，则从参数中获取token
		if (StringUtils.isEmpty(token)) {
			return httpRequest.getParameter("authorization");
		}
		if (StringUtils.isEmpty(token)) {

			// 从 cookie 获取 token
			Cookie[] cookies = httpRequest.getCookies();
			if (null == cookies || cookies.length == 0) {
				return null;
			}
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("authorization")) {
					token = cookie.getValue();
					break;
				}
			}
		}
		return token;
	}

	/**
	 * 校验token
	 * 
	 * @param token
	 * @return code:100->正常登录,code:101->Token超时,code:102->Token为空,code:103->Token非法
	 */
	public CheckResult checkToken(String token) {
		PermissionService.setUserId(null);
		CheckResult cr = new CheckResult();
		cr.setCode("102");
		if (token == null || token.trim().equals("")) {
			return cr;
		}
		String userId = null;
		String userGrade = null;
		try {
			String[] arr = token.split("\\.");
			String payload = arr[1];
			byte[] array = Base64.getUrlDecoder().decode(payload.getBytes("utf-8"));
			payload = new String(array);
			JSONObject jsonObj = JSONObject.parseObject(payload);
			userId = jsonObj.getString("sub");
			userGrade = jsonObj.getString("userGrade");
		} catch (Exception ex) {
			return cr;
		}

		if (StringUtils.isEmpty(userId)) {
			return cr;
		}
		cr.setUserId(userId);
		cr.setUserGrade(userGrade);
		String secret = getSecret(userId);
		if (secret == null || secret.trim().equals("")) {
			cr.setCode("103");
			return cr;
		}

		try {
			// ExpiredJwtException Token超时
			// SignatureException Token非法
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

			// Token超时
		} catch (ExpiredJwtException e) {
			cr.setCode("101");
			return cr;
		} catch (SignatureException e) {
			cr.setCode("103");
			return cr;
		} catch (Exception e) {
			cr.setCode("103");
			return cr;
		}
		PermissionService.setUserId(userId);
		cr.setCode("100");
		return cr;
	}

	private String getSecret(String userId) {
		String key = String.format(SystemConstant.HASH_KEY_USERINFO, userId);
		String hashKey = SystemConstant.LOGIN_TOKEN;
		Object result = null;
		try {
			// redisUtils.getRedisTemplate().setValueSerializer(new
			// StringRedisSerializer());
			result = redisUtil.hget(key, hashKey);
		} catch (Exception ex) {
			System.out.print(ex);
		}

		result = result != null ? result : "";

		return result.toString();
	}
}