package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.content.Intent;

public class Go2WriterActivity implements Runnable{

    Activity activ;
    String name;

    public Go2WriterActivity(Activity activ, String name){
        this.activ = activ;
        this.name = name;
    }

    @Override
    public void run(){
        Intent myIntent = new Intent(activ.getApplicationContext(), PersonActivity.class);
        myIntent.putExtra("name", name);
        activ.startActivity(myIntent);
    }

}