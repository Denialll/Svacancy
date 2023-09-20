<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/css/user-messages.css">
</head>

<@c.page>
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
                        <#list chatRooms as chatRoom>
                            <div class="chat_list active_chat">
                                <div class="chat_people"
                                     onclick="enterChat(${chatRoom.chatId}, ${username})">
                                    <div class="chat_img"><img src="https://ptetutorials.com/images/user-profile.png"
                                                               alt="sunil"></div>
                                    <div class="chat_ib">
                                        <h5>${username}<span
                                                    class="chat_date">${chatRoom.chatMessages?last.getFormattedDate()}</span>
                                        </h5>
                                        <p>${chatRoom.chatMessages?last.content}</p>
                                    </div>
                                </div>
                            </div>
                        </#list>

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
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@2/src/js.cookie.min.js"></script>
    <script src="/js/mychat.js"></script>
</@c.page>
