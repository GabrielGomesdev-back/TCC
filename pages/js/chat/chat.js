const socketUrl = urlDominioWebsocket + 'chat/info';
let stompClient;

async function greetinsChat() {
    let json = await apiClient.get("api/v1/FT006/home/user-info?login="+sessionStorage.getItem('login'));
    $("#loginUsuario").text("Nome: " + json?.data?.name);

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
        stompClient.subscribe('/topic/message', function (jsonMessage) {
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

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('messageInput').addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            sendMessage();
        }
    });
});

let mediaRecorder;
let audioChunks = [];
let audioBlob;
let isRecording = false;
let timerInterval;
let seconds = 0;

// Inicia ou para a gravação
function toggleRecording() {
    console.log("teste1")
    if (!isRecording) {
        startRecording();
    } else {
        stopRecording();
    }
}

// Inicia a gravação do áudio
async function startRecording() {
    console.log("teste");
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
    mediaRecorder = new MediaRecorder(stream);

    mediaRecorder.ondataavailable = event => audioChunks.push(event.data);

    mediaRecorder.onstop = () => {
        audioBlob = new Blob(audioChunks, { type: 'audio/wav' });
        audioChunks = [];
        showAudioControls();
    };

    mediaRecorder.start();
    isRecording = true;
    startTimer();

    microm('#recordButton').text('Stop');
    microm('#sendMessage').hide();
    microm('#sendAudioButton').hide();
    microm('#audioPlayer').show();
    microm('#messageInput').hide();
    microm('#recordingTime').show();
    microm('#deleteAudioButton').hide();

    // document.getElementById('recordButton').innerText = 'Stop';
    // document.getElementById('sendMessage').style.display = 'none'; // Esconde o botão de envio de mensagem ao iniciar a gravação
    // document.getElementById('sendAudioButton').style.display = 'none';
    // document.getElementById('audioPlayer').style.display = 'block';
    // document.getElementById('messageInput').style.display = 'none';
    // document.getElementById('recordingTime').style.display = 'block';
    // document.getElementById('deleteAudioButton').style.display = 'none';

}

function deleteRecording() {
    audioBlob = null;
    document.getElementById('audioPlayer').style.display = 'none';
    document.getElementById('messageInput').style.display = 'block';
    document.getElementById('sendAudioButton').style.display = 'none';
    document.getElementById('deleteAudioButton').style.display = 'none';
    document.getElementById('recordButton').innerText = '🎙️';
    document.getElementById('recordButton').style.display = 'inline';
    ocument.getElementById('sendMessage').style.display = 'inline'; 
}

// Para a gravação do áudio
function stopRecording() {
    mediaRecorder.stop();
    isRecording = false;
    stopTimer();

    document.getElementById('recordingTime').style.display = 'none';
    document.getElementById('recordButton').style.display = 'none';
    document.getElementById('sendAudioButton').style.display = 'inline';
}

function startTimer() {
    seconds = 0;
    updateTimerDisplay();
    timerInterval = setInterval(() => {
        seconds++;
        updateTimerDisplay();
        if (seconds >= 180) {
            stopRecording(); // Para automaticamente após 3 minutos
        }
    }, 1000);
}
function stopTimer() {
    clearInterval(timerInterval);
    document.getElementById('recordingTime').style.display = 'none';
    document.getElementById('deleteAudioButton').style.display = 'inline';
}

function updateTimerDisplay() {
    const minutes = String(Math.floor(seconds / 60)).padStart(2, '0');
    const secs = String(seconds % 60).padStart(2, '0');
    document.getElementById('recordingTime').textContent = `${minutes}:${secs}`;
}

// Mostra os controles após a gravação
function showAudioControls() {
    const audioUrl = URL.createObjectURL(audioBlob);
    const audioPlayer = document.getElementById('audioPlayer');
    audioPlayer.src = audioUrl;
    audioPlayer.style.display = 'block';
}

// Envia o áudio e exibe no chat
function sendAudio() {
    const audioUrl = URL.createObjectURL(audioBlob);

    // Cria uma nova mensagem de áudio para o chat
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', 'sent', 'audio-message');

    const audioPlayer = document.createElement('audio');
    audioPlayer.controls = true;
    audioPlayer.controlsList = 'nodownload nofullscreen';
    audioPlayer.src = audioUrl;

    messageDiv.appendChild(audioPlayer);

    // Adiciona ao contêiner do chat
    document.getElementById('chatBox').appendChild(messageDiv);

    // Esconde o player e o botão de envio de áudio
    document.getElementById('audioPlayer').style.display = 'none';
    document.getElementById('sendAudioButton').style.display = 'none';
    document.getElementById('sendMessage').style.display = 'inline';
    document.getElementById('messageInput').style.display = 'inline';
}

function toggleMenu() {
    const navLinks = document.getElementById("nav-links");
    const menuIcon = document.getElementById("menu-icon");

    navLinks.classList.toggle("active");
    menuIcon.classList.toggle("active");
}

async function logout(){
    let json = await apiClient.logout(sessionStorage.getItem('login'));
    if(json.status == "success"){
        sessionStorage.clear();
        window.location.replace(urlDominioFrontend);
    }
}