package top.codelab.website.service.impl;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.apache.commons.lang3.StringUtils;
import top.codelab.markdown.Cache;
import top.codelab.markdown.MarkdownFile;
import top.codelab.markdown.MarkdownRepository;
import top.codelab.markdown.Util;
import top.codelab.website.service.api.exception.IllegalFormatException;

import java.util.*;
import java.util.stream.Collectors;

class ServiceImpl {

    private static final int ONE = 1;
    private static final String TITLE_PREFIX = "# ";
    private static final String AUTHOR_PREFIX = "> author:";
    private static final String TAG_PREFIX = "> tags:";
    private static final String CONTENT_SEPARATOR = "**********";
    private static final int TITLE_BEGIN_INDEX = 0;
    private static final int AUTHOR_BEGIN_INDEX = 1;
    private static final int TAG_BEGIN_INDEX = 2;
    private static final int SUMMARY_BEGIN_INDEX = 4;

    private static final String KEY_PREFIX_TAGS = "tags_in_";

    private static final int INDENT_SIZE = 2;
    private final Parser parser;
    private final HtmlRenderer renderer;

    final MarkdownRepository markdownRepository;
    private final Cache<String, Object> cache;

    ServiceImpl(MarkdownRepository markdownRepository) {
        this.markdownRepository = markdownRepository;
        this.cache = new Cache<>();
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().indentSize(INDENT_SIZE).build();
    }

    private String extractLine(MarkdownFile markdownFile, int index, String prefix, String messageFormat) {
        String line = markdownFile.getLines().get(index);
        if (!line.startsWith(prefix)) {
            throw new IllegalFormatException(String.format(messageFormat, line));
        }
        return line;
    }

    String extractTitle(MarkdownFile markdownFile) {
        String line = this.extractLine(markdownFile, TITLE_BEGIN_INDEX, TITLE_PREFIX, "Illegal post title: %s");
        return line.substring(line.indexOf(TITLE_PREFIX) + TITLE_PREFIX.length());
    }

    String extractAuthor(MarkdownFile markdownFile) {
        String line = this.extractLine(markdownFile, AUTHOR_BEGIN_INDEX, AUTHOR_PREFIX, "Illegal post author: %s");
        return line.substring(line.indexOf(AUTHOR_PREFIX) + AUTHOR_PREFIX.length()).trim();
    }

    List<String> extractTags(MarkdownFile markdownFile) {
        String line = this.extractLine(markdownFile, TAG_BEGIN_INDEX, TAG_PREFIX, "Illegal post tags: %s");
        String[] tags = line.substring(line.indexOf(TAG_PREFIX) + TAG_PREFIX.length()).split(",");
        return Arrays.stream(tags).map(String::trim).collect(Collectors.toList());
    }

    String extractSummary(MarkdownFile markdownFile) {
        List<String> lines = markdownFile.getLines();
        int separatorIndex = lines.indexOf(CONTENT_SEPARATOR);
        if (separatorIndex < 0) {
            throw new IllegalFormatException("Illegal post summary: separator not found");
        }
        return lines.subList(SUMMARY_BEGIN_INDEX, separatorIndex).stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    String extractContent(MarkdownFile markdownFile) {
        List<String> lines = markdownFile.getLines();
        int separatorIndex = lines.indexOf(CONTENT_SEPARATOR) + ONE;
        return lines.subList(separatorIndex, lines.size()).stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    String parseMarkdown(String input) {
        return StringUtils.chomp(this.renderer.render(this.parser.parse(input)));
    }

    void refreshDocumentTree() {
        this.markdownRepository.refreshDocumentTree();
    }

    Map<String, Long> findTags(String directoryName) {
        String key = KEY_PREFIX_TAGS.concat(directoryName);
        Map<String, Long> value = Util.cast(this.cache.get(key));
        return Optional.ofNullable(value).orElseGet(() -> {
            Map<String, Long> tags = this.markdownRepository.findMarkdownFiles(directoryName).stream()
                    .map(this::extractTags)
                    .flatMap(Collection::stream)
                    .sorted()
                    .collect(Collectors.groupingBy(String::toString, LinkedHashMap::new, Collectors.counting()));
            this.cache.add(key, tags);
            return tags;
        });
    }

    void refreshCache() {
        this.cache.clear();
    }
}
