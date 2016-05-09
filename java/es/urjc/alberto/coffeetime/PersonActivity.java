package es.urjc.alberto.coffeetime;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class PersonActivity extends AppCompatActivity {

    String name;
    private Messenger service;
    private ConnecterService connection;
    private Context mContext;

    private class ConnecterService implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder ibinder) {
            Log.v("piru", "onServiceConnected!");
            service = new Messenger(ibinder);
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.v("piru", "onServiceDisconnected!");
            service = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resuming();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        EditText destinationBut = (EditText) findViewById(R.id.destination);
        EditText messageBut = (EditText) findViewById(R.id.message);
        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new ListenerSendPerson(this.name,destinationBut, messageBut, this));

        connection = new ConnecterService();
        mContext = getApplicationContext();
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Intent i = new Intent("es.urjc.alberto.coffeetime.SERVICER");
        Intent i = new Intent(this, MessageService.class);
        if(i != null){
            mContext.bindService(i, connection, Context.BIND_AUTO_CREATE);
            Log.v("piru", "bound!");

        }

        if(service != null){
            Message m = Message.obtain(null, MessageService.MSG_SAY_HELLO, 0, 0);
            try{
                service.send(m);
            }catch(Exception e){

            }
            Log.v("piru", "ButtonOneListener: message sent!");
        }else{
            Log.v("piru", "ButtonOneListener: the service not connected!");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        try{
            if(service != null){
                unbindService(connection);
            }
        }catch (Exception e){

        }
    }

    public void resuming(){
        Bundle extras = getIntent().getExtras();
        this.name = extras.getString("name");
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
            case "View Messages":
                myIntent = new Intent(this.getApplicationContext(), ViewActivity.class);
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
