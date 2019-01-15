package com.se;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hello")
public class HomeController {
    private final Logger logger= LoggerFactory.getLogger(HomeController.class);
    @RequestMapping(method=RequestMethod.GET)
    public String home(ModelMap model){
        logger.debug("first log message");
        return "index";
    }

//    @RequestMapping(value = "/song_bird",method=RequestMethod.GET)
//    public String bird(){
//        return "song_bird";
//    }
}
