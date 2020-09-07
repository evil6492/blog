package cn.evil.blog.dao;

import cn.evil.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagDao {

    int saveTag(Tag tag);
    int deleteTag(Long id);
    int updateTag(Tag tag);
    Tag getTag(Long id);
    List<Tag> findAll();
    Tag getTagByName(String name);


    List<Tag> findTagByBlog(Long blogId);

    //插入blog_tag关联数据
    int insertBlogTag(@Param("bid")Long bid,@Param("tid")Long tid);
    //删除blog_tag关联数据
    int deleteFromBlogTag(Long blogId);

    int countBlogFromTag(Long tagId);
}
