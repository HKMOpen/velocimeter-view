package com.github.glomadrian.velocimeter;

import org.java_websocket.client.WebSocketClient;
import java.net.URI;
import java.util.*;
import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.handshake.HandshakeImpl1Client;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.Gson;
import org.w3c.connectedcars.RequestResponseParser;
import org.w3c.connectedcars.Request;
import org.w3c.connectedcars.Response;

public class WebSocketExampleClient extends WebSocketClient {
    private MainActivity main = null; 

    public WebSocketExampleClient( URI serverUri, MainActivity main, Draft draft, Map<String, String> headers, int timeout) {
        super( serverUri, draft, headers, timeout );
        this.main = main;
    }
    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        Log.d("websocket", "open");
    }
    @Override
    public void onMessage( String message ) {
        final String msg = message;
        Log.d("websocket", msg);
        //Handle this message
        Integer speed = parseJson(msg);
        if (null != speed) {
            main.updateSpeed(speed);
        }
    }
    @Override
    public void onClose( int code, String reason, boolean remote ) {
        Log.d("websocket", "closed");
    }
    @Override
    public void onError( Exception ex ) {
        Log.d("websocket", "error");
        ex.printStackTrace();
    }

    // quick and dirty mph parser
    private Integer parseJson(String json) {
        if (null == json || !json.contains("value")) return null;
        Response response = RequestResponseParser.fromJsonToResponse(json);
        
        if (null == response) {
        	return null;
        }
        return Integer.parseInt(response.getSignal().getValue());
    }
}

