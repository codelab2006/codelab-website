# codelab-website
[![Build Status](https://travis-ci.com/codelab2006/codelab-website.svg?branch=master)](https://travis-ci.com/codelab2006/codelab-website)

Ensure `CODELAB_MARKDOWN_DATA_SOURCE_CONFIG` environment variable is set and points to your markdown data source config file.

An example of codelab-markdown-data-source.properties:

    # If you want to enable data fetching, ensure MARKDOWN_REPOSITORY_PATH is set
    MARKDOWN_REPOSITORY_PATH=/repository/

    MARKDOWN_DOCUMENT_PATH=/repository/abc/
    MARKDOWN_RESOURCE_PATH=/repository/xyz/

*If you want to configure username and password for data fetching, ensure `codelab.markdown.repository.username`, `codelab.markdown.repository.password` system properties are present at runtime.*

Ensure `CODELAB_WEBSITE_CONFIG` environment variable is set and points to your website config file.

An example of codelab-website.properties:

    RES_PATH=/repository/xyz/

The post template:

    # Enter title here
    > author: Enter author here
    > tags: Enter tags here

    Enter summary here
    **********
    Enter content here

An example of post:

    # Java Collections
    > author: Developer
    > tags: Java, Collection

    The Java Collections API provide Java developers......
    **********
    The Java Collection interface represents the operations possible on a generic collection, like on a List, Set, Stack, Queue and Deque......
