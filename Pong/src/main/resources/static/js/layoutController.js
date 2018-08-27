window.onload = function () {
    let layoutController = new LayoutController();
    
	layoutController.newGameButtonEventHandler();
    layoutController.instructionsButtonEventHandler();
    layoutController.aboutButtonHandler();
    layoutController.submitButtonEventHandler();
    layoutController.resetButtonEventHandler();
    layoutController.initSidebar();
}

class LayoutController {

    constructor() {
        this.uiElements = [];
        this.uiElements.push(new ButtonModel("newGameButton", "newGameBtnPressed", "newGameBtn"));
        this.uiElements.push(new ButtonModel("instructionsButton", "instructionsBtnPressed", "instructionsBtn"));
        this.uiElements.push(new ButtonModel("aboutButton", "aboutBtnPressed", "aboutBtn"));
    }

    pressButton(buttonFieldName) {
        for (var i = 0; i < this.uiElements.length; i++) {
            let buttonObject = this.uiElements[i];

            if (buttonObject.buttonId == buttonFieldName && buttonObject.state == false) {
                let buttonInactive = document.getElementById(buttonObject.inactive);
                let buttonActive = document.getElementById(buttonObject.active);

                buttonInactive.className = "hide";
                buttonActive.className = "displayInline";
                buttonObject.state = true;

            } else if (buttonObject.state == true) {
                let buttonInactive = document.getElementById(buttonObject.inactive);
                let buttonActive = document.getElementById(buttonObject.active);

                buttonInactive.className = "displayInline";
                buttonActive.className = "hide";
                buttonObject.state = false;
            }
        }
    }

    aboutButtonHandler() {
        let aboutButton = document.getElementById('aboutButton');
        aboutButton.addEventListener('click', this.pressButton("aboutButton"));
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

    resetButtonEventHandler() {
        let resetButton = document.getElementById('resetGameBtn');
        resetButton.addEventListener('click', this.pressResetButton);
    }

    pressNewGameButton() {
        let newGameButton = document.getElementById('newGameButton');
        let buttonInactive = document.getElementById('newGameBtn');
        let buttonPressed = document.getElementById('newGameBtnPressed');

        if (newGameButton.className === "inactive") {
            newGameButton.className = 'active';
            buttonPressed.className = "displayInline";
            buttonInactive.className = "hide";
            let connectToRoomDiv = document.getElementById('joinGame');
            connectToRoomDiv.className = "screenContent";
            let startScreen = document.getElementById("intro");
            startScreen.className = "screenContent hide";

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

        // if (aboutButton.className === "inactive") {
        //     aboutButton.className = 'active';
        //     buttonPressed.className = "displayInline";
        //     buttonInactive.className = "hide";
        //     aboutDiv.className = "displayInline fakeConsole";
        //     mainLogo.className = "hide";
        //     smallLogo.className = "hide";
        //     pongTeamLogo.className = "displayInline";

        // } else if (aboutButton.className === "active") {
        //     aboutButton.className = 'inactive';
        //     buttonPressed.className = "hide";
        //     buttonInactive.className = 'displayInline';
        //     aboutDiv.className = "hide fakeConsole";
        //     pongTeamLogo.className = "hide";
        // }
    }

    pressResetButton() {
    console.log("reset button");
        var xhttp = new XMLHttpRequest();
        var url = "/reset";
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader('ResetRoom', "cookie");
        xhttp.send();
    }

    pressSubmitButton() {
        var xhttp = new XMLHttpRequest();
        var scope = this;
        xhttp.onreadystatechange =
            function() {
                if (this.readyState == 4) {
                    let joinGameroomDiv = document.getElementById('joinGame');
                    joinGameroomDiv.className = "screenContent hide";

                    let board = document.getElementById('game');
                    board.className = "screenContent";
                }
            };

        var url = "/pong";
        var params = `roomId=${document.getElementById("roomId").value}&nickName=${document.getElementById("nickName").value}`
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader('User-Input', "input");
        xhttp.send(params);
    }

	startGame() {
		let joinGameroomDiv = document.getElementById('joinGameroom');
		joinGameroomDiv.className = "hide fakeConsole";

		let board = document.getElementById('myCanvas');
		board.className = "displayInline";
    }
    
    initSidebar() {
        var sidebarBox = document.querySelector('#box');
        var sidebarBtn = document.querySelector('#btn');
        var pageWrapper = document.querySelector('#boardContainer');

        sidebarBtn.addEventListener('click', function (event) {

            if (this.classList.contains('active')) {
                this.classList.remove('active');
                sidebarBox.classList.remove('active');
            } else {
                this.classList.add('active');
                sidebarBox.classList.add('active');
            }
        });

        pageWrapper.addEventListener('click', function (event) {

            if (sidebarBox.classList.contains('active')) {
                sidebarBtn.classList.remove('active');
                sidebarBox.classList.remove('active');
            }
        });

        window.addEventListener('keydown', function (event) {

            if (sidebarBox.classList.contains('active') && event.keyCode === 27) {
                sidebarBtn.classList.remove('active');
                sidebarBox.classList.remove('active');
            }
        });
    }
}