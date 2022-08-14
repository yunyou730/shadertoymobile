
window.onload = function() {
    console.log("window.onload")

    var app = new Application("ShadertoyMobile Editor")
    // app.Test()

    let btnSubmit = document.getElementById("submit")
    let btnConn = document.getElementById("connect")
    let txtIP = document.getElementById("mobile_ip")
    let txtPort = document.getElementById("mobile_port")

    btnSubmit.onclick = function() {
        app.OnClickSubmit()
    }

    btnConn.onclick = function() {
        app.OnClickConnect(txtIP.value,txtPort.value)
    }
}

