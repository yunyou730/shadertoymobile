class Application {
    constructor(name) {
        this._name = "app"
        this._client = null;
        this._extImages = [null,null,null]
        // this._fileReader = new FileReader()
        console.log("Application.constructor")
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

    ClearExtImages() {
        this._extImages = [null,null,null]
    }

    RegisterExtImage(pos,file) {
        this._extImages[pos] = file
    }
}
