class Racket {
   constructor(yPosition, name) {
       this.yPosition = yPosition;
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
        racket1View.moveTo(5, this.racket1.yPosition);
        racket1View.lineTo(5, this.racket1.yPosition - this.racket1.racketLength);

        let racket2View = c.getContext("2d");
        racket2View.moveTo(795, this.racket2.yPosition);
        racket2View.lineTo(795, this.racket2.yPosition - this.racket2.racketLength);

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

    renderData() {
        var xhttp = new XMLHttpRequest();
        let scope = this;

        xhttp.onreadystatechange =
        function() {
            if (this.readyState == 4 && this.status == 200) {
                let parsedJson = JSON.parse(this.responseText);
                console.log(parsedJson.firstPlayer.racketYPos);
                scope.updateGameBoard(parsedJson);
            }
        };
        xhttp.open("GET", "/test", true);
        xhttp.send();
        // debugger;
        this.prepareGameBoard();
        // console.log(this);
    }

    updateGameBoard(parsedJson) {
        this.updateRackets(parsedJson);
    }

    // updateBall(parsedJson) {
    //     this. = parsedJson.
    // }

    updateRackets(parsedJson) {
        console.log(this.racket1)
        this.racket1.yPosition = parsedJson.firstPlayer.racketYPos;
        this.racket2.yPosition = parsedJson.secondPlayer.racketYPos;
    }

    sendInput(direction) { // POST

        var xhttp = new XMLHttpRequest();
        var url = "/test";
        var params = JSON.stringify({"input" : direction});
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader('User-Input', "input");

        xhttp.send(params);
    }

    handlePressedKey(event) {
        let keyCode = event.keyCode;

        if (keyCode === 38 || keyCode === 87) {
            this.sendInput("up");
        } else if (keyCode === 40 || keyCode === 83){
            this.sendInput("down");
        }
    }
}

window.onload = function() {
    let gameBoard = new GameBoard;
    let reloadPage = setInterval(gameBoard.renderData.bind(gameBoard), 40);

    gameBoard.prepareGameBoard();
    
    this.document.addEventListener("keydown", gameBoard.handlePressedKey.bind(gameBoard));
}