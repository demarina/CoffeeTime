package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class AskMessage implements Runnable{

    public String name;
    public Context ctx;

    public AskMessage(String name, Context ctx){
        this.name = name;
        this.ctx = ctx;
    }

    @Override
    public void run() {
        for(;;){
            try {
                getMessages2Server();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
                odata = null;
                idata = null;
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

                        i++;
                    }
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (s != null){
                        try{
                            odata.close();
                            idata.close();
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
