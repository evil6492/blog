package cn.evil.blog.controller;

import cn.evil.blog.entity.Blog;
import cn.evil.blog.entity.Type;
import cn.evil.blog.service.BlogService;
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
 * 博客前端：分类页
 */
@Controller
public class TypeShowController {

    @Autowired
    TypeService typeService;
    @Autowired
    BlogService blogService;

    @GetMapping("/types/{id}")
    public String listTypes(
            @RequestParam(defaultValue = "1",value = "page")Integer page,
            @PathVariable Long id,Model model){
        List<Type> types=typeService.listType();
        if(id == -1){
            id=types.get(0).getId();
        }
        PageHelper.startPage(page,5,"update_time desc");
        List<Blog> blogs=blogService.findBlogByTypeId(id);
        PageInfo<Blog> pageInfo=new PageInfo<>(blogs);

        model.addAttribute("types",types);
        model.addAttribute("typeTotal",types.size());
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId",id);      //记录当前选中的typeId
        return "types";
    }
}
