<#import "parts/common.ftlh" as c>
<#import "parts/vacancyEdit.ftlh" as edit>
<#include "parts/security.ftlh">

<@c.page>

    <div class="container md-5 mt-5">
        <h3>${userChannel.username}</h3>
        <#if !isCurrentUser>
            <#if isSubscriber>
                <a class="btn btn-info" href="/unsubscribe/${userChannel.id}">Unsubscribe</a>
            <#else>
                <a class="btn btn-info" href="/subscribe/${userChannel.id}">Subscribe</a>
            </#if>
        </#if>

        <div>Subscription</div>
        <h3 class="card-text">
            <a href="/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
        </h3>
        <div>Subscribers</div>
        <h3>
            <a href="/subscribers/${userChannel.id}/list">${subscribersCount}</a>
        </h3>
        <h2>
            Role:
            <#if isAdmin>
                Admin
            <#elseif isCreator>
                Company director
            <#elseif isHR>
                HR
            <#else>
                User
            </#if>
        </h2>
        <div>Password or email changing:</div>
        ${errorMessage?if_exists}
        <form action="/user/${userId}" method="post">
            <div class="form-group row mt-2">
                <label class="col-sm-2 col-form-label">Password:</label>
                <div class="col-sm-4">
                    <input type="password" name="password" class="form-control" placeholder="Password"/>
                </div>
            </div>
            <div class="form-group row mt-2">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-4">
                    <input type="email" name="email" class="form-control " placeholder="youreMail@mail.com"
                           value="${email!''}"/>
                </div>
            </div>
            <p></p>
            <#if message??>
                <div class="alert alert-${messageType}" role="alert">
                    ${message}
                </div>
            </#if>
            <button class="btn btn-primary" type="submit">Save</button>
            <p></p>
        </form>

        <#if isUser && !isHR && !isCreator && !isAdmin>
            <p>
                <button class="btn btn-primary mt-2" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseExample"
                        aria-expanded="false" aria-controls="collapseExample">
                    Do you want to create your company?
                </button>
            </p>

            <div class="collapse <#if nameError?? || INNError?? || descriptionError?? || messageLetter??>show</#if>"
                 id="collapseExample">
                <form action="/createcompany" method="post" enctype="multipart/form-data">
                    <div class="input-group has-validation mb-3">
                        <label class="col-sm-2 col-form-label">Company name:</label>
                        <input class="form-control ${(nameError??)?string('is-invalid', '')}"
                               value="<#if name??>${company.name}</#if>" aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-default" type="text" name="name"
                               placeholder="Company Name"/>
                        <#if nameError??>
                            <div class="invalid-feedback">
                                ${nameError}
                            </div>
                        </#if>
                    </div>
                    <div class="input-group has-validation mb-3">
                        <label class="col-sm-2 col-form-label">INN:</label>
                        <input class="form-control ${(INNError??)?string('is-invalid', '')}"
                               value="<#if company??>${company.INN}</#if>" aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-default" type="text" name="INN"
                               placeholder="Your INN"/>
                        <#if INNError??>
                            <div class="invalid-feedback">
                                ${INNError}
                            </div>
                        </#if>
                    </div>
                    <div class="input-group has-validation mb-3">
                        <label class="col-sm-2 col-form-label">Company description:</label>
                        <textarea class="form-control ${(descriptionError??)?string('is-invalid', '')}"
                                  value="<#if company??>${company.description}</#if>"
                                  aria-describedby="inputGroup-sizing-default" type="text" name="description"
                                  placeholder="Description of company"
                                  aria-label="With textarea"></textarea>
                        <#if descriptionError??>
                            <div class="invalid-feedback">
                                ${descriptionError}
                            </div>
                        </#if>
                    </div>
                    <#if messageLetter??>
                        <div class="alert alert-${messageType}" role="alert">
                            ${messageLetter}
                        </div>
                    </#if>
                    <button type="submit" class="btn btn-info">Send</button>
                </form>
            </div>
        </#if>

<#--        <#if isCurrentUser>-->
<#--        </#if>-->
<#--        <#include "parts/vacancyEdit.ftlh" "/vacancyCreate">-->

            <@edit.vacancyEdit "/vacancyCreate"/>
        <#include "parts/vacancyList.ftlh">

    </div>


</@c.page>
