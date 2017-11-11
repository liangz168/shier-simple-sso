package site.shier.sso.verification.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.shier.sso.service.CacheService;
import site.shier.sso.utils.CookieUtils;
import site.shier.sso.verification.dto.UserDTO;
import site.shier.sso.verification.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author liangliang.wei
 * @description
 * @create 2017-10-27 下午3:37
 **/
@Controller
@RequestMapping("/verification")
@Slf4j
public class VerificationController {

    @Autowired
    private CacheService cacheService;

    private static final String TOKEN = "_token";

    private static final Integer LOGIN_TOKEN_EXPIRE = 60 * 60 * 24;

    private static final Integer TEMP_TOKEN_EXPIRE = 60;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String verification(String from, @CookieValue(value = TOKEN, defaultValue = "") String token, HttpServletResponse response) throws IOException {
        UserDTO userDTO = this.getUserDTOToken(token);
        if (userDTO != null) {
            //生成临时令牌
            String tempToken = this.generateToken(userDTO, TEMP_TOKEN_EXPIRE);

            response.sendRedirect(this.buildRedirectUrl(from, tempToken));
        } else {
            response.sendRedirect("/login.html?from=" + from);
        }

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginVO loginVO, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(System.currentTimeMillis());
        userDTO.setLoginName(loginVO.getLoginName());

        //生成登录长令牌
        String loginToken = this.generateToken(userDTO, LOGIN_TOKEN_EXPIRE);

        //生成临时令牌
        String tempToken = this.generateToken(userDTO, TEMP_TOKEN_EXPIRE);

        CookieUtils.addValue(TOKEN, loginToken, LOGIN_TOKEN_EXPIRE, response);

        map.put("code", 0);
        map.put("result", this.buildRedirectUrl(loginVO.getFrom(), tempToken));

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(String from, @CookieValue(value = TOKEN, defaultValue = "") String token, HttpServletResponse response) throws IOException {
        UserDTO userDTO = this.getUserDTOToken(token);
        if (userDTO != null) {
            cacheService.remove(token);
        }
        CookieUtils.addValue(TOKEN, null, 0, response);
        response.sendRedirect("/login.html?from=" + from);
        return null;
    }

    private UserDTO getUserDTOToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return cacheService.get(token, UserDTO.class);
    }

    private String generateToken(UserDTO userDTO, Integer second) {
        String tempToken = UUID.randomUUID().toString().replace("-", "");
        if (second == null) {
            cacheService.put(tempToken, userDTO);
        } else {
            cacheService.put(tempToken, userDTO, second);
        }
        return tempToken;
    }

    private String buildRedirectUrl(String from, String tempToken) {
        String redirectUrl = "/index.html";
        if (!StringUtils.isBlank(from)) {
            String add = "?";
            if (from.contains(add)) {
                add = "&";
            }
            redirectUrl = from + add + TOKEN + "=" + tempToken;
        }
        return redirectUrl;
    }

}
