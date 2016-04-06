package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Loger implements Runnable{

    String name;
    String pass;
    View v;
    Activity main;

    public Loger(String name, String pass, View v, Activity main){
        this.name = name;
        this.pass = pass;
        this.v = v;
        this.main = main;
    }

    @Override
    public void run(){
        Socket s;
        OutputStream o;
        DataOutputStream odata;
        DataInputStream idata;
        s = null;
        odata = null;
        idata = null;
        int ok = 0;
        try {
            s = new Socket("10.0.2.2", 2000);
            odata = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            odata.writeInt(0);
            String message = this.name + "/" + this.pass;
            Integer length = message.length();
            odata.writeInt(length);
            odata.write(message.getBytes("UTF-8"));
            odata.flush();
            idata = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            ok = idata.readInt();
            if(ok == 1){
                Thread asker = new Thread(new AskMessage(name, main.getApplicationContext()));
                asker.start();
                v.post(new Go2WriterActivity(main, name));
            }else {
                String toas = "Name or password incorrect! Try again.";
                v.post(new showToast(main, toas));
            }
        } catch (ConnectException e) {
            System.out.println("connection refused" + e);
        } catch (UnknownHostException e) {
            System.out.println("cannot connect to host " + e);
        } catch (IOException e) {
            System.out.println("IO exception" + e);
        } finally {
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
}