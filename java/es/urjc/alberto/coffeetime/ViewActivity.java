package es.urjc.alberto.coffeetime;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ViewActivity extends AppCompatActivity {

    String name;

    private boolean bounded = false;
    private MessageService boundService;
    Activity act = this;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder service) {
            MessageService.LocalBinder binder = (MessageService.LocalBinder) service;
            boundService = binder.getService();
            boundService.setPrint(act);
            bounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bounded = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resuming();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AskMessage.cancelNotification(this.getApplicationContext(), 1);
        showMessages();

        Intent intent = new Intent(this, MessageService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop(){
        super.onStop();
        try{
            if(bounded){
                boundService.unsetPrint();
                unbindService(mServiceConnection);
                bounded = false;
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try{
            if(bounded){
                boundService.unsetPrint();
                unbindService(mServiceConnection);
                bounded = false;
            }
        }catch (Exception e){

        }
    }

    public void resuming(){
        Bundle extras = getIntent().getExtras();
        this.name = extras.getString("name");
    }

    public void showMessages(){
        List<String> list = SchedulerFile.readMessages(getApplicationContext(), name);
        if(list != null){
            String text;
            String name;
            for(String item : list){
                String[] data = item.split("%");
                text = data[0];
                name = data[1];
                LinearLayout lView = (LinearLayout)findViewById(R.id.messageList);

                TextView myText = new TextView(this);

                myText.setText(name + ": " + text);

                lView.addView(myText);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String type = (String)item.getTitle();
        Intent myIntent;

        switch(type){
            case "Send Person":
                myIntent = new Intent(this.getApplicationContext(), PersonActivity.class);
                myIntent.putExtra("name", name);
                this.startActivity(myIntent);
                return true;
            case "Send Group":
                myIntent = new Intent(this.getApplicationContext(), GroupActivity.class);
                myIntent.putExtra("name", name);
                this.startActivity(myIntent);
                return true;
            case "Contacts":
                myIntent = new Intent(this.getApplicationContext(), ContactsActivity.class);
                myIntent.putExtra("name", name);
                this.startActivity(myIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
