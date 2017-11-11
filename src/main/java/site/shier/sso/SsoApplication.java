package site.shier.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author liangliang.wei
 * @description
 * @create 2017-10-27 下午3:28
 **/
@SpringBootApplication
@ImportResource("classpath:beanRefContext.xml")
public class SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }
}
