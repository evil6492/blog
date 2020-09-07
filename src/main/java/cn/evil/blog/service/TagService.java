package cn.evil.blog.service;

import cn.evil.blog.entity.Tag;

import java.util.List;

public interface TagService {
    int saveTag(Tag tag);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    List<Tag> listTag();
    int updateTag(Long id, Tag tag);
    int deleteTag(Long id);

    List<Tag> listTag(String ids);
}
