class GameController {
    initGame() {
        let boardObject = new GameBoard1(480, 800);
        let board = boardObject.createBoard(boardObject.height, boardObject.width);
        boardObject.renderBoard(board);
        let pongGameHandler = new
        initPongGame();
    }
}