<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <#--    <!-- Bootstrap CSS &ndash;&gt;-->
    <#--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"-->
    <#--          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">-->
    <link rel="stylesheet" href="/css/user-messages.css">

</head>

<@c.page>

<#--<#list chatRooms as chatRoom>-->
<#--&lt;#&ndash;            <#list chatRoom.chatMessages as chatMessage>&ndash;&gt;-->
<#--&lt;#&ndash;                <ul>${chatMessage.content}</ul>&ndash;&gt;-->
<#--&lt;#&ndash;            </#list>&ndash;&gt;-->
<#--    <div id="userJoin" class="container">-->
<#--        <form id="userJoinForm" name="userJoinForm">-->
<#--            <input type="hidden" id="room" value="${chatRoom.chatId!""}">-->
<#--            <input type="hidden" id="name" value="${username}">-->
<#--            <button type="submit" class="btn btn-primary">Enter chat</button>-->
<#--        </form>-->
<#--    </div>-->
<#--</#list>-->


<#--<div id="chatPage" class="container d-none">-->
<#--    <div class="chat-header">-->
<#--        <h2>Chatroom [<span id="room-id-display"></span>]</h2>-->
<#--    </div>-->
<#--    <div class="waiting">-->
<#--        We are waiting to enter the room.-->
<#--    </div>-->
<#--    <div class="card">-->
<#--        <div class="card-body">-->
<#--            <#list chatRooms as chatRoom>-->
<#--                <#list chatRoom.chatMessages as chatMessage>-->
<#--                    <ul>-->
<#--                        <li>   ${chatMessage.content}</li>-->
<#--                        <li>   ${chatMessage.sender}</li>-->

<#--                    </ul>-->
<#--                </#list>-->
<#--            </#list>-->
<#--            <ul id="messageArea">-->
<#--        </div>-->
<#--    </div>-->


<#--    </ul>-->
<#--    <form id="messagebox" name="messagebox">-->
<#--        <div class="form-group">-->
<#--            <label for="message">Enter Message:</label>-->
<#--            <input type="text" class="form-control" id="message" aria-describedby="name"-->
<#--                   placeholder="Enter message to chat ....">-->
<#--        </div>-->
<#--        <button type="submit" class="btn btn-primary">Send</button>-->
<#--    </form>-->
<#--</div>-->

<div class="container">
    <div class="messaging">
        <div class="inbox_msg">
            <div class="inbox_people">
                <div class="headind_srch">
                    <div class="recent_heading">
                        <h4>Recent</h4>
                    </div>
                    <div class="srch_bar">
                        <div class="stylish-input-group">
                            <input type="text" class="search-bar" placeholder="Search">
                            <span class="input-group-addon">
                <button type="button"> <i class="fa fa-search" aria-hidden="true"></i> </button>
                </span></div>
                    </div>
                </div>
                <div class="inbox_chat">
                    <div class="chat_list active_chat">
                        <div class="chat_people">
<#--                                                        <div class="chat_img"><img src="https://ptetutorials.com/images/user-profile.png"-->
<#--                                                                                   alt="sunil"></div>-->
<#--                                                        <div class="chat_ib">-->
<#--                                                            <h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>-->
<#--                                                            <p>Test, which is a new approach to have all solutions-->
<#--                                                                astrology under one roof.</p>-->
<#--                                                        </div>-->
<#--                                                        <#list chatRooms as chatRoom>-->
<#--                                                            <div id="userJoin" class="container">-->
<#--                                                                <form id="userJoinForm" name="userJoinForm">-->
<#--                                                                    <input type="hidden" id="room" value="${chatRoom.chatId!""}">-->
<#--                                                                    <input type="hidden" id="name" value="${username}">-->
<#--                                                                    <button type="submit" class="btn btn-primary">Enter chat</button>-->
<#--                                                                </form>-->
<#--                                                            </div>-->
<#--                                                        </#list>-->

                            <button onclick="enterChat('11111', 'lola')">AAAAA</button>

                            <div id="userJoin" class="container">
                                <form id="userJoinForm" name="userJoinForm">
                                    <input type="hidden" id="room" value="22222">
                                    <input type="hidden" id="name" value="22222">
                                    <button type="submit" class="btn btn-primary">Enter chat</button>
                                </form>
                            </div>
                            <div id="userJoin" class="container">
                                <form id="userJoinForm" name="userJoinForm">
                                    <input type="hidden" id="room" value="11111">
                                    <input type="hidden" id="name" value="11111">
                                    <button type="submit" class="btn btn-primary">Enter chat</button>
                                </form>
                            </div>

                        </div>
                    </div>
                    <div class="chat_list">
                        <div class="chat_people">
                            <div class="chat_img"><img src="https://ptetutorials.com/images/user-profile.png"
                                                       alt="sunil"></div>
                            <div class="chat_ib">
                                <h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
                                <p>Test, which is a new approach to have all solutions
                                    astrology under one roof.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="mesgs">
                <div id="msg_history">
                    <div class="outgoing_msg">
                        <#--                        <div class="sent_msg">-->
                        <#--                            <p>Apollo University, Delhi, India Test</p>-->
                        <#--                            <span class="time_date"> 11:01 AM    |    Today</span></div>-->

                        <div id="chatPage" class="container d-none">
                            <div class="chat-header">
                                <h2>Chatroom [<span id="room-id-display"></span>]</h2>
                            </div>
                            <div class="waiting">
                                We are waiting to enter the room.
                            </div>

                            <#list chatRooms as chatRoom>
                                <#list chatRoom.chatMessages as chatMessage>

                                <#--                                    ${chatMessage.sender}-->
                                    <div class="outgoing_msg">
                                        <div class="sent_msg">
                                            <p>${chatMessage.content}</p>
                                            <#--                                            <p>${chatMessage.sender}</p>-->
                                            <div class="time_date"> ${chatMessage.getFormattedDate()!''}</div>
                                        </div>
                                    </div>
                                </#list>
                            </#list>
                            <div class="incoming_msg">
                                <div class="incoming_msg_img"><img
                                            src="https://ptetutorials.com/images/user-profile.png"
                                            alt="sunil"></div>
                                <div class="received_msg">
                                    <div class="received_withd_msg">
                                        <p>We work directly with our designers and suppliers,
                                            and sell direct to you, which means quality, exclusive
                                            products, at a price anyone can afford.</p>
                                        <span class="time_date"> 11:01 AM    |    Today</span></div>
                                </div>
                            </div>
                            <div id="messageArea"></div>

                            <#--                            <div class="outgoing_msg">-->
                            <#--                                <div class="sent_msg">-->
                            <#--                                    -->
                            <#--                                    <span class="time_date"> 11:01 AM    |    Today</span></div>-->
                            <#--                            </div>-->


                        </div>
                    </div>

                </div>

                <div class="type_msg">
                    <div class="input_msg_write">
                        <form id="messagebox" name="messagebox">
                            <input type="text" class="write_msg" id="message" aria-describedby="name"
                                   placeholder="Type a message">
                            <button class="msg_send_btn" type="submit"><i class="fa fa-paper-plane-o"
                                                                          aria-hidden="true"></i></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
        <script src="/js/mychat.js"></script>
        </@c.page>
