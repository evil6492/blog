package cn.evil.blog.dao;

import cn.evil.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {

    //获取父级评论
    Comment getByParentCommentId(Long parentId);

    //根据blogId获取所有父级评论
    List<Comment> findByBlogIdAndParentIdIsNull(Long blogId);


    //根据blogId和父级id查询对应的所有子评论(即回复)
    List<Comment> findByBlogIdAndReplyId(
            @Param("blogId")Long blogId,
            @Param("replyId") Long replyId);

    //添加评论
    int saveComment(Comment comment);

}
