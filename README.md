# codelab-website
[![Build Status](https://travis-ci.com/codelab2006/codelab-website.svg?branch=master)](https://travis-ci.com/codelab2006/codelab-website)

Ensure `CODELAB_MARKDOWN_DATA_SOURCE_CONFIG` environment variable is set and points to your markdown data source config file.

An example codelab-markdown-data-source.properties:

    # If you want to enable data fetching, ensure MARKDOWN_REPOSITORY_PATH is set
    MARKDOWN_REPOSITORY_PATH=/repository/

    MARKDOWN_DOCUMENT_PATH=/repository/abc/
    MARKDOWN_RESOURCE_PATH=/repository/xyz/

*If you want to configure username and password for data fetching, ensure `codelab.markdown.repository.username`, `codelab.markdown.repository.password` system properties are present at runtime.*

Ensure `CODELAB_WEBSITE_CONFIG` environment variable is set and points to your website config file.

An example codelab-website.properties:

    RES_PATH=/repository/xyz/
