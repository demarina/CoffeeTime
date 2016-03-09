package es.urjc.alberto.coffeetime;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Writer implements Runnable{

    String server;
    int port;
    String message;

    public Writer(String server, int port, String message){
        this.server = server;
        this.port = port;
        this.message = message;
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
            s = new Socket(this.server, this.port);
            odata = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            Integer length = message.length();
            odata.writeInt(length);
            odata.write(message.getBytes("UTF-8"));
            odata.flush();
            idata = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            length = idata.readInt();
            byte[] buf = new byte[length];
            idata.read(buf, 0, length);
            String msg = new String (buf, "UTF-8");
            Log.d("piru", msg);
            s.close();
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
