<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="container md-6">
        <h2 style="text-align: center;">User editor</h2>
        <form action="/user" method="post">
            <input type="text" name="username" value="${user.username}"/>
            <#list roles as role>
                <div>
                    <label><input type="checkbox"
                                  name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                </div>
            </#list>
            <input type="hidden" value="${user.id}" name="userId"/>
            <button type="submit">Save</button>
        </form>
    </div>
</@c.page>
