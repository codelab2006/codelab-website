<#import '/spring.ftl' as spring>
<#import '/html.ftl' as html>
<@html.main navbar=true active='blog'>
<div class="container p-2">
  <div class="d-md-flex flex-row-reverse align-items-start">
    <div class="flex-shrink-0 w-md-16 ml-md-2">
      <#include "/includes/tabs-nav.ftl">
    </div>
    <div class="flex-grow-1">
      <#include "/includes/post-info-list.ftl">
    </div>
  </div>
</div>
</@html.main>
