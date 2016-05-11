package es.urjc.alberto.coffeetime;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
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
    private boolean isRunning = false;

    public class LocalBinder extends Binder {
        MessageService getService() {
            Log.d("piru", "LocalBinder");
            return MessageService.this;
        }

        public void startClient(){
            Log.d("piru", "StartClient!");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final int id = startId;
        final String name = intent.getStringExtra("name");

        if(!this.isRunning){
            this.isRunning = true;
            Log.d("piru", "onStartCommand!");
            (new Thread(new Runnable(){
                public void run(){
                        try {
                            for(;;){
                                AskMessage ask = new AskMessage(name, getApplicationContext());
                                Thread asker = new Thread(ask);
                                asker.start();
                                Thread.sleep(5000);
                            }
                        } catch (InterruptedException e) {

                        }
                    MessageService.this.stopSelf(id);
                }

            })).start();
        }else{
            this.onDestroy();
        }

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("piru", "onBind! Service");
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        Log.d("piru", "onCreate! Service");
        messenger = new Messenger(new IncomingHandler(this));
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("piru", "onDestroy! Service");
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
