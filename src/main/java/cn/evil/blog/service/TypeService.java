package cn.evil.blog.service;

import com.github.pagehelper.Page;
import cn.evil.blog.entity.Type;

import java.awt.print.Pageable;
import java.util.List;

public interface TypeService {
    int saveType(Type type);
    Type getType(Long id);
    Type getTypeByName(String name);
    List<Type> listType();
    int updateType(Long id,Type type);
    int deleteType(Long id);
}
