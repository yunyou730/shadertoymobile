package com.example.bibabo.server;

import android.util.Log;

import com.example.bibabo.WizardApp;
import com.example.bibabo.event.EventDispatcher;
import com.example.bibabo.utils.ImageFileConvert;
import com.example.bibabo.utils.ImageRawData;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class WizardServer extends WebSocketServer {

    public WizardServer(InetSocketAddress host) {
        super(host);
        setReuseAddr(true);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Log.d("ayy","WizardServer.onOpen");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Log.d("ayy","WizardServer.onClose");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Log.d("ayy","WizardServer.onMessage:" + message);
        try {
            JSONObject jobj = new JSONObject(message);
            String vertCode = jobj.getString("vs");
            String fragCode = jobj.getString("fs");
            EventDispatcher.ShaderCodeChangeEvent evt = new EventDispatcher.ShaderCodeChangeEvent(
                    vertCode,fragCode
            );
            WizardApp.getInstance().getEventDispatcher().DispatchEvent(EventDispatcher.EventType.ReceiveShaderCode,evt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage( WebSocket conn, ByteBuffer message ) {
        message.position(0);
        byte[] data = message.array();
        ImageRawData rawData = new ImageFileConvert().getImageRawData(data);
        Log.d("ayy","123");
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Log.d("ayy","WizardServer.onError:" + ex.toString());
        WizardApp.getInstance().showPopupMessage(ex.toString());
    }

    @Override
    public void onStart() {
        Log.d("ayy","WizardServer.onStart");
    }

    public String byteBufferToString(ByteBuffer buffer)
    {
        CharBuffer charBuffer = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public void sendStr(String content)
    {
        this.broadcast(content);
    }

}
