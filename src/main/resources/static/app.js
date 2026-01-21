let debounceTimer;

function randomCharacter() {
    document.getElementById("results").innerHTML = "";

    const id = Math.floor(Math.random() * 826) + 1;

    fetch(`/api/characters/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Character not found");
            }
            return response.json();
        })
        .then(character => renderResults([character]))
        .catch(error => {
            document.getElementById("results").innerHTML =
                `<p>${error.message}</p>`;
        });
}


function searchCharacter(characterName) {
    const name = characterName || document.getElementById("nameInput").value;

    if (!name || name.trim() === "") {
        alert("Please enter a character name");
        return;
    }

    hideSuggestions();

    fetch(`/api/characters?name=${encodeURIComponent(name)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("No characters found");
            }
            return response.json();
        })
        .then(data => renderResults(data))
        .catch(error => {
            document.getElementById("results").innerHTML =
                `<p>${error.message}</p>`;
        });
}

function renderResults(characters) {
    const resultsDiv = document.getElementById("results");
    resultsDiv.innerHTML = "";

    characters.forEach(character => {
        const card = document.createElement("div");
        card.className = "card";

        card.innerHTML = `
            <img src="${character.image}" alt="${character.name}">
            <h3>${character.name}</h3>
            <p>${character.species}</p>
        `;

        card.onclick = () => openModal(character);

        resultsDiv.appendChild(card);
    });
}

function handleInput(event) {
    const input = event.target.value.trim();

    clearTimeout(debounceTimer);

    if (input.length < 2) {
        hideSuggestions();
        return;
    }

    debounceTimer = setTimeout(() => {
        fetchSuggestions(input);
    }, 300);
}

function fetchSuggestions(query) {
    fetch(`/api/characters?name=${encodeURIComponent(query)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("No suggestions found");
            }
            return response.json();
        })
        .then(characters => {
            displaySuggestions(characters.slice(0, 5));
        })
        .catch(error => {
            hideSuggestions();
        });
}

function displaySuggestions(characters) {
    const suggestionsDiv = document.getElementById("suggestions");
    suggestionsDiv.innerHTML = "";

    if (characters.length === 0) {
        hideSuggestions();
        return;
    }

    characters.forEach(character => {
        const item = document.createElement("div");
        item.className = "suggestion-item";

        item.innerHTML = `
            <img src="${character.image}" alt="${character.name}">
            <div class="suggestion-info">
                <span class="suggestion-name">${character.name}</span>
                <span class="suggestion-species">${character.species}</span>
            </div>
        `;

        item.onclick = () => selectSuggestion(character);

        suggestionsDiv.appendChild(item);
    });

    suggestionsDiv.classList.remove("hidden");
}

function selectSuggestion(character) {
    hideSuggestions();
    document.getElementById("nameInput").value = "";
    openModal(character);
}

function hideSuggestions() {
    const suggestionsDiv = document.getElementById("suggestions");
    if (suggestionsDiv) {
        suggestionsDiv.classList.add("hidden");
    }
}

function openModal(character) {
    document.getElementById("modalImage").src = character.image;
    document.getElementById("modalName").innerText = character.name;
    document.getElementById("modalStatus").innerText = character.status;
    document.getElementById("modalSpecies").innerText = character.species;
    document.getElementById("modalGender").innerText = character.gender;
    document.getElementById("modalOrigin").innerText = character.origin.name;
    document.getElementById("modalLocation").innerText = character.location.name;

    document.getElementById("characterModal").classList.remove("hidden");
}

function closeModal() {
    document.getElementById("characterModal").classList.add("hidden");
}

document.addEventListener("DOMContentLoaded", function () {
    const nameInput = document.getElementById("nameInput");
    const modal = document.getElementById("characterModal");

    if (nameInput) {
        nameInput.addEventListener("input", handleInput);

        nameInput.addEventListener("keypress", function (e) {
            if (e.key === "Enter") {
                searchCharacter();
            }
        });
    }

    if (modal) {
        modal.addEventListener("click", function (e) {
            if (e.target.id === "characterModal") {
                closeModal();
            }
        });
    }

    document.addEventListener("click", function (e) {
        if (!e.target.closest(".autocomplete-wrapper")) {
            hideSuggestions();
        }
    });
});