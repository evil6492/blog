package cn.evil.blog.controller;

import cn.evil.blog.entity.Blog;
import cn.evil.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 *博客前端：归档
 */
@Controller
public class ArchivesShowController {
    @Autowired
    BlogService blogService;

    @GetMapping("/archives")
    public String listArchives(Model model){
//        Map<String, List<Blog>> map=blogService.archivesBlog();
//        model.addAttribute("archivesBlog",map);
//        model.addAttribute("blogTotal",blogService.countBlog());
        model.addAttribute("blogs",blogService.archivesBlog());
        return "archives";
    }
}
