package cn.evil.blog.service.impl;

import cn.evil.blog.NotFoundException;
import cn.evil.blog.dao.TagDao;
import cn.evil.blog.entity.Tag;
import cn.evil.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagDao tagDao;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagDao.getTag(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    /**查询所有标签，以及统计各标签下有多少篇博客*/
    @Transactional
    @Override
    public List<Tag> listTag() {
        List<Tag> tags=tagDao.findAll();
        countBlogFromTag(tags);
        return tags;
    }
    public void countBlogFromTag(List<Tag> tags) {
        for(Tag tag:tags){
            tag.setBlogTotal(tagDao.countBlogFromTag(tag.getId()));
        }
    }

    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Tag t=tagDao.getTag(id);
        if(t==null){
            throw new NotFoundException("该标签找不着");
        }
        return tagDao.updateTag(tag);
    }

    @Transactional
    @Override
    public int deleteTag(Long id) {
        return tagDao.deleteTag(id);
    }

    /**根据tagIds，查找出对应的tag集合，并返回*/
    @Override
    public List<Tag> listTag(String ids) {
        List<Tag> list=new ArrayList<>();
        List<Long> tagIds=stringToList(ids);
        for(Long id:tagIds){
            list.add(tagDao.getTag(id));
        }
        return list;
    }

    /**字符串转换成数组*/
    private  List<Long> stringToList(String ids){
        List<Long> list=new ArrayList<>();
        if(!"".equals(ids) && ids!=null){
            String[] arr=ids.split(",");
            for(int i=0;i<arr.length;i++){
                list.add(new Long(arr[i]));
            }
        }
        return list;
    }
}
