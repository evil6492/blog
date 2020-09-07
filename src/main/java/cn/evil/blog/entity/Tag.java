package cn.evil.blog.entity;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private Long id;
    private String name;
    /**实体类之间的关系*/
    private List<Blog> blogs=new ArrayList<>();
    private int blogTotal;

    public Tag(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public int getBlogTotal() {
        return blogTotal;
    }

    public void setBlogTotal(int blogTotal) {
        this.blogTotal = blogTotal;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                ", blogTotal=" + blogTotal +
                '}';
    }
}
