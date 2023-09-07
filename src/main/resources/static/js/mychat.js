'use strict';

var usernamePage = document.querySelector('#userJoin');
var chatPage = document.querySelector('#chatPage');
var room = $('#room');
var name = $("#name").val().trim();
var waiting = document.querySelector('.waiting');
var roomIdDisplay = document.querySelector('#room-id-display');
var chatBox = document.getElementById("msg_history");

var stompClient = null;
var currentSubscription;
var topic = null;
var username;

function connect(event) {
    var name1 = $("#name").val().trim();
    var chatRoom = $('#chatRoom');
    Cookies.set('name', name1);
    usernamePage.classList.add('d-none');
    chatPage.classList.remove('d-none');
    var socket = new SockJS('/sock');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}

function onConnected() {
    enterRoom(room.val());
    waiting.classList.add('d-none');
}

function onError(error) {
    waiting.textContent = 'uh oh! service unavailable';
}

function enterRoom(newRoomId) {

    var roomId = newRoomId;
    console.log("ROOM ID: " + roomId);
    Cookies.set('roomId', room);
    roomIdDisplay.textContent = roomId;
    topic = `/chat-app/chat/${newRoomId}`;
    currentSubscription = stompClient.subscribe(`/chat-room/${roomId}`, onMessageReceived);

    var username = $("#name").val().trim();
    stompClient.send(`${topic}/addUser`,
        {},
        JSON.stringify({sender: username})
    );
    chatBox.scrollTop = chatBox.scrollHeight;
}

function sendMessage(event) {
    var messageContent = $("#message").val().trim();
    var username = $("#name").val().trim();
    var newRoomId = $('#room').val().trim();
    topic = `/chat-app/chat/${newRoomId}`;
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageContent,
            date: new Date(),
            type: 'CHAT'
        };

        stompClient.send(`${topic}/sendMessage`, {}, JSON.stringify(chatMessage));

        document.querySelector('#message').value = '';

    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var chatRoom = $("#chatRoom").val();
    var message = JSON.parse(payload.body);
    var messageElement = document.createElement('div');
    messageElement.className = 'outgoing_msg';
    messageElement.classList.add('chat-message');

    var spanCard = document.createElement('span');
    spanCard.className = 'time_date';

    var dateTimeString = message.date.toString();
    const time = dateTimeString.substring(11, 16);
    var dateText = document.createTextNode(message.formattedDate);

    spanCard.appendChild(dateText);

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);
    messageElement.appendChild(spanCard);

    var divCardBody = document.createElement('div');
    divCardBody.className = 'div';
    divCardBody.appendChild(messageElement);

    var divCard = document.createElement('div');
    divCard.className = 'sent_msg';
    divCard.appendChild(divCardBody);

    var messageArea = document.querySelector('#messageArea');
    messageArea.appendChild(divCard);

    chatBox.scrollTop = chatBox.scrollHeight;
}

// $(document).ready(function () {
//     // console.log(userJoinForm);
//     // console.log(form);
//
//     // for (let i = 0; i < userJoinForm.length; i++) {
//     //     console.log(userJoinForm[i]);
//     // }
//
//     // for (const form of userJoinForm) {
//     //     const formm = document.getElementById(form);
//     //     const elements = formm.elements();
//     //     if(elements[0] == 22222) formm[0].addEventListener('submit', connect, true);
//     // //     console.log("AAA");
//     //     // console.log(elemts);
//     //     // form.getAttribute(formm.value);
//     //     // const room = document.getElementById("room");
//     //     // console.log(form.getAttribute());
//     //     // if (element.value == 22222) element.addEventListener('submit', connect, true);
//     //     // userJoinForm[2].addEventListener('submit', connect, true);
//     //     // console.log(element.value);
//     // }
//     // userJoinForm[0].addEventListener('submit', connect, true);
//     messagebox.addEventListener('submit', sendMessage, true);
// });


function enterChat(roomid, value1) {
    console.log("BUTTON");
    // userJoinForm.getElementById()

    // Находим элемент формы с идентификатором "myForm"
    const form = document.getElementById("userJoinForm");

// Находим поле формы с идентификатором "myInput"
    const room = document.getElementById("room");
    room.value = roomid;
// Находим поле формы с идентификатором "myInput"
    const name = document.getElementById("name");
    name.value = value1;

    userJoinForm.addEventListener('submit', connect, true);
    messagebox.addEventListener('submit', sendMessage, true);
}