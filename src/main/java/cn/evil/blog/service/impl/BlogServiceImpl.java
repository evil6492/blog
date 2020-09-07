package cn.evil.blog.service.impl;

import cn.evil.blog.NotFoundException;
import cn.evil.blog.dao.BlogDao;
import cn.evil.blog.dao.TagDao;
import cn.evil.blog.entity.Blog;
import cn.evil.blog.entity.Tag;
import cn.evil.blog.service.BlogService;
import cn.evil.blog.service.TagService;
import cn.evil.blog.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogDao blogDao;
    @Autowired
    TagDao tagDao;
    @Autowired
    TagService tagService;

    /**根据id获取Blog*/
    @Override
    public Blog getBlog(Long id) {
        Blog b=blogDao.getBlog(id);
        b.setTags(tagDao.findTagByBlog(b.getId()));
        return b;
    }

    /**获取所有博客*/
    @Override
    public List<Blog> listBlog() {
        return blogDao.findAll();
    }

    /**添加博客，并向blog_tag中添加关联数据*/
    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //返回插入数据的id,储存在blog对象中
        int result= blogDao.saveBlog(blog);
        //向Blog_tag表中插入博客和tag对应的关系
        editBlogTag(blog.getId(),blog.getTags());
        return result;
    }
    @Transactional
    public void editBlogTag(Long bid,List<Tag> tags) {
        for (Tag tag : tags) {
            tagDao.insertBlogTag(bid, tag.getId());
        }
    }
    /**更新博客，并更新blog_tag表中的对应数据*/
    @Transactional
    @Override
    public int updateBlog(Long id, Blog blog) {
        Blog b=blogDao.getBlog(id);
        if(b==null){
            throw new NotFoundException("该博客不存在");
        }
        blog.setUpdateTime(new Date());
        //1.先删除blog_tag表中的对应数据
        //2.再根据tagIds,向表中插入关联数据
        List<Tag> tags = tagService.listTag(blog.getTagIds());
        tagDao.deleteFromBlogTag(id);
        editBlogTag(id,tags);
        return blogDao.updateBlog(blog);
    }

    /**删除博客，同时删除blog_tag的关联数据以及评论数据*/
    @Transactional
    @Override
    public int deleteBlog(Long id) {
        tagDao.deleteFromBlogTag(id);
        return blogDao.deleteBlog(id);
    }

    /**根据条件搜索blog*/
    @Override
    public List<Blog> search(Blog b, Long typeId) {
        return blogDao.searchByTitleAndTypeAndRecommend(b.getTitle(),b.isRecommend(),typeId);
    }

    //--------------------------------------------------------------------------
    /**博客前端，博客首页*/
    @Override
    public List<Blog> findBlogToIndex() {
        return blogDao.findBlogToIndex();
    }
    /**博客详情*/
    public Blog getBlogDetail(Long id){
        Blog b=blogDao.getBlogToDetail(id);
        if(b==null){
            throw new NotFoundException("博客信息：无法查到该博客！");
        }
        return b;
    }
    /**搜索博客*/
    public List<Blog> searchBlogByQuery(String query){
        return blogDao.searchBlogByQuery(query);
    }
    /**博客详情（处理Markdown文本）*/
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog=blogDao.getBlogToDetail(id);
        if(blog==null){
            throw new NotFoundException("博客信息：无法查到该博客！");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //访问量增加+1
        blogDao.addViewsFromBlog(id);
        return b;
    }

    /**获取推荐博客*/
    @Override
    public List<Blog> recommendBlog() {
        return blogDao.findRecommendBlog();
    }
    /**查询最新博客*/
    @Override
    public List<Blog> findNewBlog() {
        return blogDao.findNewBlog();
    }

    /**根据typeId查找该分类下的博客*/
    @Override
    public List<Blog> findBlogByTypeId(Long typeId) {
        return blogDao.findBlogByTypeId(typeId);
    }
    /**根据tagId查找tag标签下的博客*/
    @Override
    public List<Blog> findBlogByTagId(Long tagId) {
        return blogDao.findBlogByTagId(tagId);
    }

    /**根据年份获取归档后的博客列表。2020,2019,2018*/
    @Override
    public List<Blog> archivesBlog() {
//        //获取年份，用LinkedHashMap保证有序
//        Map<String,List<Blog>> map=new LinkedHashMap<>();
//        List<String> years=blogDao.findGroupByYear();
//        for(String year:years){
//            map.put(year,blogDao.findBlogByYear(year));
//        }
//        return map;
        return blogDao.findArchivesBlog();
    }
    /**统计博客数量*/
    @Override
    public int countBlog() {
        return blogDao.countBlog();
    }

}
