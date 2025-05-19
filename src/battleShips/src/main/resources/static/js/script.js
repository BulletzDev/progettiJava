let originalText = "";

document.addEventListener("DOMContentLoaded", function () {
    originalText = document.getElementById("descText").innerHTML;
    createEmptyGrid('player-grid');
    createEmptyGrid('computer-grid');
    placingButtons();

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

    document.getElementById("autoPlace").addEventListener("click", function () {
        placeAuto();
        startAttack();
    });

});

function startAttack() {
    document.getElementById("buttons").innerHTML = "";
    document.getElementById("descText").innerHTML = "ATTACCA! clicca su una casella del campo avversario<br>";
    originalText = document.getElementById("descText").innerHTML;
}

function placingButtons() {
    const manuallyButton = document.createElement('button');
    manuallyButton.setAttribute('type', 'button');
    manuallyButton.classList.add('btn', 'btn-outline-secondary', 'w-25', 'me-5');
    manuallyButton.id = 'manPlace';
    manuallyButton.textContent = 'Place Manually';

    // Create the "Place Automatically" button
    const autoPlaceButton = document.createElement('button');
    autoPlaceButton.setAttribute('type', 'button');
    autoPlaceButton.classList.add('btn', 'btn-outline-secondary', 'w-25');
    autoPlaceButton.id = 'autoPlace';
    autoPlaceButton.textContent = 'Place Automatically';

    // Append buttons to the container
    document.getElementById("buttons").append(manuallyButton, autoPlaceButton);
}

function createEmptyGrid(container) {
    for (let i = 0; i < 100; i++) {
        let item = document.createElement("div");
        item.setAttribute("data-index", parseInt(i / 10) + "" + i % 10);
        item.classList.add("cell");
        document.getElementById(container).append(item);
    }
}

function placeAuto() {
    $.ajax({
        url: '/popGrid',
        method: 'GET',
        success: function (response) {
            console.log(response)
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
        });
        colorIndex++;
    });

    // Place computer ships (for visual representation)
    colorIndex = 0;
    response.computer.allShips.forEach(element => {
        element.ship.forEach(element1 => {
            const cell = $('#computer-grid .cell').eq(element1.posX + "" + element1.posy);
            cell.addClass('ship');
            cell.css('background-color', colors[colorIndex]);
        });
        colorIndex++;
    });
}

document.getElementById("computer-grid").addEventListener("click", function (event) {

    if (event.target && event.target.hasAttribute("data-index")) {
        let dataIndex = event.target.getAttribute("data-index");
        attack(dataIndex);
    }
});


function attack(index) {
    const textMess = document.getElementById("descText");
    const notIn = "Campo non inizializzato!";
    const alrHit = "Casella " + parseInt(index / 10) + "" + index % 10 + " già colpita !!";
    //se clicco veloce ricompare alrHit perchè viene modificato originalText
    $.ajax({
        url: "/attack/" + index,
        method: "GET",
        success: function (response) {
            switch (response) {
                case -2:
                    if (!textMess.innerHTML.includes(alrHit)) {
                        textMess.innerHTML += alrHit;
                        setTimeout(function () {
                            textMess.innerHTML = "";
                            textMess.innerHTML = originalText;
                        }, 5000);
                    }
                    break;
                case -1:
                    if (!textMess.innerHTML.includes(notIn)) {
                        textMess.innerHTML += notIn;
                        setTimeout(function () {
                            textMess.innerHTML = "";
                            textMess.innerHTML = originalText;
                        }, 5000);
                    }
                    break;
            }
        },
        error: function (responde) {

        }

    })
}