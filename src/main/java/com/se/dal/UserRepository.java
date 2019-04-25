package com.se.dal;

import com.se.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRepository {
    List<User> findFriends(@Param("uid")long uid);
    boolean addFriend(@Param("uid")long uid,@Param("friendId")long friendId);

    boolean validateUser(@Param("uid")long uid,@Param("password")String password);
    User findUserByUID(@Param("uid")long uid);
    User findUserByUname(@Param("uname")String uname);

    boolean insertUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(@Param("uid")long uid);
}
