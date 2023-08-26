<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="container md-6 mt-3 me-6">

        <h3>User: ${userChannel.username}</h3>
        <div style="padding-bottom: 10px">${type}</div>
            <ul class="list-group">
                <#list users as user>
                    <li class="list-group-item">
                        <a href="/user-messages/${user.id}">${user.getUsername()}</a>
                    </li>
                </#list>

            </ul>
    </div>

</@c.page>