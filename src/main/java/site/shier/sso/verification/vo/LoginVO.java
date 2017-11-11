package site.shier.sso.verification.vo;

/**
 * @author liangliang.wei
 * @description
 * @create 2017-11-02 下午3:06
 **/
public class LoginVO {

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 原系统地址
     */
    private String from;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public LoginVO setFrom(String from) {
        this.from = from;
        return this;
    }
}
