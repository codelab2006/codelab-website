<#import '/spring.ftl' as spring>
<#import '/html.ftl' as html>
<@html.main navbar=true active='blog'>
<div class="container p-2">
  <div class="d-md-flex flex-row-reverse align-items-start">
    <div class="flex-shrink-0 w-md-16 ml-md-2 mb-4">
      <#include "/includes/tabs-nav.ftl">
    </div>
    <div class="flex-grow-1">
      <#if filteredResultCount??>
      <div class="d-flex mb-4 align-items-center bg-primary text-white p-2">
        <#assign filters=[{'name':filterByTag!'', 'label':'标签'},
                          {'name':filterByLastModifiedTime!'', 'label':'最后修改时间'}]>
        <#list filters as filter>
          <#if filter.name?has_content>
          <span class="colon mr-1">${filter.label}</span>
          <span class="mr-2">${filter.name}</span>
          </#if>
        </#list>
        <span class="bracket">共${filteredResultCount}篇文章</span>
      </div>
      </#if>
      <#include "/includes/post-info-list.ftl">
    </div>
  </div>
</div>
</@html.main>
