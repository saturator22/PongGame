class Racket {
   constructor(yPosition, xPosition, name) {
       this.yPosition = yPosition;
       this.xPosition = xPosition;
       this.racketHeight = 60;
       this.racketWidth = 15;
       this.name = name;
       this.score = 0;
   } 
}

class Ball {
    constructor(xPos, yPos, size) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
    }
}

class GameBoard {
    constructor() {
        this.racket1 = new Racket(240, 5, "Wojtech");
        this.racket2 = new Racket(240, 785, "Bart");
        this.ball = new Ball(400, 240, 10);
    }

    prepareGameBoard() {
        this.clear();
        console.log("prepare gb");
        let c = document.getElementById("myCanvas");
        
        let gameLine = c.getContext("2d");
        gameLine.moveTo(400, 0);
        gameLine.lineTo(400, 480);

        let ballView = c.getContext("2d");
        ballView.fillStyle = "#FFF";
        ballView.fillRect(this.ball.xPos, this.ball.yPos, this.ball.size, this.ball.size);

        let racket1View = c.getContext("2d");
        racket1View.fillStyle = "#FFF";
        racket1View.fillRect(this.racket1.xPosition, this.racket1.yPosition,
            this.racket1.racketWidth, this.racket1.racketHeight);


        let racket2View = c.getContext("2d");
        racket2View.fillStyle = "#FFF";
        racket2View.fillRect(this.racket2.xPosition, this.racket2.yPosition,
                    this.racket2.racketWidth, this.racket2.racketHeight);

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
    console.log("racket render data");
        var xhttp = new XMLHttpRequest();
        let scope = this;

        xhttp.onreadystatechange =
        function() {
            if (this.readyState == 4 && this.status == 200) {
                let parsedJson = JSON.parse(this.responseText);
                scope.updateGameBoard(parsedJson);
            }
        };
        xhttp.open("GET", "/test", true);
        xhttp.send();
        this.prepareGameBoard();
    }

    updateGameBoard(parsedJson) {
        this.updateRackets(parsedJson);
        this.updateBall(parsedJson);
    }

    updateBall(parsedJson) {
        this.ball.xPos = parsedJson.ball.xPos;
        this.ball.yPos = parsedJson.ball.yPos;
    }

    updateRackets(parsedJson) {
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
    console.log("racket on load");
    let gameBoard = new GameBoard;
    let reloadPage = setInterval(gameBoard.renderData.bind(gameBoard), 45);

    gameBoard.prepareGameBoard();

    this.document.addEventListener("keydown", gameBoard.handlePressedKey.bind(gameBoard));
}
