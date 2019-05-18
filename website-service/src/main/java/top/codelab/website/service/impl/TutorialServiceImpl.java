package top.codelab.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.codelab.markdown.MarkdownRepository;
import top.codelab.website.service.api.TutorialService;

@Service
class TutorialServiceImpl extends ServiceImpl implements TutorialService {

    @Autowired
    TutorialServiceImpl(MarkdownRepository markdownRepository) {
        super(markdownRepository);
    }
}
