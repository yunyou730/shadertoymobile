package com.example.bibabo.server;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class WizardServer extends WebSocketServer {

    public WizardServer(InetSocketAddress host) {
        super(host);
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
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Log.d("ayy","WizardServer.onError:" + ex.toString());
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

}
