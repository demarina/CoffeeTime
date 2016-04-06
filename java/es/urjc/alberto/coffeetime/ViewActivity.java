package es.urjc.alberto.coffeetime;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ViewActivity extends AppCompatActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resuming();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showMessages();
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
