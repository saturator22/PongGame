var intervalId;

function loadPage() {
    var text = document.createElement("h1");
    text.id = "text";
    text.innerHTML = "loading";

    var input = document.createElement("input");
    input.id = "input";

    var button = document.createElement("button");
    button.onclick =
        function(e) {
            sendInput();
        }
    button.id = "button";
    document.body.appendChild(text);
    document.body.appendChild(input);
    document.body.appendChild(button);

//    intervalId = setInterval(reloadData, 30);
}

function sendInput() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange =
    function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("text").innerHTML = this.responseText;
        }
    };
    xhttp.open("POST", "/PongGame/static/testFiles/test.json", true);
    console.log(xhttp);
    xhttp.send();
}

function reloadData() {
    var xhttp = new XMLHttpRequest();
    var url = "/pong";
    var params = "{\"input\": \"" + document.getElementById("input").value + "\"}";
    xhttp.open("GET", url, true);
    xhttp.setRequestHeader('User-Input', params);
    xhttp.onreadystatechange =
    function() {
        if (this.readyState == 4 && this.status == 200) {
            alert(this.responseText);
        }
    };
    xhttp.send(params);
}