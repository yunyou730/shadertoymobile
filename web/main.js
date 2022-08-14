
window.onload = function() {
    console.log("window.onload")

    var app = new Application("ShadertoyMobile Editor")
    // app.Test()

    let btnSubmit = document.getElementById("submit")
    // console.log(btnSubmit)

    btnSubmit.onclick = function(){
        app.OnClickSubmit()
    }


}

