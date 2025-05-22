let originalText = "";
const notIn = "Campo non inizializzato!";

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('blocker').classList.toggle("blocker");
    originalText = document.getElementById("descText").innerHTML;
    createEmptyGrid('player-grid');
    createEmptyGrid('computer-grid');
    placingButtons();

    initialize();
    checkWinOnload();
    document.getElementById("autoPlace").addEventListener("click", function () {
        placeAuto();
        startAttack();
    });
});

function initialize() {
    $.ajax({
        url: '/getGrid',
        method: 'GET',
        success: function (response) {
            placeShips(response);
            if (response.player.allShips && response.player.allShips.length > 0) {
                startAttack();

            }
        },
        error: function () {
            alert('Errore nel caricamento delle griglie!');
        }
    });
}

function checkWinOnload() {
    $.ajax({
        url: '/checkWin',
        method: 'GET',
        success: function (response) {
            if (response === 1) {
                displayText("Il giocatore ha vinto!", 10000);
                blockUI();
            } else if (response === 2) {
                displayText("Il computer ha vinto!", 10000);
                blockUI();
            }
        },
        error: function () {
            console.error("Errore nel controllo vittoria.");
        }
    });
}

function startAttack() {
    document.getElementById("buttons").innerHTML = "";
    document.getElementById("descText").innerHTML = "ATTACCA! clicca su una casella del campo avversario<br>";
    originalText = document.getElementById("descText").innerHTML;
}

function placingButtons() {
    /*const manuallyButton = document.createElement('button');
    manuallyButton.setAttribute('type', 'button');
    manuallyButton.classList.add('btn', 'btn-outline-dark', 'w-25', 'me-5');
    manuallyButton.id = 'manPlace';
    manuallyButton.textContent = 'Place Manually';*/

    // Create the "Place Automatically" button
    const autoPlaceButton = document.createElement('button');
    autoPlaceButton.setAttribute('type', 'button');
    autoPlaceButton.classList.add('btn', 'btn-outline-dark', 'w-25','ms-5');
    autoPlaceButton.id = 'autoPlace';
    autoPlaceButton.textContent = 'Place Automatically';

    // Append buttons to the container
    document.getElementById("buttons").append(autoPlaceButton);
}

function createEmptyGrid(container) {
    for (let i = 0; i < 100; i++) {
        let item = document.createElement("div");
        item.setAttribute("data-index", parseInt(i / 10) + "" + i % 10);
        item.classList.add("cell", "justify-content-center", "align-items-center","fs-2");
        document.getElementById(container).append(item);
    }
}

function placeAuto() {
    $.ajax({
        url: '/popGrid',
        method: 'GET',
        success: function (response) {
            placeShips(response);
        },
        error: function () {
            alert('Errore nel caricamento delle griglie!');
        }
    });
}

function placeShips(response) {
    const colors = ["#FE57F3", "#33FF57", "#3357FF", "#F1C40F", "#8E44AD", "#1ABC9C", "#E74C3C", "#3498DB"];
    let colorIndex = 0;

    response.player.allShips.forEach(element => {
        element.ship.forEach(element1 => {
            const cell = $('#player-grid .cell').eq(element1.posX + "" + element1.posy);
            cell.addClass('ship');
            cell.css('background-color', colors[colorIndex]);
            if (element1.hit) {
                cell.html("X");
            }
        });
        colorIndex++;
    });

    // Place computer ships (for visual representation)

    //colorIndex = 0;
    response.computer.allShips.forEach(element => {
        element.ship.forEach(element1 => {
            const cell = $('#computer-grid .cell').eq(element1.posX + "" + element1.posy);
            /*cell.addClass('ship');
            cell.css('background-color', colors[colorIndex]);*/
            if (element1.hit) {
                cell.html("X");
            }
        });
        //colorIndex++;
    });

    // PLAYER has alreadyHit to computer?
    response.player.alreadyHit.forEach(hit => {
        const cell = $(`#player-grid .cell[data-index="${hit}"]`);
        cell.html("~");
    });

    // COMPUTER alreadyHit
    response.computer.alreadyHit.forEach(hit => {
        const cell = $(`#computer-grid .cell[data-index="${hit}"]`);
        cell.html("~");
    });
}

document.getElementById("computer-grid").addEventListener("click", function (event) {

    if (event.target && event.target.hasAttribute("data-index")) {
        let dataIndex = event.target.getAttribute("data-index");
        attack(dataIndex);
    }
});

function attack(index) {
    $.ajax({
        url: "/attack/" + index,
        method: "GET",
        success: function (response) {
            playerAttack(response.playerResult, index);
            computerAttack(response.computerResult);

        }

    })
}

function playerAttack(response, index) {
    const alrHit = "Casella con x: " + parseInt(index / 10) + " ,y: " + index % 10 + " già colpita !!";
    const sunk = "Il giocatore ha affondato una barca!";
    const won = "Il giocatore ha vinto!";
    switch (response) {
        case -2:
            displayText(alrHit, 5000);
            break;
        case -1:
            displayText(notIn, 5000);
            break;
        case 0:
            document.getElementById("computer-grid").querySelector(`.cell[data-index="${index}"`).innerHTML = "X";
            break;
        case 1:
            document.getElementById("computer-grid").querySelector(`.cell[data-index="${index}"`).innerHTML = "X";
            displayText(won, 5000);
            blockUI();
            break;
        case 2:
            document.getElementById("computer-grid").querySelector(`.cell[data-index="${index}"`).innerHTML = "X";
            displayText(sunk, 5000);
            break;
        case 3:
            document.getElementById("computer-grid").querySelector(`.cell[data-index="${index}"`).innerHTML = "~";
    }
}

function computerAttack(response) {
    let index = response[0] + "" + response[1];
    const sunk = "Il computer ha affondato una barca!";
    const won = "Il computer ha vinto!";

    switch (response[2]) {
        case -1:
            displayText(notIn, 5000);
            break;
        case 0:
            document.getElementById("player-grid").querySelector(`.cell[data-index="${index}"`).innerHTML = "X";
            break;
        case 1:
            document.getElementById("player-grid").querySelector(`.cell[data-index="${index}"`).innerHTML = "X";
            displayText(won, 5000);
            blockUI();
            break;
        case 2:
            document.getElementById("player-grid").querySelector(`.cell[data-index="${index}"`).innerHTML = "X";
            displayText(sunk, 5000);
            break;
        case 3:
            document.getElementById("player-grid").querySelector(`.cell[data-index="${index}"`).innerHTML = "~";
            break;
    }
}


function displayText(text, time) {
    const textMess = document.getElementById("descText");
    if (!textMess.innerHTML.includes(text)) {
        textMess.innerHTML += text;
        setTimeout(function () {
            textMess.innerHTML = "";
            textMess.innerHTML = originalText;
        }, time);
    }
}

function blockUI() {
    document.getElementById('blocker').classList.toggle("blocker");
    const reset = document.createElement('button');
    reset.setAttribute('type', 'button');
    reset.classList.add('btn', 'btn-outline-secondary', 'w-25', 'me-5', 'replay', "top-50", "start-50", "translate-middle");
    reset.id = 'replay';
    reset.textContent = 'Replay';
    document.getElementById("buttons").append(reset);

        document.getElementById("replay").addEventListener("click", function () {
        $.ajax({
            url: '/restartGame',
            method: "DELETE",
            success: function () {
                location.reload();
            },
            error: function () {
                console.error("Errore.");
            }
        });
    })
}


