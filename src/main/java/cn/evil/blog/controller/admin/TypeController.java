package cn.evil.blog.controller.admin;

import cn.evil.blog.entity.Type;
import cn.evil.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 后台管理：分类管理
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    TypeService typeService;

    /**删除分类*/
    @GetMapping("types/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes attributes){
        int success = typeService.deleteType(id);
        if(success!= 1){
            attributes.addFlashAttribute("message","删除Type失败");
        }else{
            attributes.addFlashAttribute("message","删除Type成功");
        }
        return "redirect:/admin/types";
    }

    /**更新分类*/
    @PostMapping("/types/{id}")
    public String updateType(@PathVariable Long id,Type type,RedirectAttributes attributes){
        Type t=typeService.getTypeByName(type.getName());
        if(t!= null){
            attributes.addFlashAttribute("message","存在相同的Type，无法更新");
            return "redirect:/admin/types/input";
        }
        int success= typeService.updateType(id,type);
        if(success!= 1){
            attributes.addFlashAttribute("message","更新Type失败");
        }else{
            attributes.addFlashAttribute("message","更新Type成功");
        }
        return "redirect:/admin/types";
    }

    /**跳转到编辑分类页面*/
    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    /**跳转到添加分类页面*/
    @GetMapping("/types/input")
    public String toAddType(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    /**所有分类*/
    @GetMapping("/types")
    public String list(
            Model model,
            @RequestParam(defaultValue = "1",value = "page")Integer page){
        PageHelper.startPage(page,10,"id desc");
        List<Type> list= typeService.listType();
        PageInfo<Type> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }

    /**添加新分类*/
    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes){
        Type t=typeService.getTypeByName(type.getName());
        if(t!=null){
            attributes.addFlashAttribute("message","该分类已存在");
            return "redirect:/admin/types/input";
        }
        int success=typeService.saveType(type);
        if(success!= 1){
            attributes.addFlashAttribute("message","增加Type失败");
        }else{
            attributes.addFlashAttribute("message","增加Type成功");
        }
        return "redirect:/admin/types";
    }
}
