package top.codelab.website.service.api.bo;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostInfo {

    private String name;
    private String title;
    private String author;
    private List<String> tags;
    private String summary;
    private LocalDateTime creationTime;
    private LocalDateTime lastModifiedTime;

    public String getName() {
        return this.name;
    }

    public PostInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public PostInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return this.author;
    }

    public PostInfo setAuthor(String author) {
        this.author = author;
        return this;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public PostInfo setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public String getSummary() {
        return this.summary;
    }

    public PostInfo setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    private LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    public PostInfo setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    private LocalDateTime getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    public PostInfo setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public YearMonth getLastModifiedTimeYearMonth() {
        return YearMonth.from(this.getLastModifiedTime());
    }

    public String getLastModifiedTimeString() {
        return this.getLastModifiedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getCreationTimeString() {
        return this.getCreationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
