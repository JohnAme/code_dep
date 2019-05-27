package com.se.controller;

import com.se.entity.User;
import com.se.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class FriendController {
    private static final Logger logger= LoggerFactory.getLogger(FriendController.class);

    private final UserService userService;
    @Autowired
    public FriendController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/findFriends")
    @ResponseBody
    public ResponseEntity<?> findFriends(@RequestParam(value = "uid",required = true)long uid){
        List<User> myFriends=userService.getFriends(uid);

        return new ResponseEntity<List<User>>(myFriends, HttpStatus.OK);
    }

}
