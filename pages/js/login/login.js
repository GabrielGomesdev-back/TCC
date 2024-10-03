const apiClient = new ApiClient(urlDominioBackend);

async function loginUser(){
    let mapUser = {
        login:    $("#login").val(),
        password: $("#password").val()
    };

    sessionStorage.setItem("login", $("#login").val());
    let data = await apiClient.post("api/v1/FT003/auth/user-login", mapUser)

    if(data.status == "success"){
        alert(data.message);
        window.location.replace(urlDominioFrontend + "pages/html/chat/chat.html");
    } else {
        alert("We didn't can found a account with the inserted informations, please review them");
    }
}