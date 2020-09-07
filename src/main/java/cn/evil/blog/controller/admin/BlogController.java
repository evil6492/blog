package cn.evil.blog.controller.admin;

import cn.evil.blog.entity.Blog;
import cn.evil.blog.entity.Type;
import cn.evil.blog.entity.User;
import cn.evil.blog.service.BlogService;
import cn.evil.blog.service.TagService;
import cn.evil.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 后台管理：博客管理
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT="admin/blogs-input";

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    /**删除博客*/
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id ,RedirectAttributes attributes){
        int success = blogService.deleteBlog(id);
        if(success!= 1){
            attributes.addFlashAttribute("message","删除博客失败");
        }else{
            attributes.addFlashAttribute("message","删除博客成功");
        }
        return "redirect:/admin/blogs";
    }

    /**更新博客的修改*/
    @PostMapping("/blogs/{id}")
    public String updateBlog(@PathVariable Long id,Blog blog,RedirectAttributes attributes){
        System.err.println("修改的博客："+blog);
        int success=blogService.updateBlog(id,blog);
        if(success!=1){
            attributes.addFlashAttribute("message","更新博客失败");
        }else{
            attributes.addFlashAttribute("message","更新博客成功");
        }
        return "redirect:/admin/blogs";
    }

    /**博客添加*/
    @PostMapping("/blogs")
    public String addBlog(
            @RequestParam("tagIds")String tagIds,
            Blog blog, HttpSession session, RedirectAttributes attributes){
        User user = (User) session.getAttribute("user");
        blog.setUser(user);
        blog.setType(typeService.getType(blog.getType().getId()));
        //获取tagIds对应的所有tag数据，放在blog中，后续进行添加
        blog.setTags(tagService.listTag(tagIds));
        System.err.println("修改的博客："+blog);
        int success = blogService.saveBlog(blog);
        if(success!=1){
            attributes.addFlashAttribute("message","添加博客失败");
        }else{
            attributes.addFlashAttribute("message","添加博客成功");
        }
        return "redirect:/admin/blogs";
    }

    /**根据条件检索*/
    @PostMapping("/blogs/search")
    public String search(@RequestParam("typeId")Long typeId,Blog blog,
                         Model model,@RequestParam(defaultValue = "1",value = "page")Integer page){
        PageHelper.startPage(page,10,"update_time desc");
        List<Blog> list=blogService.search(blog,typeId);
        PageInfo<Blog> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs :: blogList";
    }

    /**跳转到修改页面*/
    @GetMapping("/blogs/{id}/input")
    public String toEditBlog(@PathVariable Long id, Model model){
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        return "admin/blogs-input";
    }

    /**跳转新增页面*/
    @GetMapping("/blogs/input")
    public String toAddBlog(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    /**获取所有博客*/
    @GetMapping("/blogs")
    public String list(Model model, @RequestParam(defaultValue = "1",value = "page")Integer page){
        PageHelper.startPage(page,10,"update_time desc");
        List<Blog> list=blogService.listBlog();
        PageInfo<Blog> pageInfo=new PageInfo<>(list);
        List<Type> types=typeService.listType();
        model.addAttribute("types",types);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs";
    }

}
