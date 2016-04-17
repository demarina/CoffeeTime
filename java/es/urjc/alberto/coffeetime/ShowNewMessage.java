package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowNewMessage implements Runnable{

    Activity acti;
    String message;

    public ShowNewMessage(Activity acti, String message){
        this.acti = acti;
        this.message = message;
        Log.d("piru", "Contrust");
    }

    @Override
    public void run(){
        Log.d("piru", "RUN");
        String splited[] = message.split("%");
        String messageShow = splited[1] + ": " + splited[0];
        LinearLayout lView = (LinearLayout)acti.findViewById(R.id.messageList);
        TextView myText = new TextView(acti);
        myText.setText(messageShow);
        lView.addView(myText);
    }
}