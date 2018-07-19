window.onload = function () {
	let layoutController = new LayoutController();

	layoutController.newGameButtonEventHandler();
  layoutController.instructionsButtonEventHandler();
  layoutController.aboutButtonHandler();
}

class LayoutController {

  aboutButtonHandler() {
    let aboutButton = document.getElementById('aboutButton');
    aboutButton.addEventListener('click', this.pressAboutButton);
  }

	newGameButtonEventHandler() {
		let ngButton = document.getElementById('newGameButton');
		ngButton.addEventListener('click', this.pressNewGameButton);
	}

	instructionsButtonEventHandler() {
		let iButton = document.getElementById('instructionsButton');
		iButton.addEventListener('click', this.pressInstructionsButton);
	}

	submitButtonEventHandler() {
		let submitButton = document.getElementById('sendGameRoomDetails');
		submitButton.addEventListener('click', this.pressSubmitButton);
	}

	pressSubmitButton() {
		var xhttp = new XMLHttpRequest();

		var url = "/pong";
		var params = `roomId=${document.getElementById("roomId").value}
                  &nickName=${document.getElementById("nickName").value}`;

		xhttp.open("POST", url, true);
		xhttp.setRequestHeader('User-Input', "input");
		xhttp.send(params);
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
			let statsContainer = document.getElementById("playerDetailsContainer");
			statsContainer.className = "displayInline";


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

	pressInstructionsButton() {		
		let instructionsButton = document.getElementById('instructionsButton');
		let buttonInactive = document.getElementById('instructionsBtn');
		let buttonPressed = document.getElementById('instructionsBtnPressed');
		let mainLogo = document.getElementById('pixelLogo');
    let instructionsDiv = document.getElementById('instructions');
    let smallLogo = document.getElementById('pixelLogoSmall');


		if (instructionsButton.className === "inactive") {
			instructionsButton.className = 'active';
			buttonPressed.className = "displayInline";
			buttonInactive.className = "hide";
			mainLogo.className = "hide";
      instructionsDiv.className = "displayInline fakeConsole";
      smallLogo.className = "hide";

		} else if (instructionsButton.className === "active") {
			instructionsButton.className = 'inactive';
			buttonPressed.className = "hide";
			buttonInactive.className = 'displayInline';
      instructionsDiv.className = "hide fakeConsole";
      smallLogo.className = "displayInline";

		}
  }
  
  pressAboutButton() {		
		let aboutButton = document.getElementById('aboutButton');
		let buttonInactive = document.getElementById('aboutBtn');
		let buttonPressed = document.getElementById('aboutBtnPressed');
    let aboutDiv = document.getElementById('about');
    let mainLogo = document.getElementById('pixelLogo');
    let smallLogo = document.getElementById('pixelLogoSmall');
    let pongTeamLogo = document.getElementById('pongTeamLogo');

		if (aboutButton.className === "inactive") {
			aboutButton.className = 'active';
			buttonPressed.className = "displayInline";
			buttonInactive.className = "hide";
      aboutDiv.className = "displayInline fakeConsole";
      mainLogo.className = "hide";
      smallLogo.className = "hide";
      pongTeamLogo.className = "displayInline";

		} else if (aboutButton.className === "active") {
			aboutButton.className = 'inactive';
			buttonPressed.className = "hide";
			buttonInactive.className = 'displayInline';
      aboutDiv.className = "hide fakeConsole";
      pongTeamLogo.className = "hide";
		}
	}

	startGame() {
		let joinGameroomDiv = document.getElementById('joinGameroom');
		joinGameroomDiv.className = "hide fakeConsole";

		let board = document.getElementById('myCanvas');
		board.className = "displayInline";

	}
}