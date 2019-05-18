package top.codelab.website.service.api.bo;

public class Post {

    private PostInfo info;
    private String content;

    public PostInfo getInfo() {
        return this.info;
    }

    public Post setInfo(PostInfo info) {
        this.info = info;
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }
}
