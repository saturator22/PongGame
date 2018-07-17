class GameBoard {

  constructor(height, width) {
    this.height = height;
    this.width = width;
  }

  createBoard(height, width) {
    let board = document.createElement('canvas');
    board.width = width;
    board.height = height;
    let context = board.getContext('2d');

    return board;
  }

  renderBoard(board) {
    const parentNode = document.getElementById('board');
    parentNode.appendChild(board);
  }
}