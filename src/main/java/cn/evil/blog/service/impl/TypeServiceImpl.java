package cn.evil.blog.service.impl;

import cn.evil.blog.NotFoundException;
import cn.evil.blog.dao.TypeDao;
import cn.evil.blog.service.TypeService;
import com.github.pagehelper.Page;
import cn.evil.blog.entity.Type;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Transactional
    @Override
    public int saveType(Type type) {
        return typeDao.saveType(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeDao.getType(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    /**查询所有分类，及统计分类下的博客数*/
    @Transactional
    @Override
    public List<Type> listType() {
        List<Type> types=typeDao.findAll();
        countBlogFromType(types);
        return types;
    }

    private void countBlogFromType(List<Type> types) {
        for(Type t:types){
            t.setBlogTotal(typeDao.countBlogFromType(t.getId()));
        }
    }

    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Type t=typeDao.getType(id);
        if(t == null ){
            throw new NotFoundException("不存在该分类");
        }
        return typeDao.updateType(type);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        return typeDao.deleteType(id);
    }
}
