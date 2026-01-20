let characterName = "";
let answerLength = 0;
let revealedLetters = [];
let hintStep = 0;

function startGame() {
    fetch("/api/game/start")
        .then(res => res.json())
        .then(data => {
            console.log("Game data:", data);

            const img = document.getElementById("character-image");
            img.src = "";
            img.src = data.image;
            img.className = "blur-heavy";

            document.getElementById("species").innerText = "Species: " + data.species;
            document.getElementById("status").innerText = "Status: " + data.status;
            document.getElementById("gender").innerText = "Gender: " + data.gender;

            document.getElementById("species").classList.add("hidden");
            document.getElementById("status").classList.add("hidden");
            document.getElementById("gender").classList.add("hidden");

            hintStep = 0;
            answerLength = data.nameLength;

            // Initialize all as underscores
            revealedLetters = Array(data.nameLength).fill("_");

            // Reveal special characters (spaces, apostrophes, etc.)
            if (data.revealedCharacters) {
                Object.keys(data.revealedCharacters).forEach(index => {
                    revealedLetters[index] = data.revealedCharacters[index];
                });
            }

            updateNameDisplay();
            document.getElementById("guess-input").value = "";
            document.getElementById("result").innerText = "";
        })
        .catch(error => {
            console.error("Error starting game:", error);
            document.getElementById("result").innerText = "❌ Error loading game";
        });
}

function revealHint() {
    hintStep++;

    const img = document.getElementById("character-image");

    switch (hintStep) {
        case 1:
            document.getElementById("species").classList.remove("hidden");
            img.className = "blur-medium";
            break;
        case 2:
            document.getElementById("status").classList.remove("hidden");
            img.className = "blur-light";
            break;
        case 3:
            document.getElementById("gender").classList.remove("hidden");
            img.className = "blur-none";
            break;
        default:
            console.log("All hints revealed");
            break;
    }
}

function revealLetter() {
    fetch("/api/game/reveal-letter")
        .then(res => res.json())
        .then(data => {
            console.log("Revealed letter:", data);
            revealedLetters[data.index] = data.letter;
            updateNameDisplay();
        })
        .catch(error => {
            console.error("Error revealing letter:", error);
        });
}


function updateNameDisplay() {
    let display = "";

    for (let i = 0; i < revealedLetters.length; i++) {
        if (revealedLetters[i] === " ") {
            display += "   "; // 3 spaces for word break
        } else {
            display += revealedLetters[i] + " "; // character + 1 space
        }
    }

    const nameDiv = document.getElementById("name-display");
    nameDiv.innerText = display.trim();
    nameDiv.style.whiteSpace = "pre-wrap"; // Allow wrapping

    // Add smaller font class for long names (more than 20 characters)
    if (revealedLetters.length > 20) {
        nameDiv.classList.add("long-name");
    } else {
        nameDiv.classList.remove("long-name");
    }
}

function submitGuess() {
    const guess = document.getElementById("guess-input").value.trim();

    if (!guess) {
        document.getElementById("result").innerText = "⚠️ Please enter a guess";
        return;
    }

    fetch("/api/game/guess", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({guess})
    })
        .then(res => res.json())
        .then(data => {
            console.log("Guess result:", data);
            if (data.correct) {
                document.getElementById("result").innerText =
                    "✅ Correct!" + (data.name ? " It was " + data.name : "");
                document.getElementById("character-image").className = "blur-none";

                // If backend sends the name on correct guess
                if (data.name) {
                    revealedLetters = data.name.split("");
                    updateNameDisplay();
                }
            } else {
                document.getElementById("result").innerText = "❌ Wrong! Try again";
            }
        })
        .catch(error => {
            console.error("Error submitting guess:", error);
        });
}

// Auto-start game when page loads
window.onload = function () {
    startGame();
};