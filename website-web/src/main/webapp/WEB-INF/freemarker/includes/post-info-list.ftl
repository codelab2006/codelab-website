<div class="item-mb-x-container">
  <#list postInfoList as item>
    <div class="item-mb-2 card">
      <div class="card-body">
        <h5 class="card-title font-weight-normal mb-1">
          <a href="<@spring.url '/posts/${item.name?url}'/>" class="card-link text-dark">${item.title}</a>
        </h5>
        <@html.tags items=item.tags/>
        <div class="card-text mt-4 markdown-container">
        ${item.summary}
        </div>
        <@html.lastModifiedTime time='${item.lastModifiedTimeString}'/>
      </div>
    </div>
  </#list>
</div>
<#if currentPage?? && pageCount??>
<div class="mt-2">
  <@html.pagination currentPage=currentPage pageCount=pageCount/>
</div>
</#if>
