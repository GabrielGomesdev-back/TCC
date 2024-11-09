var queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

const urlDominioFrontend  = "http://127.0.0.1:5500/"
const urlDominioBackend   = "http://localhost:8084/";
const urlDominioWebsocket = "ws://localhost:8084/";

//const urlDominioFrontend  = "http://you-speaking.s3-website-sa-east-1.amazonaws.com/"
//const urlDominioBackend   = "http://localhost:8084/";
//const urlDominioWebsocket = "ws://15.228.63.234:8084/";

window.urlDominioBackend   = urlDominioBackend;
window.urlDominioFrontend  = urlDominioFrontend;
window.urlDominioWebsocket = urlDominioWebsocket;