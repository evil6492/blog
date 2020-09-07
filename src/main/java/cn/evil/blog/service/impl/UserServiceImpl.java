package cn.evil.blog.service.impl;

import cn.evil.blog.dao.UserDao;
import cn.evil.blog.entity.User;
import cn.evil.blog.service.UserService;
import cn.evil.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        String code= MD5Utils.code(MD5Utils.code(password));
        User user = userDao.findByUsernameAndPassword(username,code);
        return user;
    }

    @Override
    public User getUser(String username) {
        return userDao.getUserByUsername(username);
    }
}
