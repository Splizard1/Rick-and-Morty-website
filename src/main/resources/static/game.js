let characterName = "";
let answerLength = 0;
let revealedLetters = [];
let hintStep = 0;


function startGame() {
    fetch("/api/game/start")
        .then(res => res.json())
        .then(data => {

            const img = document.getElementById("character-image");

            // force reload
            img.src = "";       // clear previous
            img.src = data.image;  // set new URL

            img.className = "blur-heavy";

            document.getElementById("species").innerText = "Species: " + data.species;
            document.getElementById("status").innerText = "Status: " + data.status;
            document.getElementById("gender").innerText = "Gender: " + data.gender;

            document.getElementById("species").classList.add("hidden");
            document.getElementById("status").classList.add("hidden");
            document.getElementById("gender").classList.add("hidden");

            hintStep = 0;
            revealedLetters = Array(data.nameLength).fill("_");

            updateNameDisplay();
        });
}


function revealHint() {
    hintStep++;

    const img = document.getElementById("character-image");

    switch (hintStep) {
        case 1:
            // First hint: show species + reduce blur slightly
            document.getElementById("species").classList.remove("hidden");
            img.className = "blur-medium";
            break;
        case 2:
            // Second hint: show status + reduce blur more
            document.getElementById("status").classList.remove("hidden");
            img.className = "blur-light";
            break;
        case 3:
            // Third hint: show gender + remove blur
            document.getElementById("gender").classList.remove("hidden");
            img.className = "blur-none";
            break;
        default:
            // No more hints
            console.log("All hints revealed");
            break;
    }
}


function revealLetter() {
    fetch("/api/game/reveal-letter")
        .then(res => res.json())
        .then(data => {
            revealedLetters[data.index] = data.letter;
            updateNameDisplay();
        });
}


function updateNameDisplay() {
    document.getElementById("name-display").innerText =
        revealedLetters.join(" ");
}

function submitGuess() {
    const guess = document.getElementById("guess-input").value;

    fetch("/api/game/guess", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({guess})
    })
        .then(res => res.json())
        .then(data => {
            document.getElementById("result").innerText =
                data.correct ? "✅ Correct!" : "❌ Wrong!";
        });
}

// Auto-start game when page loads
window.onload = function () {
    startGame();
};
