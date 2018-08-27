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
        this.screenContents = [];
        this.screenContents.push(new ScreenContent("intro", true));
        this.screenContents.push(new ScreenContent("joinGame", false));
        this.screenContents.push(new ScreenContent("game", false));
        this.screenContents.push(new ScreenContent("instructions", false));
        this.screenContents.push(new ScreenContent("about", false));

        this.uiElements = [];
        this.uiElements.push(new ButtonModel("newGameButton", "newGameBtnPressed", "newGameBtn"));
        this.uiElements.push(new ButtonModel("instructionsButton", "instructionsBtnPressed", "instructionsBtn"));
        this.uiElements.push(new ButtonModel("aboutButton", "aboutBtnPressed", "aboutBtn"));
    }

    switchScreen(screenName) {
        for (var i = 0; i < this.screenContents.length; i++) {
            let screenContent = this.screenContents[i];

            if (screenContent.sectionId == screenName && screenContent.state == false) {
                let screen = document.getElementById(screenContent.sectionId);
                screen.classList.add("displayInline");
                screen.classList.remove("hide");
                screenContent.state = true;

            } else if (screenContent.state == true && screenContent.sectionId != screenName) {
                let screen = document.getElementById(screenContent.sectionId);
                screen.classList.remove("displayInline");
                screen.classList.add("hide");                
                screenContent.state = false;
                console.log(screen);
            }
        }
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

            } else if (buttonObject.state == true && buttonObject.buttonId != buttonFieldName) {
                let buttonInactive = document.getElementById(buttonObject.inactive);
                let buttonActive = document.getElementById(buttonObject.active);

                buttonInactive.className = "displayInline";
                buttonActive.className = "hide";
                buttonObject.state = false;
            }
        }
    }

    aboutButtonHandler() {
        let scope = this;
        let aboutButton = document.getElementById('aboutButton');
        aboutButton.addEventListener('click', function () { 
            scope.pressButton("aboutButton");
            scope.switchScreen("about");
        });
    }

    newGameButtonEventHandler() {
        let scope = this;
        let ngButton = document.getElementById('newGameButton');
        ngButton.addEventListener('click', function () {
            scope.pressButton("newGameButton");
            scope.switchScreen("joinGame");
        });
    }

    instructionsButtonEventHandler() {
        let scope = this;
        let iButton = document.getElementById('instructionsButton');
        iButton.addEventListener('click', function() {
            scope.pressButton("instructionsButton");
            scope.switchScreen("instructions");
        });
    }

    submitButtonEventHandler() {
        let scope = this;
        let submitButton = document.getElementById('sendGameRoomDetails');
        submitButton.addEventListener('click', this.pressSubmitButton);
    }

    resetButtonEventHandler() {
        let resetButton = document.getElementById('resetGameBtn');
        resetButton.addEventListener('click', this.pressResetButton);
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