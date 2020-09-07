package cn.evil.blog.dao;

import cn.evil.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    User findByUsernameAndPassword(
            @Param("username")String username,
            @Param("password")String password);

    User getUserByUsername(String username);
}
