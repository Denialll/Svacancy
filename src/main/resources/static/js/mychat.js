'use strict';

// var usernamePage = document.querySelector('#userJoin');
// var chatPage = document.querySelector('#chatPage');
// var room = $('#room');
// var name = $("#name").val().trim();
var waiting = document.querySelector('.waiting');
var roomIdDisplay = document.querySelector('#room-id-display');
var chatBox = document.getElementById("msg_history");

// const userJoinForm = document.getElementById('userJoinForm')

var stompClient = null;
var currentSubscription;
var topic = null;
var username;



function onConnected(roomId, username) {
    enterRoom(roomId, username);
    waiting.classList.add('d-none');
}

function onError(error) {
    waiting.textContent = 'uh oh! service unavailable';
}

function enterRoom(newRoomId, username) {
    var roomId = newRoomId;
    console.log("ROOM ID: " + roomId);
    Cookies.set('roomId', roomId);
    roomIdDisplay.textContent = roomId;
    topic = `/chat-app/chat/${newRoomId}`;
    currentSubscription = stompClient.subscribe(`/chat-room/${newRoomId}`, onMessageReceived);

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

$(document).ready(function () {
    // userJoinForm.addEventListener('submit', connect, true);

    messagebox.addEventListener('submit', sendMessage, true);
});

// function connect(event) {
//     var name1 = $("#name").val().trim();
//     var chatRoom = $('#chatRoom');
//     Cookies.set('name', name1);
//     usernamePage.classList.add('d-none');
//     chatPage.classList.remove('d-none');
//     var socket = new SockJS('/sock');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, onConnected, onError);
//     event.preventDefault();
// }

function enterChat(roomId, username){
    Cookies.set('name', username);
    // usernamePage.classList.add('d-none');
    // chatPage.classList.remove('d-none');
    var socket = new SockJS('/sock');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected(roomId, username), onError);
}

// $(document).ready(function() {
//     $("#userJoinForm").submit('submit', connect, true);
//         messagebox.addEventListener('submit', sendMessage, true);

// });

// function passValue(value) {
//     document.getElementById("valueDisplay").innerText = value;
// }
//
// function openModal(value) {
//     document.getElementById("valueDisplay").textContent = value;
//     document.getElementById("myModal").style.display = "block";
// }