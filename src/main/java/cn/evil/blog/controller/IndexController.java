package cn.evil.blog.controller;

import cn.evil.blog.NotFoundException;
import cn.evil.blog.entity.Blog;
import cn.evil.blog.entity.Tag;
import cn.evil.blog.entity.Type;
import cn.evil.blog.entity.User;
import cn.evil.blog.service.BlogService;
import cn.evil.blog.service.TagService;
import cn.evil.blog.service.TypeService;
import cn.evil.blog.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客前端：首页
 */
@Controller
public class IndexController {

    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    /**最新博客*/
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.findNewBlog());
        return "_fragments :: newblogList";
    }

    /**搜索博客*/
    @PostMapping("/search")
    public String search(
            @RequestParam("query")String query,Model model,
            @RequestParam(defaultValue = "1",value = "page")Integer page){
        PageHelper.startPage(page,5,"update_time desc");
        List<Blog> list=blogService.searchBlogByQuery(query);
        PageInfo<Blog> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blogDetail(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/")
    public String index(
            @RequestParam(defaultValue = "1",value = "page")Integer page,
            Model model){
        List<Type> types=typeService.listType();
        List<Tag> tags=tagService.listTag();
        PageHelper.startPage(page,5,"update_time desc");
        List<Blog> list=blogService.findBlogToIndex();
        PageInfo<Blog> pageInfo=new PageInfo<>(list);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("recommendBlog",blogService.recommendBlog());
        return "index";
    }
}
