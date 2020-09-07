package cn.evil.blog.service;

import cn.evil.blog.entity.User;

public interface UserService {

    User checkUser(String username, String password);
    User getUser(String username);
}
