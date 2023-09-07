<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="container md-5 mt-5">
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
                    <td><a href="/adminpanel/${user.id}">edit</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
        <br>
        <h2 style="text-align: center;">List of Companies</h2>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Creator</th>
                <th scope="col">Description</th>
                <th scope="col">Email</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#if companies??>
                <#list companies as company>
                    <tr>
                        <td>${company.name}</td>
                        <td>${company.creator.username}</td>
                        <td>${company.description}</td>
                        <td>${company.creator.email}</td>
                        <td>
                            <form action="/adminpanel/acceptCompany/${company.id}" method="post">
                                <button type="submit" class="btn btn-success">Accept</button>
                            </form>
                            <form action="/adminpanel/rejectCompany/${company.id}" method="post">
                                <button type="submit" class="btn btn-danger">Reject</button>
                            </form>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>