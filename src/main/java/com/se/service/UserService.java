package com.se.service;

import com.se.entity.User;

import java.util.List;

public interface UserService {
    List<User> getFriends(long uid);
    boolean addFriend(long uid,long friendId);
}
