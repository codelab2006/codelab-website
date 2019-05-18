<#import '/html.ftl' as html>
<@html.main navbar=true active='tutorials'>
<div class="container px-0">
  <div class="row no-gutters">
    <#list 1..20 as item>
    <div class="col-12 col-md-6 col-lg-4"><@html.card title='教程${item}标题' text='教程${item}摘要'/></div>
    </#list>
  </div>
</div>
</@html.main>
