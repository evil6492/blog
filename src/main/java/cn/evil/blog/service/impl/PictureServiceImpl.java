package cn.evil.blog.service.impl;

import cn.evil.blog.dao.PictureDao;
import cn.evil.blog.entity.Picture;
import cn.evil.blog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureDao pictureDao;

    @Override
    public int addPicture(Picture picture) {
        return pictureDao.addPicture(picture);
    }

    @Override
    public int deletePictureById(Long pid) {
        return pictureDao.deletePictureById(pid);
    }

    @Override
    public int updatePicture(Picture picture) {
        return pictureDao.updatePicture(picture);
    }

    @Override
    public Picture getPictureById(Long pid) {
        return pictureDao.getPictureById(pid);
    }

    @Override
    public List<Picture> findPictures() {
        return pictureDao.findPictures();
    }

    @Override
    public Picture getPictureByName(String name) {
        return pictureDao.getPictureByName(name);
    }
}
