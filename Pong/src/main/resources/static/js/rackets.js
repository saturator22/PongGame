class Racket {
   constructor(yPostion, name) {
       this.yPostion = yPostion;
       this.racketLength = 60;
       this.name = name;
       this.score = 0;
   } 
}

class GameBoard {
    constructor() {
        this.racket1 = new Racket(240, "Wojtech");
        this.racket2 = new Racket(240, "Bart");
    }

    prepareGameBoard() {
        this.clear();

        let c = document.getElementById("myCanvas");
        
        let gameLine = c.getContext("2d");
        gameLine.moveTo(400, 0);
        gameLine.lineTo(400, 480);

        let racket1View = c.getContext("2d");
        racket1View.moveTo(5, this.racket1.yPostion);
        racket1View.lineTo(5, this.racket1.yPostion - this.racket1.racketLength);

        let racket2View = c.getContext("2d");
        racket2View.moveTo(795, this.racket2.yPostion);
        racket2View.lineTo(795, this.racket2.yPostion - this.racket2.racketLength);

        gameLine.stroke();
        racket1View.stroke();
        racket2View.stroke();
    }

    clear() {
        let c = document.getElementById("myCanvas");
        let ctx = c.getContext("2d");
        
        ctx.clearRect(0, 0, c.width, c.height);
        ctx.beginPath();
    }

    handlePressedKey(event) {
        let keyCode = event.keyCode;

        if (keyCode === 38 || keyCode === 87) {
            this.racket1.yPostion -= 10;
            this.prepareGameBoard();
        } else if (keyCode === 40 || keyCode === 83){
            this.racket1.yPostion += 10;
            this.prepareGameBoard();
        }
    }
}

window.onload = function() {
    let gameBoard = new GameBoard;
    gameBoard.prepareGameBoard();
    
    this.document.addEventListener("keydown", gameBoard.handlePressedKey.bind(gameBoard));
}