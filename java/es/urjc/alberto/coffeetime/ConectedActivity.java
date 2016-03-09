package es.urjc.alberto.coffeetime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.net.Socket;

public class ConectedActivity extends AppCompatActivity {

    String server;
    int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resuming(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conected);

        EditText messageBut = (EditText) findViewById(R.id.message);
        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new ListenerSend(this.server, this.port, messageBut));
    }

    public void resuming(Bundle bundle){
        Bundle extras = getIntent().getExtras();
        this.server = extras.getString("server");
        this.port = Integer.parseInt(extras.getString("port"));
    }

}
