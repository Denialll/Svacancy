    'use strict';

    
var chatPage = document.querySelector('#chatPage');
var room = 111;
var name = null;
var waiting = document.querySelector('.waiting');
var roomIdDisplay = document.querySelector('#room-id-display');
var chatBox = document.getElementById("msg_history");

var stompClient = null;
var currentSubscription;
var topic = null;
var username;

function connect(chatroom, name) {
    Cookies.set('name', name);
    console.log(chatPage)
    chatPage.classList.remove('d-none');
    var socket = new SockJS('/sock');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}

function onConnected() {
    enterRoom(room);
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

    var username = name;
    stompClient.send(`${topic}/addUser`,
{},
    JSON.stringify({sender: username})
    );
    chatBox.scrollTop = chatBox.scrollHeight;
}

function sendMessage(event) {
    var messageContent = $("#message").val().trim();
    var username = name;
    var newRoomId = room;
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

    var dateTimeString = message.date;
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
    messagebox.addEventListener('submit', sendMessage, true);
});


function enterChat(roomid, username) {
    console.log("BUTTON");

    room = roomid;
    name = username;

    connect(roomid, username);

    messagebox.addEventListener('submit', sendMessage, true);
}
