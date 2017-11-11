package site.shier.sso.verification.dto;

import java.io.Serializable;

/**
 * @author liangliang.wei
 * @description
 * @create 2017-11-01 下午6:04
 **/
public class UserDTO implements Serializable{

    private Long id;

    private String loginName;

    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
