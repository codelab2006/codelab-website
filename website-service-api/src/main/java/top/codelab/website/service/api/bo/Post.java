package top.codelab.website.service.api.bo;

public class Post {

    private PostInfo info;
    private String content;

    public Post(PostInfo info, String content) {
        this.info = info;
        this.content = content;
    }

    public PostInfo getInfo() {
        return this.info;
    }

    public void setInfo(PostInfo info) {
        this.info = info;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
