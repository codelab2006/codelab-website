package top.codelab.website.web.controller.read;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import top.codelab.website.service.api.BlogService;
import top.codelab.website.service.api.bo.Post;
import top.codelab.website.service.api.bo.PostInfo;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
class BlogReadController {

    private static final int PAGE_START = 1;
    private static final int NUMBER_PER_PAGE = 5;

    private static final String VIEW_BLOG = "blog";
    private static final String VIEW_POSTS = "posts";
    private static final String VIEW_POST = "post";

    private static final String ATTR_POST = "post";
    private static final String ATTR_POST_INFO_LIST = "postInfoList";
    private static final String ATTR_NAV_ITEMS_BY_LAST_MODIFIED_TIME = "lastModifiedTimeItems";
    private static final String ATTR_NAV_ITEMS_BY_TAG = "tagItems";
    private static final String ATTR_CURRENT_PAGE = "currentPage";
    private static final String ATTR_PAGE_COUNT = "pageCount";

    private static final String ATTR_FILTER_BY_TAG = "filterByTag";
    private static final String ATTR_FILTER_BY_LAST_MODIFIED_TIME = "filterByLastModifiedTime";
    private static final String ATTR_FILTER_RESULT_COUNT = "filteredResultCount";

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private BlogService blogService;

    @GetMapping("/blog")
    ModelAndView blog(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView(VIEW_BLOG);
        List<PostInfo> postInfoList = this.blogService.findAllPostInfo();

        modelAndView.addAllObjects(this.generateNavItems(postInfoList));
        modelAndView.addAllObjects(this.generatePagingInfo(postInfoList, page));

        return modelAndView;
    }

    @GetMapping("/posts")
    ModelAndView posts(@RequestParam(value = "tag", required = false) String tag,
                       @RequestParam(value = "modified", required = false) String modified,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView(VIEW_POSTS);
        List<PostInfo> postInfoList = this.blogService.findAllPostInfo();

        modelAndView.addAllObjects(this.generateNavItems(postInfoList));

        postInfoList = this.filterPostInfoByTagAndModified(postInfoList, tag, modified);
        modelAndView.addAllObjects(this.generateFilterInfo(postInfoList, tag, modified));

        modelAndView.addAllObjects(this.generatePagingInfo(postInfoList, page));

        return modelAndView;
    }

    @GetMapping("/posts/{name}")
    ModelAndView post(@PathVariable String name) {
        ModelAndView modelAndView = new ModelAndView(VIEW_POST);
        Post post = this.blogService.findPostByName(name);
        return modelAndView.addObject(ATTR_POST, post);
    }

    private int calculatePageCount(int collectionSize, int numberPerPage) {
        return (int) Math.ceil(collectionSize / (double) numberPerPage);
    }

    private int correctPage(int page, int pageStart, int pageCount) {
        return page < pageStart ? pageStart : page > pageCount ? pageCount : page;
    }

    private int calculateFromIndexForPaging(int currentPage, int numberPerPage) {
        return (currentPage - 1) * numberPerPage;
    }

    private int calculateToIndexForPaging(int startIndex, int numberPerPage, int collectionSize) {
        int endIndex = startIndex + numberPerPage;
        return endIndex >= collectionSize ? collectionSize : endIndex;
    }

    private Map<String, Object> generateNavItems(List<PostInfo> postInfoList) {
        Map<YearMonth, List<PostInfo>> date2PostInfo = postInfoList.stream()
                .collect(Collectors.groupingBy(PostInfo::getLastModifiedTimeYearMonth, LinkedHashMap::new, Collectors.toList()));
        Map<String, Object> result = new HashMap<>();
        result.put(ATTR_NAV_ITEMS_BY_LAST_MODIFIED_TIME, date2PostInfo);
        result.put(ATTR_NAV_ITEMS_BY_TAG, this.blogService.findAllTag());
        return result;
    }

    private Map<String, Object> generatePagingInfo(List<PostInfo> postInfoList, int page) {
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(postInfoList)) {
            result.put(ATTR_POST_INFO_LIST, Collections.emptyList());
            return result;
        }
        int pageCount = this.calculatePageCount(postInfoList.size(), NUMBER_PER_PAGE);
        page = this.correctPage(page, PAGE_START, pageCount);
        int startIndex = this.calculateFromIndexForPaging(page, NUMBER_PER_PAGE);
        int endIndex = this.calculateToIndexForPaging(startIndex, NUMBER_PER_PAGE, postInfoList.size());
        result.put(ATTR_POST_INFO_LIST, postInfoList.subList(startIndex, endIndex));
        result.put(ATTR_CURRENT_PAGE, page);
        result.put(ATTR_PAGE_COUNT, pageCount);
        return result;
    }

    private Map<String, Object> generateFilterInfo(List<PostInfo> postInfoList, String tag, String modified) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put(ATTR_FILTER_RESULT_COUNT, postInfoList.size());
            if (!StringUtils.isEmpty(tag)) {
                result.put(ATTR_FILTER_BY_TAG, tag);
            }
            if (!StringUtils.isEmpty(modified)) {
                YearMonth yearMonth = YearMonth.parse(modified);
                result.put(ATTR_FILTER_BY_LAST_MODIFIED_TIME, yearMonth);
            }
        } catch (DateTimeParseException e) {
            logger.catching(e);
        }
        return result;
    }

    private List<PostInfo> filterPostInfoByTagAndModified(List<PostInfo> postInfoList, String tag, String modified) {
        try {
            if (!StringUtils.isEmpty(tag)) {
                postInfoList = postInfoList.stream()
                        .filter(postInfo -> postInfo.getTags().contains(tag))
                        .collect(Collectors.toList());
            }
            if (!StringUtils.isEmpty(modified)) {
                YearMonth yearMonth = YearMonth.parse(modified);
                postInfoList = postInfoList.stream()
                        .filter(postInfo -> postInfo.getLastModifiedTimeYearMonth().equals(yearMonth))
                        .collect(Collectors.toList());
            }
        } catch (DateTimeParseException e) {
            logger.catching(e);
        }
        return postInfoList;
    }
}
