package cn.evil.blog.entity;

public class Picture {
    private Long id;
    private String name;
    private String aUrl;
    private String imgUrl;
    private String descr;
    public Picture(){

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

    public String getaUrl() {
        return aUrl;
    }

    public void setaUrl(String aUrl) {
        this.aUrl = aUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", aUrl='" + aUrl + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", descr='" + descr + '\'' +
                '}';
    }
}
