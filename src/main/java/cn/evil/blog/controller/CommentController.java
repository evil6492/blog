package cn.evil.blog.controller;

import cn.evil.blog.entity.Comment;
import cn.evil.blog.entity.User;
import cn.evil.blog.service.BlogService;
import cn.evil.blog.service.CommentService;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 博客前端：评论
 */
@Controller
public class CommentController {
    @Value("${comment.avatar}")
    String avatar;

    @Autowired
    CommentService commentService;
    @Autowired
    BlogService blogService;

    /**添加评论*/
    @PostMapping("/comments")
    public String addComment(Comment comment, RedirectAttributes attributes, HttpSession session){
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user=(User) session.getAttribute("user");
        //判断当前评论用户是否是博主
        if(user!=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        }else{
            comment.setAvatar(avatar);
        }
        int success =commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBlog().getId();
    }

    /**获取blog评论*/
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        List<Comment> list=commentService.listCommentByBlogId(blogId);

        model.addAttribute("comments",list);
        return "blog :: commentList";

    }
}
