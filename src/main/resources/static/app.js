function searchCharacter() {
    const name = document.getElementById("nameInput").value;

    if (!name) {
        alert("Please enter a character name");
        return;
    }

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

function openModal(character) {
    document.getElementById("modalImage").src = character.image;
    document.getElementById("modalName").innerText = character.name;
    document.getElementById("modalStatus").innerText = character.status;
    document.getElementById("modalSpecies").innerText = character.species;
    document.getElementById("modalGender").innerText = character.gender;
    document.getElementById("modalOrigin").innerText = character.origin;
    document.getElementById("modalLocation").innerText = character.location;

    document.getElementById("characterModal").classList.remove("hidden");
}

function closeModal() {
    document.getElementById("characterModal").classList.add("hidden");
}

document.getElementById("characterModal").addEventListener("click", function (e) {
    if (e.target.id === "characterModal") {
        closeModal();
    }
});
