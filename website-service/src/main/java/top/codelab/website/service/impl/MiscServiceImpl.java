package top.codelab.website.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.codelab.markdown.MarkdownRepository;
import top.codelab.website.service.api.BlogService;
import top.codelab.website.service.api.MiscService;
import top.codelab.website.service.api.exception.PathNotFoundException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
class MiscServiceImpl extends ServiceImpl implements MiscService {

    private static final Logger logger = LogManager.getLogger();

    private final BlogService blogService;

    private Repository repository;
    private String username;
    private String password;

    @Autowired
    MiscServiceImpl(@Value("${MARKDOWN_REPOSITORY:#{null}}") String markdownRepositoryPath,
                    @Value("${MARKDOWN_REPOSITORY_USERNAME:#{null}}") String markdownRepositoryUsername,
                    @Value("${MARKDOWN_REPOSITORY_PASSWORD:#{null}}") String markdownRepositoryPassword,
                    BlogService blogService, MarkdownRepository markdownRepository) {
        super(markdownRepository);
        this.blogService = blogService;

        try {
            Path markdownRepositoryGitPath = this.getPath(this.appendDotGit(markdownRepositoryPath));
            this.repository = new FileRepositoryBuilder()
                    .setGitDir(markdownRepositoryGitPath.toFile())
                    .build();
            this.username = markdownRepositoryUsername;
            this.password = markdownRepositoryPassword;
        } catch (Exception e) {
            logger.warn("Markdown data fetching is turned off");
        }
    }

    private String appendDotGit(String s) {
        return (s.endsWith("/") ? s : s.concat("/")).concat(Constants.DOT_GIT);
    }

    private Path getPath(String p) {
        Path path = Paths.get(p);
        if (!Files.exists(path)) {
            throw new PathNotFoundException(p);
        }
        return path;
    }

    @Override
    public void refresh() {
        Optional.ofNullable(this.repository).ifPresent(repository -> {
            try {
                logger.info("Refreshing...");
                logger.info("Refreshing data...");
                UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(this.username, this.password);
                new Git(repository)
                        .pull()
                        .setCredentialsProvider(provider)
                        .call();
                logger.info("Refreshing document tree...");
                this.refreshDocumentTree();
                logger.info("Refreshing cache...");
                this.blogService.refreshCache();
                logger.info("Refreshing...done");
            } catch (Exception e) {
                logger.catching(Level.WARN, e);
            }
        });
    }
}
