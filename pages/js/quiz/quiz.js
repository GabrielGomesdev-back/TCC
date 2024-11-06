const apiClient = new ApiClient(urlDominioBackend);

let currentIndex = 0;
let questionsCorrect = 0;

const question = document.querySelector(".question");
const answers = document.querySelector(".answers");
const spnQtd = document.querySelector(".spnQtd");
const textFinish = document.querySelector(".finish span");
const content = document.querySelector(".content");
const contentFinish = document.querySelector(".finish");
const btnRestart = document.querySelector(".finish button");
var correctQuestions = "";
var questions =  "";
await findQuestions();

btnRestart.onclick = async () => {
  let mapJson = {
    user: sessionStorage.getItem('login'),
    questions: correctQuestions 
  };
  await apiClient.post('api/v1/FT005/quiz/generate-feedback', mapJson);
  window.location.replace(urlDominioFrontend + "/pages/html/home/home.html");
};

function nextQuestion(e) {
  if (e.target.getAttribute("data-correct") === "true") {
    correctQuestions = correctQuestions + currentIndex + ",";
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
  let jsonResponse = await apiClient.get('api/v1/FT005/quiz/generate-question?login='+ sessionStorage.getItem('login') + "&language=" + navigator.language)
  sessionStorage.setItem("questions", jsonResponse.data);
  questions = jsonResponse.data;
  loadQuestion();
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