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
    button.innerHTML = "Submit";
    document.body.appendChild(text);
    document.body.appendChild(input);
    document.body.appendChild(button);

    intervalId = setInterval(reloadData, 1200);
}

function reloadData() { // GET
    console.log("Reload data");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange =
        function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("text").innerHTML = this.responseText;
            }
        };
    xhttp.open("GET", "/test", true);
    xhttp.send();
}

function sendInput() { // POST
    var xhttp = new XMLHttpRequest();
    var url = "/test";
    var inputElement = document.getElementById("input");
    var params = JSON.stringify({"input" : inputElement.value});
    console.log("Send input as JSON: " + params);
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader('User-Input', params);
    xhttp.onreadystatechange =
    function() {
        if (this.readyState == 4 && this.status == 200) {
            inputElement.value = "";
        }
    };
    xhttp.send(params);
}