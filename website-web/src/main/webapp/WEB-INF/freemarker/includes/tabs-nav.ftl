<#assign tags>
<#list tagItems as tag, count>
  <a href="<@spring.url '/posts?tag=${tag?url}'/>" class="list-group-item list-group-item-action py-_4">
    <span>${tag}</span><span class="ml-1 bracket">${count}</span>
  </a>
</#list>
</#assign>
<#assign lastModifiedTimes>
<#list lastModifiedTimeItems as date, items>
  <a href="<@spring.url '/posts?modified=${date?url}'/>" class="list-group-item list-group-item-action py-_4">
    <span>${date}</span><span class="ml-1 bracket">${items?size}</span>
  </a>
</#list>
</#assign>
<@html.navTabs title='分类'
               tabs=[{
                 'id':'by-tag',
                 'active':true,
                 'label':'按标签',
                 'content':tags
               },{
                 'id':'by-last-modified-time',
                 'active':false,
                 'label':'按最后修改时间',
                 'content':lastModifiedTimes
               }]/>
