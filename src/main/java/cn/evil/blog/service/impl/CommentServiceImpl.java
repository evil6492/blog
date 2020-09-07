package cn.evil.blog.service.impl;

import cn.evil.blog.dao.CommentDao;
import cn.evil.blog.entity.Comment;
import cn.evil.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys=new ArrayList<>();

    /**根据blogId查询所有父级评论*/
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> list=commentDao.findByBlogIdAndParentIdIsNull(blogId);
        return combineChildren(list,blogId);
    }

    /**
     * 循环该博客每个父级的评论节点，找出每个评论下的回复
     * @param comments  root根节点，Blog不为空的对象集合
     * @return
     */
    private List<Comment> combineChildren(List<Comment> comments,Long blogId){
        for(Comment comment:comments){
            //将顶层评论节点的id作为parentId参数去查询对应的子节点集合
            Long parentId=comment.getId();
            List<Comment> replys=commentDao.findByBlogIdAndReplyId(blogId,parentId);
            //循环迭代每个reply，查找下属的所有回复，存放在一个集合中
            for(Comment reply: replys){
                reply.setParentName(comment.getNickname());
                tempReplys.add(reply);
                recursively(reply,blogId);
            }
            //修改顶级结点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys=new ArrayList<>();
        }
        return comments;
    }

    /**
     * 递归迭代，利用递归方法，将评论下的所有子节点都查询出来，存放在一个集合中
     * 即将评论的树状转换成扁平化的线性
     * 1.同时将父节点的nickname保存下来，前端页面的展示需要用到该数据
     * @param comment
     */
    private void recursively(Comment comment,Long blogId) {
        String parentName=comment.getNickname();
        List<Comment> replys=commentDao.findByBlogIdAndReplyId(blogId,comment.getId());
        if(replys.size()>0){
            for(Comment reply: replys){
                reply.setParentName(parentName);
                tempReplys.add(reply);
                if(commentDao.findByBlogIdAndReplyId(blogId,reply.getId()).size()>0){
                    recursively(reply,blogId);
                }
            }
        }
    }

    /**添加评论
     * 1.如果父级id为null则为父级评论
     * 2.如果id不会null则是该评论（父级）的回复（子级）*/
    @Transactional
    @Override
    public int saveComment(Comment comment) {
        /*判断有没有父级评论*/
        Long parentCommentId=comment.getParentComment().getId();
        if(parentCommentId != -1){
            Comment c=commentDao.getByParentCommentId(parentCommentId);
            comment.setParentComment(c);
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.saveComment(comment);
    }
}
