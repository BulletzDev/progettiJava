document.addEventListener("DOMContentLoaded", function () {

    const colors = ["#FE57F3", "#33FF57", "#3357FF", "#F1C40F", "#8E44AD", "#1ABC9C", "#E74C3C", "#3498DB"];

    createEmptyGrid('player-grid');
    createEmptyGrid('computer-grid');
    $.ajax({
        url: '/popola-griglie',
        method: 'GET',
        success: function (response) {
            let colorIndex = 0;
            response.player.allShips.forEach(element => {
                element.ship.forEach(element1 => {
                    const cell = $('#player-grid .cell').eq(element1.posX + "" + element1.posy);
                    cell.addClass('ship');
                    cell.css('background-color', colors[colorIndex]);
                })
                colorIndex++;
            });
            colorIndex = 0;
            response.computer.allShips.forEach(element => {
                element.ship.forEach(element1 => {
                    const cell = $('#computer-grid .cell').eq(element1.posX + "" + element1.posy);
                    cell.addClass('ship');
                    cell.css('background-color', colors[colorIndex]);
                })
                colorIndex++;
            });
        },
        error: function () {
            alert('Errore nel caricamento delle griglie!');
        }
    });
})

function createEmptyGrid(container) {
    for (let i = 0; i < 100; i++) {
        let item = document.createElement("div");
        item.setAttribute("data-index", i % 10 + "" + parseInt(i / 10));
        item.classList.add("cell");
        document.getElementById(container).append(item);
    }
}