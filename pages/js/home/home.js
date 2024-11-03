function toggleMenu() {
    const navLinks = document.getElementById("nav-links");
    const menuIcon = document.getElementById("menu-icon");

    navLinks.classList.toggle("active");
    menuIcon.classList.toggle("active");
}

document.addEventListener("DOMContentLoaded", () => {
    const calendarDays = document.querySelector(".calendar-days");

    // Função para preencher o calendário
    function renderCalendar() {
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth();
        const currentYear = currentDate.getFullYear();

        // Cria todos os dias do mês atual
        const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();
        for (let day = 1; day <= daysInMonth; day++) {
            const dayElement = document.createElement("div");
            dayElement.classList.add("day");
            dayElement.textContent = day;

            // Verifica se o dia corresponde ao login e muda a cor
            if (localStorage.getItem("loginDay") === `${currentYear}-${currentMonth + 1}-${day}`) {
                dayElement.style.backgroundColor = "#C595FF"; // Marca o dia de login
            }

            calendarDays.appendChild(dayElement);
        }
    }

    // Salva a data do login no localStorage (somente uma vez por sessão)
    if (!localStorage.getItem("loginDay")) {
        const today = new Date();
        localStorage.setItem("loginDay", `${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}`);
    }

    // Chama a função para renderizar o calendário
    renderCalendar();
});
