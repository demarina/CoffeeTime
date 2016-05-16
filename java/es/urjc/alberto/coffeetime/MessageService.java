package es.urjc.alberto.coffeetime;


import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
public class MessageService extends Service{

    private boolean isRunning = false;
    private final IBinder mBinder = new LocalBinder();
    private boolean bounded = false;
    private Activity act;

    public class LocalBinder extends Binder {
        MessageService getService() {
            return MessageService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final int id = startId;
        final String name = intent.getStringExtra("name");

        if(!this.isRunning){
            this.isRunning = true;
            (new Thread(new Runnable(){
                public void run(){
                        try {
                            for(;;){
                                AskMessage ask;
                                if(bounded){
                                    ask = new AskMessage(name, getApplicationContext(), act, true);
                                }else{
                                    ask = new AskMessage(name, getApplicationContext(), false);
                                }
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
        return mBinder;
    }

    public void setPrint(Activity act){
        this.act = act;
        this.bounded = true;
    }

    public void unsetPrint(){
        this.bounded = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
