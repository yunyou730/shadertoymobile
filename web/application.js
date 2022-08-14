class Application {
    constructor(name) {
        this._name = "app"
        this._client = null;
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
    }






}

