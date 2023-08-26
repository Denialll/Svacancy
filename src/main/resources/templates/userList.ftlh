<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="container md-6">
        <h2 style="text-align: center;">List of users</h2>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Role</th>
                <th scope="col">ID</th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td>${user.id}</td>
                    <td><a href="/user/${user.id}">edit</a></td>
                </tr>
            </#list>

            <#list letters as letter>
                <tr>
                    <td>${letter.text}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

</@c.page>