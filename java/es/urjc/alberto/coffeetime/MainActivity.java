package es.urjc.alberto.coffeetime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText address = (EditText) findViewById(R.id.addressNumber);
        EditText portText = (EditText) findViewById(R.id.portNumber);
        Button connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(new ListenerConnect(address, portText, this));
    }

}
