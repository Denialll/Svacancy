<#import "parts/common.ftlh" as c>
<#import "parts/passwordrecovery.ftlh" as p>

<@c.page>
    <div class="container md-5 mt-5">
        <h2>Company: ${company.name}</h2>
        <h4>Creator: <a href="/userprofile/${author.id}">${author.username}</a></h4>
        <#if isCurrentUser>
            <h2 style="text-align: center;">List of employees</h2>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Username</th>
                    <th scope="col">Job</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list employees as employee>
                    <tr>
                        <td><a href="/userprofile/${employee.id}">${employee.username}</a></td>
                        <td>${employee.id}</td>
                        <td><a href="/user/${employee.id}">edit</a></td>
                        <td>
                            <form action="/removeEmploy/${employee.id}" method="post">
                                <button type="submit" class="btn btn-danger">Remove</button>
                            </form>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>
        <#include "parts/vacancyList.ftlh">
    </div>
</@c.page>