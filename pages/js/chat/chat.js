const socketUrl = urlDominioWebsocket + 'chat/info';
let stompClient;

async function greetinsChat(){
    $.ajax({
        url : urlDominioBackend + 'api/v1/FT004/classes/greetings?login='+ sessionStorage.getItem('login') + '&language=' + navigator.language,
        type: "GET",
        success: function (data) {
            const chatBox = document.getElementById('chatBox');
            const messageDiv = document.createElement('div');
            messageDiv.classList.add('message', 'received');
            messageDiv.innerHTML = `
                <div class="text">${data.data}</div>
                <div class="message-time">${new Date().toLocaleTimeString()}</div>
            `;
            chatBox.appendChild(messageDiv);
            chatBox.scrollTop = chatBox.scrollHeight;
        },
        error: function (error) {
            console.log(`Error ${error}`);
        }
    });
}

function connect() {
    const socket = new WebSocket(socketUrl);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/message', function(jsonMessage) {
            this.showMessageOutput(jsonMessage);
        });
    }, function (error) {
        console.error('STOMP error: ' + error);
    });
    greetinsChat();
}

function showMessageOutput(data) { 
    let jsonData = JSON.parse(data.body);
    const chatBox = document.getElementById('chatBox');
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', 'received');
    messageDiv.innerHTML = `
        <div class="text">${jsonData.data}</div>
        <div class="message-time">${new Date().toLocaleTimeString()}</div>
    `;
    chatBox.appendChild(messageDiv);
    chatBox.scrollTop = chatBox.scrollHeight;
};

async function sendMessage() {
    const messageInput = document.getElementById('messageInput');
    const chatBox = document.getElementById('chatBox');
    const messageText = messageInput.value.trim();

    if (messageText) {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message', 'sent');
        messageDiv.innerHTML = `
            <div class="text">${messageText}</div>
            <div class="message-time">${new Date().toLocaleTimeString()}</div>
        `;
        chatBox.appendChild(messageDiv);
        messageInput.value = '';
        chatBox.scrollTop = chatBox.scrollHeight;
        stompClient.send("/app/send", {}, JSON.stringify({'login': sessionStorage.getItem('login'), 'language': sessionStorage.getItem("language"), 'message':messageText, 'time': new Date().toLocaleTimeString()}));
    }
}
