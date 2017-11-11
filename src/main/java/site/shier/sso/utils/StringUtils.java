package site.shier.sso.utils;

/**
 * @author liangliang.wei
 * @description
 * @create 2017-11-01 下午5:57
 **/
public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param string
     * @return
     */
    public Boolean isBlank(String string){
        return string == null || "".equals(string.trim());
    }

}
