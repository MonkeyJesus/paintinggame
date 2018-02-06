package org.monkeyjesus.ctrl;

import org.monkeyjesus.service.CacheTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by MonkeyJesus on 2018/2/6 0006.
 */
@Controller
public class TestCtrl {

    @Autowired
    private CacheTestService cacheTestService;


    @RequestMapping(value = "/aa")
    @ResponseBody
    public String getInteger(){
        return cacheTestService.getCacheNumber();
    }


}
