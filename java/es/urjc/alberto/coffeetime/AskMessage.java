package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class AskMessage implements Runnable{

    private String name;
    private Context ctx;
    private boolean bound;
    private Activity act;

    public AskMessage(String name, Context ctx, boolean bound){
        this.name = name;
        this.ctx = ctx;
        this.bound = bound;
    }

    public AskMessage(String name, Context ctx, Activity act, boolean bound){
        this.name = name;
        this.ctx = ctx;
        this.bound = bound;
        this.act = act;
    }

    @Override
    public void run() {
        getMessages2Server();
    }

    private void showNotification(String text){
        NotificationManager mNotificationManager = (NotificationManager) ctx
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                ctx).setSmallIcon(R.drawable.coffee)
                .setContentTitle("Coffee Time")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setContentText(text);
        Intent intent = new Intent(ctx, ViewActivity.class);
        intent.putExtra("name", name);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(ViewActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent contentIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(1, mBuilder.build());
    }

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(notifyId);
    }

    public void getMessages2Server(){
        Thread t = new Thread(){
            @Override
            public void run(){
                Socket s;
                OutputStream o;
                DataOutputStream odata;
                DataInputStream idata;
                s = null;
                int length;
                String message;
                try {
                    s = new Socket("10.0.2.2", 2000);
                    odata = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
                    odata.writeInt(2);
                    length = name.length();
                    odata.writeInt(length);
                    odata.write(name.getBytes("UTF-8"));
                    odata.flush();
                    idata = new DataInputStream(new BufferedInputStream(s.getInputStream()));
                    int size = idata.readInt();
                    int i = 0;
                    while(i < size){
                        length = idata.readInt();
                        byte[] buf = new byte[length];
                        idata.read(buf);
                        message = new String (buf, "UTF-8");
                        int lastId = SchedulerFile.getLastId(name, ctx);
                        lastId++;
                        String messageTotal = "" + lastId + "%" + name + "%" +message;
                        SchedulerFile.writeMessage(messageTotal, ctx);
                        if(bound){
                            act.runOnUiThread(new ShowNewMessage(act, message));
                        }else{
                            showNotification("Tienes mensajes nuevos");
                        }
                        i++;
                    }
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (s != null){
                        try{
                            s.close();
                        } catch (IOException e) {
                            System.out.println("IO exception close OutPutStream" + e);
                        }
                    }
                }

            }
        };
        t.start();
    }
}
