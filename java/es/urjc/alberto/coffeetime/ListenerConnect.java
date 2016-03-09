package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ListenerConnect implements View.OnClickListener {

    private EditText addressServer;
    private EditText port;
    private Activity main;

    public ListenerConnect(EditText address, EditText port, Activity main){
        this.addressServer = address;
        this.port = port;
        this.main = main;
    }

    @Override
    public void onClick(View v){
        String server = this.addressServer.getText().toString();
        String portNumber = this.port.getText().toString();
        Intent myIntent = new Intent(main.getApplicationContext(), ConectedActivity.class);
        myIntent.putExtra("server", server);
        myIntent.putExtra("port", portNumber);
        main.startActivity(myIntent);
    }

}
