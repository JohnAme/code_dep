package com.se.service.impl;

import com.se.dal.UserRepository;
import com.se.entity.User;
import com.se.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getFriends(long uid) {
        return userRepository.findFriends(uid);
    }

    @Override
    public boolean addFriend(long uid, long friendId) {
        return userRepository.addFriend(uid,friendId);
    }
}
