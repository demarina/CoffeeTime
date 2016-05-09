package es.urjc.alberto.coffeetime;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MessageService extends Service{

    private Messenger messenger;
    public static final int MSG_SAY_HELLO = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        Log.v("piru", "onCreate! Service");
        messenger = new Messenger(new IncomingHandler(this));
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.v("piru", "onDestroy!");
        super.onDestroy();
    }

    static class IncomingHandler extends Handler {

        private WeakReference<Context> context;

        public IncomingHandler(Context c){
            context = new WeakReference<Context>(c);
        }

        @Override
        public void handleMessage(Message msg) {
            if(context != null){
                switch (msg.what) {
                    case MSG_SAY_HELLO:
                        Toast.makeText(context.get(), "hello!", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        }
    }
}
