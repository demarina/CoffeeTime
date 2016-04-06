package es.urjc.alberto.coffeetime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class GroupActivity extends AppCompatActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resuming();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        EditText destinationBut = (EditText) findViewById(R.id.destinationGroup);
        EditText messageBut = (EditText) findViewById(R.id.messageGroup);
        Button send = (Button) findViewById(R.id.sendGroup);
        send.setOnClickListener(new ListenerSendGroup(this.name,destinationBut, messageBut, this));
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
            case "Send Person":
                myIntent = new Intent(this.getApplicationContext(), PersonActivity.class);
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