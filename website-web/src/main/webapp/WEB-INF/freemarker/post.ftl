<#import '/html.ftl' as html>
<@html.main navbar=true active='blog'>
<div class="container p-2">
<h1>${post.info.title}</h1>
<div><@html.tags items=post.info.tags/></div>
<div class="d-flex align-items-center mb-5">
  <div class="flex-grow-1"><@html.author author=post.info.author/></div>
  <div class="flex-shrink-0"><@html.lastModifiedTime time='${post.info.lastModifiedTimeString}'/></div>
</div>
<div class="markdown-container">
${post.info.summary}
</div>
<div class="markdown-container">
${post.content}
</div>
</div>
</@html.main>
