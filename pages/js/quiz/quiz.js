const question = document.querySelector(".question");
const answers = document.querySelector(".answers");
const spnQtd = document.querySelector(".spnQtd");
const textFinish = document.querySelector(".finish span");
const content = document.querySelector(".content");
const contentFinish = document.querySelector(".finish");
const btnRestart = document.querySelector(".finish button");
var rittenQuestions = [];
var questions = await findQuestions();

let currentIndex = 0;
let questionsCorrect = 0;

btnRestart.onclick = () => {
  window.location.replace(urlDominioFrontend + "/pages/html/home/home.html");
};

function nextQuestion(e) {
  if (e.target.getAttribute("data-correct") === "true") {
    rittenQuestions.push(currentIndex++);
    questionsCorrect++;
  }

  if (currentIndex < questions.length - 1) {
    currentIndex++;
    loadQuestion();
  } else {
    finish();
  }
}

function finish() {
  textFinish.innerHTML = `você acertou ${questionsCorrect} de ${questions.length}`;
  content.style.display = "none";
  contentFinish.style.display = "flex";
}

async function findQuestions(){
    $.ajax({
        url : urlDominioBackend + 'api/v1/FT005/quiz/generate-question?login='+ sessionStorage.getItem('login') + "&language=" + navigator.language,
        type: "GET",
        success: function (data) {
          sessionStorage.setItem("questions", data.data);
          questions = data.data;
          loadQuestion();
        },
        error: function (error) {
            console.log(`Error ${error}`);
        }
    });
}

function loadQuestion() {
  spnQtd.innerHTML = `${currentIndex + 1}/${questions.length}`;
  const item = questions[currentIndex];
  answers.innerHTML = "";
  question.innerHTML = item.texto;

  item.alternativas.forEach((answer) => {
    const div = document.createElement("div");

    div.innerHTML = `
    <button class="answer" data-correct="${answer.correta}">
      ${answer.resposta}
    </button>
    `;

    answers.appendChild(div);
  });

  document.querySelectorAll(".answer").forEach((item) => {
    item.addEventListener("click", nextQuestion);
  });
}