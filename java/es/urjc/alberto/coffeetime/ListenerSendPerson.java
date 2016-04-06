package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class ListenerSendPerson implements View.OnClickListener {

    private String name;
    private EditText desti;
    private EditText msg;
    private Activity acti;

    public ListenerSendPerson(String name, EditText desti, EditText msg, Activity acti){
        this.name = name;
        this.desti = desti;
        this.msg = msg;
        this.acti = acti;
    }

    @Override
    public void onClick(View v){
        String message = msg.getText().toString();
        String dest = desti.getText().toString();
        new Thread(new WriterPerson(this.name, dest, message, v, acti)).start();
        desti.setText("");
        msg.setText("");
    }

}