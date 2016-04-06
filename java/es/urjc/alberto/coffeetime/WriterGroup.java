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

public class WriterGroup implements Runnable{

    String name;
    String group;
    String message;
    Activity acti;
    View v;

    public WriterGroup(String name, String group, String message, View v, Activity acti){
        this.name = name;
        this.group = group;
        this.message = message;
        this.acti = acti;
        this.v = v;
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
        try {
            s = new Socket("10.0.2.2", 2000);
            odata = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            odata.writeInt(3);
            Integer length = group.length();
            odata.writeInt(length);
            odata.write(group.getBytes("UTF-8"));
            length = name.length();
            odata.writeInt(length);
            odata.write(name.getBytes("UTF-8"));
            length = message.length();
            odata.writeInt(length);
            odata.write(message.getBytes("UTF-8"));
            odata.flush();
            idata = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            int ok = idata.readInt();
            if(ok == 1){
                String toas = "Message sent!";
                v.post(new showToast(acti, toas));
            }else if(ok == 2){
                String toas = "Group destination not exist!";
                v.post(new showToast(acti, toas));
            }else if(ok == 3){
                String toas = "You don't belong to this group!";
                v.post(new showToast(acti, toas));
            }else if(ok == 0){
                String toas = "Error to sent message, try again!";
                v.post(new showToast(acti, toas));
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
                    s.close();
                } catch (IOException e) {
                    System.out.println("IO exception close OutPutStream" + e);
                }
            }
        }
    }
}