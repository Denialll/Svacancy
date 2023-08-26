<#include "security.ftlh">
<#import "pager.ftlh" as p>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/static/css/vacancy-card.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500&display=swap" rel="stylesheet">
    <title>Hello, world!</title>
</head>

<@p.pager url page/>

<#list page.content as vacancy>
    <div class="col data-id=${vacancy.id}">
        <div class="cardd" onclick="openWindow()">
            <#if vacancy.filename??>
                <img class="card-img-top" src="/img/${vacancy.filename}">
            </#if>
            <div class='card-title'>
                ${vacancy.title}
            </div>
            <div class="card-salary">
                ${vacancy.salary}
            </div>
            <div class='card-sub'>
                ${vacancy.description}
            </div>
            <div class='card-tag'>
                ${vacancy.tag}
            </div>

            <a class="col align-self-center"
               href="/user/profile/${vacancy.author.id}"
            > ${vacancy.authorName}</a>

            <div class='btn-keeper'>
                <#if vacancy.author.id == userId && isHR>
                <a type="button" class="col btn btn-info"
                   href="/user-messages/${vacancy.author.id}?vacancy=${vacancy.id}"> Edit </a>
                <#elseif isUser>

                <#--                <button type="button" class="btn btn-primary" data-bs-toggle="modal"-->
                <#--                        data-bs-target="#staticBackdrop">-->
                <#--                    Respond-->
                <#--                </button>-->
                <a class="col align-self-center" href="/messages/${vacancy.id}/like">
                    <#if vacancy.meLiked>
                        <i class="fas fa-heart"></i>
                    <#else>
                        <i class="far fa-heart"></i>
                    </#if>
                    ${vacancy.likes}
                </a>
            </div>
        </div>

        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">Modal title</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="/respond" method="post">
                                            <textarea
                                                    class="form-control  ${(contentError??)?string('is-invalid', '')}"
                                                    value="<#if chatMessage??>${chatMessage.content}</#if>"
                                                    aria-describedby="inputGroup-sizing-default"
                                                    type="text" name="content"
                                                    placeholder="Enter your covering letter about your job you are offering"
                                                    aria-label="With textarea"
                                                    style="height: 100px"></textarea>
                            <#if contentError??>
                                <div class="invalid-feedback">
                                    ${contentError}
                                </div>
                            </#if>
                            <#assign roomId = "" + userId + vacancy.author.id>
                            <input type="hidden" name="currentVacancyId" value="${vacancy.id}"/>
                            <input type="hidden" name="sender" value="${username}"/>
                            <input type="hidden" name="chatId" value="${roomId}"/>
                            <button type="submit" class="btn btn-primary" style="margin-top: 5px">
                                Respond
                            </button>
                        </form>

                        <div id="chatPage" class="container d-none">
                            <div class="chat-header">
                                <h2>Chatroom [<span id="room-id-display"></span>]</h2>
                            </div>
                            <div class="waiting">
                                We are waiting to enter the room.
                            </div>
                            <div class="card">
                                <div class="card-body">
                                    <ul id="messageArea"></ul>
                                </div>
                            </div>
                            <form id="messagebox" name="messagebox">
                                <div class="form-group">
                                    <label for="message">Enter Message:</label>
                                    <input type="text" class="form-control" id="message"
                                           aria-describedby="name"
                                           placeholder="Enter message to chat ....">
                                </div>
                                <button type="submit" class="btn btn-primary">Send</button>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <#--        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false"-->
        <#--             tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">-->
        <#--            -->
        <#--        </div>-->
        </#if>


    </div>

<#else>
    No vacancies.
</#list>

<script>
    // Находим карточку по ее идентификатору
    function openWindow() {
        // Устанавливаем обработчик события клика на карточку
        // Находим модальное окно по его идентификатору
        const modal = document.getElementById('staticBackdrop');
        // Открываем модальное окно
        const myModal = new bootstrap.Modal(modal);
        myModal.show();
    }


</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="/js/main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
        crossorigin="anonymous"></script>

<@p.pager url page/>