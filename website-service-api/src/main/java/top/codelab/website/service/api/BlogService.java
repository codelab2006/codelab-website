package top.codelab.website.service.api;

import top.codelab.website.service.api.bo.Post;
import top.codelab.website.service.api.bo.PostInfo;

import java.util.List;
import java.util.Map;

public interface BlogService {

    List<PostInfo> findAllPostInfo();

    Post findPostByName(String name);

    Map<String, Long> findAllTag();

    void refreshCache();
}
