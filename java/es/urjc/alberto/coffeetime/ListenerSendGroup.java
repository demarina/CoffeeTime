package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class ListenerSendGroup implements View.OnClickListener {

    private String name;
    private EditText desti;
    private EditText msg;
    private Activity acti;

    public ListenerSendGroup(String name, EditText desti, EditText msg, Activity acti){
        this.name = name;
        this.desti = desti;
        this.msg = msg;
        this.acti = acti;
    }

    @Override
    public void onClick(View v){
        String message = msg.getText().toString();
        String group = desti.getText().toString();
        new Thread(new WriterGroup(this.name, group, message, v, acti)).start();
        desti.setText("");
        msg.setText("");
    }

}