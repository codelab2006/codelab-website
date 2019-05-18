<#import '/spring.ftl' as spring>
<#import '/html.ftl' as html>
<@html.main>
<div class="container-fluid h-100">
  <div class="row h-100 align-items-center justify-content-center">
    <div class="col-8 col-md-4 bg-white rounded-xl shadow-sm p-3 border">
      <form class="form-inline" action="<@spring.url '/refresh'/>" method="POST">
        <input type="text" class="flex-grow-1 form-control mr-2 w-0" name="password" id="password" placeholder="Password">
        <button type="submit" class="btn btn-primary">刷新</button>
        <div class="d-flex w-100 mt-5">
          <span class="mr-2">Snapshot:</span>
          <span>${currentDateTime}</span>
        </div>
      </form>
    </div>
  </div>
</div>
</@html.main>
