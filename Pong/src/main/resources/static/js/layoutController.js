window.onload = function () {
  let layoutController = new LayoutController();

  layoutController.newGameButtonEventHandler();
  layoutController.submitButtonEventHandler();
}

class LayoutController {

  newGameButtonEventHandler() {
    let ngButton = document.getElementById('newGameButton');
    ngButton.addEventListener('click', this.pressNewGameButton);
  }

  submitButtonEventHandler() {
    let submitButton = document.getElementById('sendGameRoomDetails');
    submitButton.addEventListener('click', this.pressSubmitButton.bind(this));
  }

  pressSubmitButton() {
    try {
        var xhttp = new XMLHttpRequest();
        var scope = this;
        xhttp.onreadystatechange =
            function() {
                if (this.readyState == 4) {
                    scope.startGame();
                }
            };

        var url = "/pong";
        var params = `roomId=${document.getElementById("roomId").value}&nickName=${document.getElementById("nickName").value}`
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader('User-Input', "input");
        xhttp.send(params);
    } catch (err) {
    }
  }

  pressNewGameButton() {
    let newGameButton = document.getElementById('newGameButton');
    let buttonInactive = document.getElementById('newGameBtn');
    let buttonPressed = document.getElementById('newGameBtnPressed');

    if (newGameButton.className === "inactive") {
      newGameButton.className = 'active';
      buttonPressed.className = "displayInline";
      buttonInactive.className = "hide";
      let logo = document.getElementById('pixelLogo');
      logo.className = "hide";
      let connectToRoomDiv = document.getElementById('joinGameroom');
      connectToRoomDiv.className = "displayInline fakeConsole";

    } else if (newGameButton.className === "active") {
      newGameButton.className = 'inactive';
      buttonPressed.className = "hide";
      buttonInactive.className = 'displayInline';
      let connectToRoomDiv = document.getElementById('joinGameroom');
      connectToRoomDiv.className = "hide fakeConsole";
      let logo = document.getElementById('pixelLogo');
      logo.className = "displayInline";
    }
  }

  startGame() {
    let joinGameroomDiv = document.getElementById('joinGameroom');
    joinGameroomDiv.className = "hide fakeConsole";

    let board = document.getElementById('myCanvas');
    board.className = "displayInline";
  }
}