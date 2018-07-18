window.onload = function () {
  let layoutController = new LayoutController();

  layoutController.newGameButtonEventHandler();
}

class LayoutController {

  newGameButtonEventHandler() {
    let ngButton = document.getElementById('newGameButton');
    ngButton.addEventListener('click', this.pressNewGameButton);
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
}