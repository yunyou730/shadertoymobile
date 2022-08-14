
let g_debug = true
var g_app = null
let g_debug_ip = "192.168.1.6"
let g_debug_port = "7324"

window.onload = function() {
    console.log("window.onload")


    g_app = new Application("ShadertoyMobile Editor")

    let btnSubmit = document.getElementById("submit")
    let btnConn = document.getElementById("connect")
    let txtIP = document.getElementById("mobile_ip")
    let txtPort = document.getElementById("mobile_port")

    if(g_debug) {
        txtIP.value     = g_debug_ip
        txtPort.value   = g_debug_port
    }

    btnSubmit.onclick = function() {
        g_app.OnClickSubmit()
    }

    btnConn.onclick = function() {
        g_app.OnClickConnect(txtIP.value,txtPort.value)
    }
}

window.addEventListener("load",function() {

    let inputImg1 = document.getElementById("img_1")
    inputImg1.addEventListener("change",function(){
        if(this.files && this.files[0])
        {
            console.log(this.files[0])
            g_app.RegisterExtImage(0,this.files[0])
        }
    })

    let inputImg2 = document.getElementById("img_2")
    inputImg2.addEventListener("change",function(){
        if(this.files && this.files[0])
        {
            console.log(this.files[0])
            g_app.RegisterExtImage(1,this.files[0])            
        }
    })
    
    let inputImg3 = document.getElementById("img_3")
    inputImg3.addEventListener("change",function(){
        if(this.files && this.files[0])
        {
            console.log(this.files[0])
            g_app.RegisterExtImage(2,this.files[0])            
        }
    })
})

