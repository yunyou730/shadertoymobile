
let g_debug = true
var g_app = null
let g_debug_ip = "10.78.53.153"
let g_debug_port = "7324"

window.onload = function() {
    console.log("window.onload")

    let btnSubmit = document.getElementById("submit")
    let btnConn = document.getElementById("connect")
    let btnShaderCode = document.getElementById("submit_shader_code")
    //let btnImage = document.getElementById("submit_images")

    var txtIP = document.getElementById("mobile_ip")
    var txtPort = document.getElementById("mobile_port")

    var txtVertShader = document.getElementById("vert_shader")
    var txtFragShader = document.getElementById("frag_shader")

    var connect_status_t_el = document.getElementById("connect_status_t")
    var connect_status_f_el = document.getElementById("connect_status_f")
    connect_status_t_el.style.display = "none"
    let txtRemoteMsg = document.getElementById("remote_messsage")


    g_app = new Application("ShadertoyMobile Editor")
    g_app.RegisterWidgets(connect_status_t_el,connect_status_f_el,txtVertShader,txtFragShader,txtRemoteMsg)

    if(g_debug) {
        txtIP.value = g_debug_ip
        txtPort.value = g_debug_port
    }

    btnSubmit.onclick = function() {
        g_app.OnClickSubmit()
    }

    btnConn.onclick = function() {
        g_app.OnClickConnect(txtIP.value,txtPort.value)
    }


    btnShaderCode.onclick = function() {
        g_app.OnClickSubmitShaderCode()
    }

    // btnImage.onclick = function() {
    //     g_app.OnClickSubmitImages()
    // }

    // g_app.FillDefaultVS()
    // g_app.FillDefaultFS()
}

// window.addEventListener("load",function() {

//     let inputImg1 = document.getElementById("img_1")
//     inputImg1.addEventListener("change",function(){
//         if(this.files && this.files[0])
//         {
//             console.log(this.files[0])
//             g_app.RegisterExtImage(0,this.files[0])
//         }
//     })

//     let inputImg2 = document.getElementById("img_2")
//     inputImg2.addEventListener("change",function(){
//         if(this.files && this.files[0])
//         {
//             console.log(this.files[0])
//             g_app.RegisterExtImage(1,this.files[0])            
//         }
//     })
    
//     let inputImg3 = document.getElementById("img_3")
//     inputImg3.addEventListener("change",function(){
//         if(this.files && this.files[0])
//         {
//             console.log(this.files[0])
//             g_app.RegisterExtImage(2,this.files[0])            
//         }
//     })
// })

