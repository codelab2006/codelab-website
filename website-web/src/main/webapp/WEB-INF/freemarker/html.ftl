<#import '/spring.ftl' as spring>
<#macro main title='Codelab'
             navbar=false
             active=''
             items=[{'/blog/':'网络日志'}, <#--{'/tutorials/':'教程'},--> {'/about/':'About'}]>
<!doctype html>
<html class="h-100">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="shortcut icon" href="<@spring.url '/favicon.ico'/>">
  <link rel="stylesheet" href="<@spring.url '/static/bootstrap.min.css'/>">
  <link rel="stylesheet" href="<@spring.url '/static/style.css'/>">
  <title>${title}</title>
</head>
<body class="h-100 <#if navbar>pt-nav</#if>">
<#if navbar>
<nav class="navbar navbar-light bg-light navbar-background-color fixed-top navbar-expand-md px-0">
  <div class="container px-2">
    <a class="navbar-brand" href="<@spring.url '/'/>">
      <img src="<@spring.url '/static/logo.png'/>" width="36" height="36" alt="">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-md-end" id="navbarNav">
      <div class="navbar-nav">
        <#list items as item>
          <#list item as href, label>
            <a class="nav-item nav-link <#if href?contains(active)>active</#if>" href="<@spring.url href/>">${label}</a>
          </#list>
        </#list>
      </div>
    </div>
  </div>
</nav>
</#if>
<#nested>
<script src="<@spring.url '/static/jquery.min.js'/>"></script>
<script src="<@spring.url '/static/bootstrap.bundle.min.js'/>"></script>
<script src="<@spring.url '/static/script.js'/>"></script>
</body>
</html>
</#macro>

<#macro lastModifiedTime time='******'>
<div class="text-info small"><span class="colon mr-1">最后修改时间</span><span>${time}</span></div>
</#macro>

<#macro author author='******'>
<div class="text-body"><span class="colon mr-1">作者</span><span>${author}</span></div>
</#macro>

<#macro tags items=[]>
<div class="item-mr-x-container d-flex align-items-center">
  <#list items as item><a class="item-mr-2 badge badge-primary text-wrap" href="<@spring.url '/posts?tag=${item?url}'/>">${item}</a></#list>
</div>
</#macro>

<#macro navTabs title='' tabs=[]>
<div class="card">
  <div class="card-header d-flex align-items-center">
    <h6 class="flex-grow-1 flex-shrink-0 m-0 font-weight-normal mr-3">${title}</h6>
    <ul class="nav nav-pills small fs-68">
      <#list tabs as tab>
      <li><a class="nav-link px-1 py-0 <#if tab.active>active</#if>" data-toggle="pill" href="#${tab.id}">${tab.label}</a></li>
      </#list>
    </ul>
  </div>
  <div class="tab-content">
    <#list tabs as tab>
    <div id="${tab.id}" class="tab-pane <#if tab.active>active</#if>">
      <div class="list-group list-group-flush fs-85">
      ${tab.content}
      </div>
    </div>
    </#list>
  </div>
</div>
</#macro>

<#macro pagination startPage=1
                   currentPage=1
                   pageCount=1
                   relativeUrl=springMacroRequestContext.requestUri?remove_beginning(springMacroRequestContext.contextPath)>
<#assign q=(springMacroRequestContext.queryString!'')?replace('&?page=\\d*', '', 'r')?remove_beginning('&')>
<#assign relativeUrlWithQuery=relativeUrl?remove_ending('/') + q?has_content?then('?${q}&', '?')>
<nav>
  <ul class="pagination pagination-sm">
    <#if startPage<currentPage>
      <#list [{'page':startPage, 'label':'<<'}, {'page':currentPage - 1, 'label':'<'}] as item>
      <li class="page-item">
        <a class="page-link" href="<@spring.url '${relativeUrlWithQuery}page=${item.page}'/>">${item.label}</a>
      </li>
      </#list>
    </#if>
    <#list -2..2 as offset>
      <#assign page=currentPage + offset>
      <#if startPage<=page && page<=pageCount>
        <li class="page-item <#if page==currentPage>active</#if>">
          <a class="page-link" href="<@spring.url '${relativeUrlWithQuery}page=${page}'/>">${page}</a>
        </li>
      </#if>
    </#list>
    <#if currentPage<pageCount>
      <#list [{'page':currentPage + 1, 'label':'>'}, {'page':pageCount, 'label':'>>'}] as item>
      <li class="page-item">
        <a class="page-link" href="<@spring.url '${relativeUrlWithQuery}page=${item.page}'/>">${item.label}</a>
      </li>
      </#list>
    </#if>
  </ul>
</nav>
</#macro>

<#macro card title='' text=''>
<div class="card m-2">
  <div class="card-body d-flex flex-column">
    <h5 class="card-title">${title}</h5>
    <p class="card-text">${text}</p>
    <a href="" class="btn btn-primary align-self-end">开始学习</a>
  </div>
</div>
</#macro>
