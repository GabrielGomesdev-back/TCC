const apiClient = new ApiClient(urlDominioBackend);

async function fillScreen (){
    let json = await apiClient.get("api/v1/FT006/home/user-info?login="+sessionStorage.getItem('login'));
    $("#nomUser").text("Nome: " + json?.data?.name);
    $("#emailUser").text("Email: " + json?.data?.email);
    $("#languageLevel").text("Nível: " + json?.data?.languageLevel);
    await this.loadFeedbacks();
}

async function loadFeedbacks(){
    let json = await apiClient.get("api/v1/FT006/home/analysis?login="+sessionStorage.getItem('login'));
    const feedbackDiv = document.getElementById('feedbackDiv');
    json.data.forEach( (_feedback) => {
        const feedback = document.createElement("div");
        feedback.innerHTML = `  <div class="container-feedback">
                                    <div class="post-header">
                                        <div class="post-user-info">
                                            <span class="post-date">Nível: ${_feedback?.languageLevel} - ${_feedback?.feedbackDate}</span>
                                        </div>
                                    </div>
                                    <div class="post-text">${_feedback?.feedbackUser}<div>
                                </div> `
        feedbackDiv.appendChild(feedback);
    })
}

function toggleMenu() {
    const navLinks = document.getElementById("nav-links");
    const menuIcon = document.getElementById("menu-icon");

    navLinks.classList.toggle("active");
    menuIcon.classList.toggle("active");
}

document.addEventListener("DOMContentLoaded", async () => {
    await fillScreen();
});

async function logout(){
    let json = await apiClient.logout(sessionStorage.getItem('login'));
    if(json.status == "success"){
        sessionStorage.clear();
        window.location.replace(urlDominioFrontend);
    }
}