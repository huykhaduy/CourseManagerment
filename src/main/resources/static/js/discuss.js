'use strict';

var messageForm = document.querySelector('#msg_form');
var messageInput = document.querySelector('#msg_input');
var messageArea = document.querySelector('#msg_area');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
const path = window.location.pathname;
const parts = path.split('/');
const channelId = parts[parts.length - 1]; 

const courseID = document.querySelector('#courseID').value;
const userID = Number(document.querySelector('#userID').value);

var socket = new SockJS('/ws');
stompClient = Stomp.over(socket);

stompClient.connect({}, onConnected, onError);


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({type: 'JOIN'})
    )
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    event.preventDefault();
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            courseID: courseID,
            content: messageContent,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    if (message.type === 'CHAT') {
        message.time = formattedDatetime(message.time);
        if (message.courseID === courseID) {
            var $messageArea = $('#msg_area');
            var messageElement = null;
            if (message.userID !== userID) {
                messageElement = createInComingMessage(message.userName, message.content, message.userAvt, message.time)
            } else {
                messageElement = createOnGoingMessage(message.content, message.time);
            }
            $messageArea.append(messageElement);
            messageArea.scrollTop = messageArea.scrollHeight;
        }
        var elementId = "#"+message.courseID;
        if (!$(elementId).first().is(":first-child")) {
            $(elementId).hide().prependTo("#inbox_chat").fadeIn();
        }
        $(elementId).find('.chat_date').text(message.time);
        $(elementId).find('p').text(message.content);
    }
}

function createInComingMessage(userName, content, imageURL, time) {
    var $incomingMsg = $('<div>').addClass('incoming_msg');
    var $incomingMsgImg = $('<div>').addClass('incoming_msg_img');
    var $img = $('<img>').attr('src', imageURL).attr('alt', 'sunil');
    $incomingMsgImg.append($img);
    var $receivedMsg = $('<div>').addClass('received_msg');
    var $receivedWithdMsg = $('<div>').addClass('received_withd_msg');
    var $name = $('<span>').addClass('time_date').text(userName);
    var $content = $('<p>').text(content);
    var $time = $('<span>').addClass('time_date').text(time);
    $receivedWithdMsg.append($name, $content, $time);
    $receivedMsg.append($receivedWithdMsg);
    $incomingMsg.append($incomingMsgImg, $receivedMsg);
    return $incomingMsg;
}
function createOnGoingMessage(content, time) {
    var $ongoingMsg = $('<div>').addClass('outgoing_msg');
    var $sentMsg = $('<div>').addClass('sent_msg');
    var $content = $('<p>').text(content);
    var $time = $('<span>').addClass('time_date').text(time);
    $sentMsg.append($content);
    $sentMsg.append($time);
    $ongoingMsg.append($sentMsg);
    return $ongoingMsg;
}
function formattedDatetime(date) {
    var d = new Date(date);
    // Định dạng giờ và phút (HH:mm)
    var hours = d.getHours();
    var minutes = d.getMinutes();
    var timeFormatted = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;

    // Định dạng ngày tháng (dd-MM-yyyy)
    var day = d.getDate();
    var month = d.getMonth() + 1;
    var year = d.getFullYear();
    var dateFormatted = `${day.toString().padStart(2, '0')}-${month.toString().padStart(2, '0')}-${year.toString()}`;

    // Kết hợp giờ và ngày đã định dạng
    var dateTimeFormatted = `${timeFormatted} | ${dateFormatted}`;
    return dateTimeFormatted;
}

// function updateInbox() {
//     $.ajax({
//         url: '/discuss/getInbox',
//         method: 'GET',
//         data:{
//             id: userID,
//         },
//         success: function (data) {
//             if (!data.length) return;
//             let html = data.map(item => {
//                 return `<div class=${"chat_list" + (String(item.courseID) === courseID ? " active-chat" : "")} id="${item.courseID}">
//                             <a href="/discuss/${item.courseID}">
//                             <div class="chat_people"><div class="chat_img"> <img src="${item.courseAvt}" alt="course avatar"/> </div>
//                             <div class="chat_ib">
//                                 <h5> ${item.courseName} <span class="chat_date">${(item.time != null)?formattedDatetime(item.time):""}</span></h5>
//                                 <p>${item.message}</p>
//                             </div>
//                             </div>
//                             </a>
//                         </div>`
//             }).join(" ");
//             $('#inbox_chat').html(html);
//         },
//         fail: function() {
//             console.log("ERROR")
//         }
//     });
// }

// function getHistoryDiscuss() {
//     $.ajax({
//         url: '/discuss/get',
//         method: 'GET',
//         data:{
//             id: courseID,
//         },
//         success: function (data) {
//             if (!data.length) return;
//             let html = data.map(item => {
//                 if (item.userID === userID) {
//                     return `div class="outgoing_msg">
//                                 <div class="sent_msg">
//                                     <p>${item.content}</p>
//                                     <span class="time_date">${formattedDatetime(item.time)}</span>
//                                 </div>
//                             </div>`;
//                 }else {
//                     return `<div class="incoming_msg">
//                                 <div class="incoming_msg_img"> <img src="${item.userAvt}" alt="user avatar"> </div>
//                                 <div class="received_msg">
//                                     <div class="received_withd_msg">
//                                         <span class="time_date">${discussion.userName}</span>
//                                         <p>${time.content}</p>
//                                         <span class="time_date">${formattedDatetime(item.time)}</span>
//                                     </div>
//                                 </div>
//                             </div>`;
//                 }

//             }).join(" ");
//             $('#msg_area').html(html);
//         },
//         fail: function() {
//             console.log("ERROR")
//         }
//     });
// }
$("#input_search_bar").on('input',(function(){
    let inputValue =  $("#input_search_bar").val();
    // if (inputValue !== ""){
        $.ajax({
            url: "/discuss/search",
            method: "GET",
            data:{
                id: userID,
                text: inputValue
            },
            success: function(data) {
                if (!data.length) return;
                let html = data.map(item => {
                    return `<div class=${"chat_list" + (String(item.courseID) === courseID ? " active-chat" : "")} id="${item.courseID}">
                                <a href="/discuss/${item.courseID}">
                                <div class="chat_people"><div class="chat_img"> <img src="${item.courseAvt}" alt="course avatar"/> </div>
                                <div class="chat_ib">
                                    <h5> ${item.courseName} <span class="chat_date">${(item.time != null)?formattedDatetime(item.time):""}</span></h5>
                                    <p>${item.message}</p>
                                </div>
                                </div>
                                </a>
                            </div>`
                }).join(" ");
                $('#inbox_chat').html(html);
            },
            fail: function(){
                console.log("fail");
            }
        });
    // }
    // else {
    //     $("#popup-search-box").css("display", "none");
    // }
}));

messageForm.addEventListener('submit', sendMessage, true)

function formattedTime(_time) {
    var time = new Date(_time);
    return time.getHours() + ':' + time.getMinutes() + ' | ' + time.getDate() + '-' + (time.getMonth() + 1) + '-' + time.getFullYear();
}