# codelab-website
[![Build Status](https://travis-ci.com/codelab2006/codelab-website.svg?branch=master)](https://travis-ci.com/codelab2006/codelab-website)

Ensure `CODELAB_MARKDOWN_DATA_SOURCE_CONFIG` environment variable is set and points to your markdown config file.

An example codelab-markdown-data-source.properties:

    MARKDOWN_REPOSITORY_USERNAME=username
    MARKDOWN_REPOSITORY_PASSWORD=password
    MARKDOWN_REPOSITORY=/repository/
    MARKDOWN_DOC_PATH=/repository/abc/
    MARKDOWN_RES_PATH=/repository/xyz/

Ensure `CODELAB_WEBSITE_CONFIG` environment variable is set and points to your website config file.

An example codelab-website.properties:

    RES_PATH=/repository/xyz/
