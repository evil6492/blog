package cn.evil.blog.service;

import cn.evil.blog.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);
    List<Blog> listBlog();

    int saveBlog(Blog blog);
    int updateBlog(Long id,Blog blog);
    int deleteBlog(Long id);
    List<Blog> search(Blog b,Long typeId);

    /**博客前端*/
    List<Blog> findBlogToIndex();
    Blog getBlogDetail(Long id);
    List<Blog> searchBlogByQuery(String query);
    Blog getAndConvert(Long id);
    List<Blog> recommendBlog();
    List<Blog> findNewBlog();
    List<Blog> findBlogByTypeId(Long typeId);
    List<Blog> findBlogByTagId(Long tagId);

    /**归档*/
//    Map<String,List<Blog>> archivesBlog();
    List<Blog> archivesBlog();
    int countBlog();
}
