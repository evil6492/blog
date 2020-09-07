package cn.evil.blog.controller;

import cn.evil.blog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *博客前端：照片
 */
@Controller
public class PictureShowController {

    @Autowired
    PictureService pictureService;

    @GetMapping("/picture")
    public String picture(Model model){
        model.addAttribute("pictures",pictureService.findPictures());
        return "picture";
    }
}
