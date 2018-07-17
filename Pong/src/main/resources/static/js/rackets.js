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
        xhttp.onreadystatechange =
        function() {
            if (this.readyState == 4 && this.status == 200) {
                let parsedJson = JSON.parse(this.responseText);
            }
        };
        xhttp.open("GET", "/test", true);
        xhttp.send();

    }

    sendInput(direction) { // POST

        var xhttp = new XMLHttpRequest();
        var url = "/test";
        // var inputElement = document.getElementById("input");
        var params = JSON.stringify({"input" : direction});
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader('User-Input', "input");
        // xhttp.onreadystatechange =
        // function() {
        //     if (this.readyState == 4 && this.status == 200) {
        //         inputElement.value = "";
        //     }
        // };
        xhttp.send(params);
    }

    handlePressedKey(event) {
        let keyCode = event.keyCode;

        if (keyCode === 38 || keyCode === 87) {
            this.racket1.yPosition -= 10;
            this.sendInput("up");
            this.prepareGameBoard();
        } else if (keyCode === 40 || keyCode === 83){
            this.racket1.yPosition += 10;
            this.sendInput("down");
            this.prepareGameBoard();
        }
    }
}

window.onload = function() {
    let gameBoard = new GameBoard;
    let reloadPage = setInterval(gameBoard.renderData(), 40);

    gameBoard.prepareGameBoard();
    
    this.document.addEventListener("keydown", gameBoard.handlePressedKey.bind(gameBoard));
}