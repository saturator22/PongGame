class GameController {
  initGame() {
    let boardObject = new GameBoard(480, 800);
    let board = boardObject.createBoard(boardObject.height, boardObject.width);
    boardObject.renderBoard(board);
  }
}