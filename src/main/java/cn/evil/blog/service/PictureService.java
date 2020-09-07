package cn.evil.blog.service;

import cn.evil.blog.entity.Picture;

import java.util.List;

public interface PictureService {
    int addPicture(Picture picture);
    int deletePictureById(Long pid);
    int updatePicture(Picture picture);
    Picture getPictureById(Long pid);

    List<Picture> findPictures();
    Picture getPictureByName(String name);
}
