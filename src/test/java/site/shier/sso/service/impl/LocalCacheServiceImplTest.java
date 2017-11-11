package site.shier.sso.service.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocalCacheServiceImplTest {
    @Test
    public void init() throws Exception {

        LocalCacheServiceImpl cacheService = new LocalCacheServiceImpl();
        cacheService.put("test", "heheh",8);
        cacheService.init();

        while (true){
            System.out.println(cacheService.get("test"));
            Thread.sleep(1000L);
        }

    }

    @Test
    public void put() throws Exception {
    }

    @Test
    public void put1() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void getString() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }



}