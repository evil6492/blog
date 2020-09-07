package cn.evil.blog.dao;

import cn.evil.blog.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureDao {
    int addPicture(Picture picture);
    int deletePictureById(Long pid);
    int updatePicture(Picture picture);
    Picture getPictureById(Long pid);

    List<Picture> findPictures();
    Picture getPictureByName(String name);
}
