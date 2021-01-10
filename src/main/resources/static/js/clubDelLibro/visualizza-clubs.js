const ordinamenti = {
    alfabetico: (a,b) => a.nome > b.nome ? -1 : 1,
    iscritti: x => x,
    creazione: x => x
}

clubs.sort(ordinamenti[ordinamento]);

function clearTable(table) {
    let tableRows = table.getElementsByTagName('tr');
    let rowCount = tableRows.length;

    for (let x = rowCount - 1; x > 0; x = x - 1) {
        table.removeChild(tableRows[x]);
    }
}

function clubToRow(row, club) {
    function makeCol(index, content) {
        let col = row.insertCell(index);
        col.classList.add("align-middle");
        col.innerHTML = content;
    }

    let srcCopertina = "data:image/png;base64," + club.immagineCopertina;
    
    let copertinaElem = document.createElement("img");
    copertinaElem.src = srcCopertina;
    copertinaElem.setAttribute("height", 120);

    let copertinaLink = document.createElement("a");
    copertinaLink.href = "/club-del-libro/" + club.idClub;
    copertinaLink.appendChild(copertinaElem);

    let copertina = row.insertCell(0);
    copertina.classList.add("align-middle");
    copertina.appendChild(copertinaLink);

    makeCol(1, club.nome);
    makeCol(2, club.descrizione);
    makeCol(3, club.generi.join(", "));
    makeCol(4, club.nomeEsperto);
    makeCol(5, club.iscritti);

}

function populateTable(table) {
    for (let club of clubs) {
        let row = table.insertRow()
        clubToRow(row, club);
    }

    $.bootstrapSortable({ applyLast: true })
}

function regenerateTable() {
    let table = document.getElementById("tabellaClubs");

    clearTable(table);

    populateTable(table);
}

document.addEventListener("DOMContentLoaded", function(event) {
    clubs.sort(ordinamenti[ordinamento]) 
    regenerateTable();
});
  