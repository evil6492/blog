package cn.evil.blog.controller.admin;

import cn.evil.blog.entity.Tag;
import cn.evil.blog.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * 后台管理：标签管理
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
        int success = tagService.deleteTag(id);
        if(success!= 1){
            attributes.addFlashAttribute("message","删除Tag失败");
        }else{
            attributes.addFlashAttribute("message","删除Tag成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags")
    public String addTag(Tag tag, RedirectAttributes attributes){
        Tag t=tagService.getTagByName(tag.getName());
        if(t!=null){
            attributes.addFlashAttribute("message","存在相同的Tag，无法更新");
            return "redirect:/admin/tags/input";
        }
        int success=tagService.saveTag(tag);
        if(success!= 1){
            attributes.addFlashAttribute("message","增加Tag失败");
        }else{
            attributes.addFlashAttribute("message","增加Tag成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String updateTag(@PathVariable Long id,Tag tag,
                            RedirectAttributes attributes){
        Tag t=tagService.getTagByName(tag.getName());
        if(t!=null){
            attributes.addFlashAttribute("message","该标签已存在");
            return "redirect:/admin/tags/input";
        }
        int success=tagService.updateTag(id,tag);
        if(success!= 1){
            attributes.addFlashAttribute("message","更新Tag失败");
        }else{
            attributes.addFlashAttribute("message","更新Tag成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags")
    public String list(
            @RequestParam(defaultValue = "1",value = "page")Integer page, Model model){
        PageHelper.startPage(page,10,"id desc");
        List<Tag> list=tagService.listTag();
        PageInfo<Tag> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/tags";
    }

    /**页面跳转*/
    @GetMapping("/tags/{id}/input")
    public String eidtTag(@PathVariable Long id,Model model){
        Tag tag=tagService.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }

    @GetMapping("/tags/input")
    public String toAddTag(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

}
