package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ListenerConnect implements View.OnClickListener {

    private EditText name;
    private EditText pass;
    private Activity main;

    public ListenerConnect(EditText name, EditText pass, Activity main){
        this.name = name;
        this.pass = pass;
        this.main = main;
    }

    @Override
    public void onClick(View v){
        String name = this.name.getText().toString();
        String pass = this.pass.getText().toString();
        new Thread(new Loger(name,pass, v, main)).start();
    }

}
