class SocketClient
{
    constructor(remoteIP,remotePort)
    {
        this._remoteIP = remoteIP;
        this._remotePort = remotePort;
        this._ws = null;
    }

    Connect()
    {
        let remote = "ws://" + this._remoteIP + ":" + this._remotePort;

        try {
            this._ws = new WebSocket(remote)
        
            this._ws.onopen = function() {
                console.log("onopen")
                g_app.RefreshConnectStateLabel()
            }
    
            this._ws.onmessage = function(e) {
                console.log(e.data)
                g_app.OnRemoteMessage(e.data)
            }
    
            this._ws.onclose = function(e) {
                console.log("close");
                this._ws = null;
                g_app.RefreshConnectStateLabel()                
            }
    
            this._ws.onerror = function(e) {
                console.log(e)
                g_app.RefreshConnectStateLabel()
            }

        } catch (error) {
            console.log("Create WS error:" + error)
            this._ws = null;
        }
    }

    Disconnect()
    {
        if(this._ws != null)
        {
            this._ws.close()
            this._ws = null;
        }
    }

    IsConnecting()
    {
        if(this._ws == null) 
        {
            return false;
        }

        /*
            0 - not estamplish connection
            1 - connection ok , can interact
            2 - closing
            3 - closed or can not open
        */
        return this._ws.readyState == 1;
    }

    SendText(text) {
        if(!this.IsConnecting())
        {
            return;
        }
        this._ws.send(text)
    }

    SendArrayBuffer(arraybuffer) {
        if(!this.IsConnecting())
        {
            return;
        }
        this._ws.send(arraybuffer)
    }
}
