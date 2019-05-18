package top.codelab.website.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.codelab.markdown.MarkdownFile;
import top.codelab.markdown.MarkdownRepository;
import top.codelab.website.service.api.BlogService;
import top.codelab.website.service.api.bo.Post;
import top.codelab.website.service.api.bo.PostInfo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class BlogServiceImpl extends ServiceImpl implements BlogService {

    private final static String DIRECTORY_NAME = "blog";

    @Autowired
    BlogServiceImpl(MarkdownRepository markdownRepository) {
        super(markdownRepository);
    }

    @Override
    public Map<String, Long> findAllTag() {
        return this.findTags(DIRECTORY_NAME);
    }

    @Override
    public void refreshCache() {
        super.refreshCache();
    }

    @Override
    public List<PostInfo> findAllPostInfo() {
        return this.markdownRepository.findMarkdownFiles(DIRECTORY_NAME).stream()
                .map(this::createPostInfo)
                .collect(Collectors.toList());
    }

    @Override
    public Post findPostByName(String name) {
        MarkdownFile markdownFile = this.markdownRepository.findMarkdownFileByDirectoryAndName(DIRECTORY_NAME, name);
        return new Post()
                .setInfo(this.createPostInfo(markdownFile))
                .setContent(this.parseMarkdown(this.extractContent(markdownFile)));
    }

    private PostInfo createPostInfo(MarkdownFile markdownFile) {
        return new PostInfo()
                .setName(FilenameUtils.removeExtension(markdownFile.getName()))
                .setTitle(this.extractTitle(markdownFile))
                .setAuthor(this.extractAuthor(markdownFile))
                .setTags(this.extractTags(markdownFile))
                .setSummary(this.parseMarkdown(this.extractSummary(markdownFile)))
                .setCreationTime(markdownFile.getCreationTime())
                .setLastModifiedTime(markdownFile.getLastModifiedTime());
    }
}
