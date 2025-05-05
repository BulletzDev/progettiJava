$(document).ready(function () {
    function createEmptyGrid(container) {
        for (let i = 0; i < 100; i++) {
            $(container).append('<div class="cell" data-index="' + i + '"></div>');
        }
    }

    createEmptyGrid('#player-grid');
    createEmptyGrid('#computer-grid');

    // Chiamata AJAX per ottenere le posizioni casuali
    $.ajax({
        url: '/popola-griglie',
        method: 'GET',
        success: function (response) {
            // response = { player: [1, 23, 45], computer: [10, 20, 30] } 
            response.player.forEach(index => {
                $('#player-grid .cell').eq(index).addClass('ship');
            });
            response.computer.forEach(index => {
                $('#computer-grid .cell').eq(index).addClass('ship');
            });
        },
        error: function () {
            alert('Errore nel caricamento delle griglie!');
        }
    });
});