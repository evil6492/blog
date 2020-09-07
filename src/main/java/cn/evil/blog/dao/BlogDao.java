package cn.evil.blog.dao;

import cn.evil.blog.entity.Blog;
import cn.evil.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogDao {
    Blog getBlog(Long id);
    List<Blog> findAll();
    int saveBlog(Blog blog);
    int updateBlog(Blog blog);
    int deleteBlog(Long id);
    List<Blog> searchByTitleAndTypeAndRecommend(
            @Param("title")String title,
            @Param("recommend")boolean recommend,
            @Param("typeId")Long typeId
    );

    /**博客前端*/
    List<Blog> findBlogToIndex();
    Blog getBlogToDetail(Long id);
    List<Blog> searchBlogByQuery(String query);
    int addViewsFromBlog(Long id);
    List<Blog> findBlogByTypeId(Long typeId);
    List<Blog> findBlogByTagId(Long tagId);
    List<Blog> findRecommendBlog();
    List<Blog> findNewBlog();

    /**归档*/
    List<Blog> findArchivesBlog();
//    List<String> findGroupByYear();
//    List<Blog> findBlogByYear(String year);
    int countBlog();
}
