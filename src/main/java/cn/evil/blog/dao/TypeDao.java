package cn.evil.blog.dao;

import cn.evil.blog.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeDao {
    int saveType(Type type);

    Type getType(Long id);

    List<Type> findAll();

    //List<Type> getAllTypeAndBlog();

    Type getTypeByName(String name);

    int updateType(Type type);

    int deleteType(Long id);

    int countBlogFromType(Long typeId);
}
