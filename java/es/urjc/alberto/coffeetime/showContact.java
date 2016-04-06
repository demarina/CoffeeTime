package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class showContact implements Runnable{

    Activity acti;
    List<String> contList;

    public showContact(Activity acti, List<String> contList){
        this.acti = acti;
        this.contList = contList;
    }

    @Override
    public void run(){
        LinearLayout lView = (LinearLayout)acti.findViewById(R.id.contactsList);
        for(String contact : contList){
            TextView myText = new TextView(acti);
            myText.setText(contact);
            lView.addView(myText);
        }

    }
}
