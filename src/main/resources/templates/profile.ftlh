<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <div class="container md-6 mt-3">
        <h3>${userChannel.username}</h3>
        <#if !isCurrentUser>
            <#if isSubscriber>
                <a class="btn btn-info" href="/user/unsubscribe/${userChannel.id}">Unsubscribe</a>
            <#else>
                <a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Subscribe</a>
            </#if>
        </#if>

        <div>Subscription</div>
        <h3 class="card-text">
            <a href="/user/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
        </h3>
        <div>Subscribers</div>
        <h3>
            <a href="/user/subscribers/${userChannel.id}/list">${subscribersCount}</a>
        </h3>
        <h2>
            Role:
            <#if isAdmin>
                Admin
            <#elseif isHR>
                HR
            <#else>
                User
            </#if>
        </h2>
        <div>Password or email changing:</div>
        ${errorMessage?if_exists}
        <form action="/user/profile/${userId}" method="post">
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

        <#if !isHR || !isCreator || !isAdmin>
            <p>
                <button class="btn btn-primary mt-2" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseExample"
                        aria-expanded="false" aria-controls="collapseExample">
                    Do you want to create your company?
                </button>
            </p>

            <div class="collapse <#if textError?? || messageLetter??>show</#if>" id="collapseExample">
                <form action="/user/sendcoveringletter" method="post" enctype="multipart/form-data">
                    <div class="input-group has-validation mb-3">
                        <label class="col-sm-2 col-form-label">Password:</label>
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
                        <label class="col-sm-2 col-form-label">Company info:</label>
                        <textarea class="form-control  ${(textError??)?string('is-invalid', '')}"
                                  value="<#if coveringLetter??>${coveringLetter.text}</#if>"
                                  aria-describedby="inputGroup-sizing-default" type="text" name="text"
                                  placeholder="Enter your covering letter about your job you are offering"
                                  aria-label="With textarea"></textarea>
                        <#if textError??>
                            <div class="invalid-feedback">
                                ${textError}
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

        <#include "parts/vacancyEdit.ftlh">
        <#if isHR>
            <#include "parts/vacancyList.ftlh">
        </#if>

    </div>
</@c.page>
