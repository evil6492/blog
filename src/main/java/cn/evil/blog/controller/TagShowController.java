package cn.evil.blog.controller;

import cn.evil.blog.NotFoundException;
import cn.evil.blog.entity.Blog;
import cn.evil.blog.entity.Tag;
import cn.evil.blog.entity.Type;
import cn.evil.blog.service.BlogService;
import cn.evil.blog.service.TagService;
import cn.evil.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 博客前端：标签页
 */
@Controller
public class TagShowController {
    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;

    @GetMapping("/tags/{id}")
    public String listTags(
            @RequestParam(defaultValue = "1",value = "page")Integer page,
            @PathVariable Long id, Model model){
        List<Tag> tags=tagService.listTag();
        //如果tagId=-1，则从集合中取第一个tag的id查询其对应的所有博客
        if(id == -1){
            id=tags.get(0).getId();
        }
        PageHelper.startPage(page,5,"update_time desc");
        List<Blog> blogs=blogService.findBlogByTagId(id);
        PageInfo<Blog> pageInfo=new PageInfo<>(blogs);

        model.addAttribute("tags",tags);
        model.addAttribute("tagTotal",tags.size());
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTagId",id);   //记录当前选中的tagId
        return "tags";
    }
}
