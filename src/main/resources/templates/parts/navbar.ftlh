<#include "security.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg text-bg-dark p-3 ">
    <a class="navbar-brand ms-3 text-white" href="#">SVacancy</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">

            <li class="nav-item">
                <a class="nav-link text-white bg-dark" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white bg-dark" href="/main">Vacancies</a>
            </li>
            <#if username != "">
                <li class="nav-item">
                    <a class="nav-link text-white bg-dark" href="/user/profile/${userId}">Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white bg-dark" href="/user-messages/${userId}">My messages</a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link text-white bg-dark" href="/user">User list</a>
                </li>
            </#if>
            <#if isHR>
                <li class="nav-item">
                    <a class="nav-link text-white bg-dark" href="/company/${userId}">Your company</a>
                </li>
            </#if>
        </ul>
        <div class="navbar-text text-white me-3">${username}</div>
        <#if isActive??>
            <@l.logout />
        </#if>
    </div>
</nav>


