package cn.yanfa.jwt;

/**
 * 校验返回结果
 */
public class CheckResult {
	private String code;
	
	private String userId;

	private String userGrade;

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
 
}
