package cn.evil.blog.controller.admin;

import cn.evil.blog.entity.Picture;
import cn.evil.blog.entity.Tag;
import cn.evil.blog.service.PictureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class PictureController {
    @Autowired
    PictureService pictureService;

    @GetMapping("/pictures/{id}/delete")
    public String deletePicture(@PathVariable Long id,RedirectAttributes attributes){
        int success = pictureService.deletePictureById(id);
        if(success!= 1){
            attributes.addFlashAttribute("message","删除图片失败");
        }else{
            attributes.addFlashAttribute("message","删除图片成功");
        }
        return "redirect:/admin/pictures";
    }

    @PostMapping("/pictures")
    public String addPicture(Picture picture, RedirectAttributes attributes){
        Picture p=pictureService.getPictureByName(picture.getName());
        if(p!=null){
            attributes.addFlashAttribute("message","存在相同的图片名，无法添加");
            return "redirect:/admin/pictures/input";
        }
        int success=pictureService.addPicture(picture);
        if(success!= 1){
            attributes.addFlashAttribute("message","更新图片失败");
        }else{
            attributes.addFlashAttribute("message","更新图片成功");
        }
        return "redirect:/admin/pictures";
    }

    @PostMapping("/pictures/{id}")
    public String updatePicture(@PathVariable Long id, Picture picture,
                            RedirectAttributes attributes){
        int success=pictureService.updatePicture(picture);
        if(success!= 1){
            attributes.addFlashAttribute("message","更新图片失败");
        }else{
            attributes.addFlashAttribute("message","更新图片成功");
        }
        return "redirect:/admin/pictures";
    }

    @GetMapping("/pictures")
    public String listPictures(@RequestParam(defaultValue = "1",value = "page")Integer page, Model model){
        PageHelper.startPage(page,10,"id desc");
        List<Picture> list=pictureService.findPictures();
        PageInfo<Picture> pageInfo=new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/pictures";
    }

    @GetMapping("/pictures/{id}/input")
    public String toEditPicture(@PathVariable Long id, Model model){
        model.addAttribute("picture",pictureService.getPictureById(id));
        return "admin/pictures-input";
    }

    @GetMapping("/pictures/input")
    public String toAddPicture(Model model){
        model.addAttribute("picture",new Picture());
        return "admin/pictures-input";
    }
}
