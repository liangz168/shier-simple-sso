package site.shier.sso.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liangliang.wei
 * @description
 * @create 2017-10-27 下午3:59
 **/
public class CookieUtils {

    public static String getValue(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }


    public static void addValue(String name, String value, Integer maxAge, String path, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    public static void addValue(String name, String value, Integer maxAge, HttpServletResponse response) {
        addValue(name, value, maxAge, "/",response);
    }

    public static void addValue(String name, String value, String path, HttpServletResponse response) {
        addValue(name, value, null, path, response);
    }
}
