<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>
<#include "parts/security.ftlh">
<@c.page>
    <div class="container md-5 mt-5">
        <#list users as user>
            <h1>${user.username!''}</h1>
        </#list>
    </div>
</@c.page>
