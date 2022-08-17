class Application {
    constructor(name) {
        console.log("Application.constructor")

        this._name = "app"
        this._client = null;
        this._extImages = [null,null,null]

        this._vertInput = null
        this._fragInput = null
        this._connectLabel = null
        this._remoteMsgLabel = null
        // this._fileReader = new FileReader()


        this._defaultVS = 
        "attribute vec4 vPosition;\n" +
        "attribute vec2 aUV;\n" +
        "varying vec2 vUV;\n" +
        "void main() {\n" +
        "   vUV = aUV;\n" +
        "   gl_Position = vPosition;\n" +
        "}";


        this._defaultFS = 
        "#extension GL_OES_EGL_image_external : require\n" + // declare OEM texture
        "precision mediump float;\n" +
        "varying vec2 vUV;\n" +
        "uniform samplerExternalOES s_texture;\n" +
        "void main() {\n" +
        "   vec4 texColor = texture2D(s_texture,vUV);\n" +
        "   gl_FragColor = texColor * vec4(vUV.x,vUV.y,0.0,1.0);\n" +
        "}";
    }

    OnClickConnect(ip,port)
    {
        console.log("onClickConnect")
        if(this._client != null)
        {
            this._client.Disconnect()
            this._client = null
        }
        this._client = new SocketClient(ip,port)
        this._client.Connect()
    }

    OnClickDisconnect()
    {
        if(this._client != null)
        {
            this._client.Disconnect()
            this._client = null
        }
    }
    
    OnClickSubmit()
    {
        console.log("OnClickSubmit")
        var thiz = this
        
        if(this._extImages[0] != null) {
           let file = this._extImages[0] 
           const reader = new FileReader()
            reader.onload = function(event)
            {
                console.log(event.target.result)
                if(thiz._client != null)
                {
                    thiz._client.SendArrayBuffer(event.target.result)
                }
            }
            reader.readAsArrayBuffer(file)            
        }
    }

    OnClickSubmitShaderCode() 
    {
        let contentJson = {}
        contentJson.vs = this._vertInput.value
        contentJson.fs = this._fragInput.value        
        let contentStr = JSON.stringify(contentJson)
        this._client.SendText(contentStr)
    }

    FillDefaultVS()
    {
        this._vertInput.value = this._defaultVS
    }

    FillDefaultFS()
    {
        this._fragInput.value = this._defaultFS
    }

    OnClickSubmitImages()
    {
        console.log("submit images")
    }

    ClearExtImages() 
    {
        this._extImages = [null,null,null]
    }


    RegisterExtImage(pos,file) 
    {
        this._extImages[pos] = file
    }

    RegisterWidgets(vertInput,fragInput,connectLabel,remoteLabel) {
        this._vertInput = vertInput
        this._fragInput = fragInput
        this._connectLabel = connectLabel
        this._remoteMsgLabel = remoteLabel
    }

    getShaderCode()
    {

        return "";
    }

    RefreshConnectStateLabel()
    {
        var bConnecting = false
        if(this._client != null && this._client.IsConnecting())
        {
            bConnecting = true
        }

        var connectStr = bConnecting ? "Connected" : "Disconnected"
        this._connectLabel.innerHTML = connectStr
        this._connectLabel.style.backgroundColor = bConnecting ? "#00ff00" : "#ff0000"
    }

    OnRemoteMessage(msgObject)
    {
        console.log(msgObject)
        this._remoteMsgLabel.innerHTML = msgObject
    }
}
