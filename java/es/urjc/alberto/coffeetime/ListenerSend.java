package es.urjc.alberto.coffeetime;

import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class ListenerSend implements View.OnClickListener {

    private String server;
    private int port;
    private EditText msg;

    public ListenerSend(String server, int port, EditText msg){
        this.server = server;
        this.port = port;
        this.msg = msg;
    }

    @Override
    public void onClick(View v){
        String message = msg.getText().toString();
        new Thread(new Writer(server,port, message)).start();
    }

}