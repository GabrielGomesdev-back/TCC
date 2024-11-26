var queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

//const urlDominioFrontend  = "http://127.0.0.1:5500/"
//const urlDominioBackend   = "http://localhost:8084/";
//const urlDominioWebsocket = "ws://localhost:8084/";

const urlDominioFrontend  = "https://you-speakingcom.online/"
const urlDominioBackend   = "https://you-speaking-api.tech/";
const urlDominioWebsocket = "ws://you-speaking-api.tech/";

window.urlDominioBackend   = urlDominioBackend;
window.urlDominioFrontend  = urlDominioFrontend;
window.urlDominioWebsocket = urlDominioWebsocket;

