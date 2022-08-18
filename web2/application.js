class Application {
    constructor(name) {
        console.log("Application.constructor")

        this._name = "app"
        this._client = null;
        this._extImages = [null,null,null]

        this._vertInput = null
        this._fragInput = null

        this._remoteMsgLabel = null
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

    OnClickSubmitShaderCode() 
    {

        let contentJson = {}
        
        contentJson.vs = this._vertInput.value
        contentJson.fs = this._fragInput.value
        // contentJson.vs =  layui.layedit.getContent(global_vs_el)
        // contentJson.fs =  layui.layedit.getContent(global_fg_el)
        let contentStr = JSON.stringify(contentJson)
        this._client.SendText(contentStr)
        console.log(contentJson.vs)
        console.log(contentJson.fs)
        console.log("合并成功")
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
    //赋值vert和frag的标签对象；
    RegisterWidgets(connect_status_t_el,connect_status_f_el,vertInput,fragInput,remoteLabel) {
        this._connect_status_t_el = connect_status_t_el
        this._connect_status_f_el = connect_status_f_el
        this._vertInput = vertInput
        this._fragInput = fragInput
        this._remoteMsgLabel = remoteLabel
    }
    RefreshConnectStateLabel()
    {
        var bConnecting = false
        
        if(this._client != null && this._client.IsConnecting())
        {
            bConnecting = true
            this._connect_status_t_el.style.display = ""
            this._connect_status_f_el.style.display = "none"
        }

    }
    getShaderCode()
    {

        return "";
    }
    OnRemoteMessage(msgObject)
    {
        console.log(msgObject)
        
        this._remoteMsgLabel.value = JSON.stringify(msgObject)
    }
}
