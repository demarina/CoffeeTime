package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.widget.Toast;

public class showToast implements Runnable{

    Activity acti;
    String message;

    public showToast(Activity acti, String message){
        this.acti = acti;
        this.message = message;
    }

    @Override
    public void run(){
        int time = Toast.LENGTH_SHORT;
        Toast msg = Toast.makeText(acti, message, time);
        msg.show();
    }
}
