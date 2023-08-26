<#import "parts/common.ftlh" as c>
<#import "parts/passwordrecovery.ftlh" as p>

<@c.page>
    <h2>Company: ${company.name}</h2>
    <h4>Creator: <a href="/user/profile/${author.id}">${author.name}</a></h4>
    <#if isHR && isCurUsrFromThsComp>
        <h2 style="text-align: center;">List of users</h2>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Username</th>
                <th scope="col">Job</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#if employees??>
                <#list employees as employee>
                    <tr>
                        <td><a href="/user/profile/${employee.id}">${employee.name}</a>${employee.username}</td>
                        <td>${employee.id}</td>
                        <td><a href="/user/${employee.id}">edit</a></td>
                    </tr>
                </#list>
            </#if>

            </tbody>
        </table>
    </#if>
</@c.page>