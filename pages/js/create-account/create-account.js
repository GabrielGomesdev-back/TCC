const apiClient = new ApiClient(urlDominioBackend);

function assessPasswordStrength(password) {
    const minLength = 8;
    const minLowercase = 1;
    const minUppercase = 1;
    const minNumbers = 1;
    const minSpecialChars = 1;

    let strength = 0;

    // Check length
    if (password.length >= minLength) {
        strength++;
    }

    // Check lowercase characters
    if (password.match(/[a-z]/g) && password.match(/[a-z]/g).length >= minLowercase) {
        strength++;
    }

    // Check uppercase characters
    if (password.match(/[A-Z]/g) && password.match(/[A-Z]/g).length >= minUppercase) {
        strength++;
    }

    // Check numbers
    if (password.match(/[0-9]/g) && password.match(/[0-9]/g).length >= minNumbers) {
        strength++;
    }

    // Check special characters
    if (password.match(/[^a-zA-Z0-9]/g) && password.match(/[^a-zA-Z0-9]/g).length >= minSpecialChars) {
        strength++;
    }

    return strength;
}

function updateStrengthIndicator(password) {
    const strength = assessPasswordStrength(password);

    let strengthText = "";
    var color = "";

    switch (strength) {
    case 0:
        strengthText = "Very Weak";
        color="red";
        break;
    case 1:
        strengthText = "Weak";
        color="orange";
        break;
    case 2:
        strengthText = "Moderate";
        color="yellow";
        break;
    case 3:
        strengthText = "Strong";
        color="lightgreen";
        break;
    case 4:
        strengthText = "Very Strong";
        color="green";
        break;
    default:
        strengthText = "";
    }

    var strengthIndicator = document.getElementById("password-strength");
    strengthIndicator.textContent = "Strength: " + strengthText;
    document.getElementById("password-strength").style.color = color;
}

async function verifyLogin(login){
    let json = await apiClient.get("api/v1/FT003/auth/verify-login?login="+login);
    if(json.status != "success"){
        alert("Try another login, this login is already in use")
    }
}

async function verificarCriacao(){

    if($("#email").val() == ""){
        alert("You need to write the email to confirm");
        return false;
    } else if($("#email").val() != $("#confirmEmail").val()){
        alert("The first email needs to be equal than second");
        return false;
    }

    if($("#password").val() == ""){
        alert("You need to write the password to confirm");
        return false;
    } else if($("#password").val() != $("#confirmPassword").val()){
        alert("The first password needs to be equal than second");
        return false;
    }

    let mapUser = {
        name:     $("#firstName").val() + " " + $("#lastName").val(),
        login:    $("#login").val(),
        email:    $("#email").val(),
        password: $("#password").val(),
        language: $("#languages").val()
    };
    
    sessionStorage.setItem("login", $("#login").val());
    let data = await apiClient.post("api/v1/FT003/auth/user-register", mapUser)

    if(data.status == "success"){
        alert(data.message);
        window.location.replace(urlDominioFrontend + "pages/html/chat/chat.html");

    } else {
        alert("You didn't can create your account, review the informations inserted");
    }
}