package es.urjc.alberto.coffeetime;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ViewContacts implements Runnable{

    View v;
    Activity act;
    String name;

    public ViewContacts(View v, Activity act, String name){
        this.v = v;
        this.act = act;
        this.name = name;
    }

    @Override
    public void run() {
        Socket s;
        OutputStream o;
        DataOutputStream odata;
        DataInputStream idata;
        s = null;
        odata = null;
        idata = null;
        int length;
        String contact;
        List<String> contList = new ArrayList<String>();
        try {
            s = new Socket("10.0.2.2", 2000);
            odata = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            odata.writeInt(4);
            length = name.length();
            odata.writeInt(length);
            odata.write(name.getBytes("UTF-8"));
            odata.flush();
            idata = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            int size = idata.readInt();
            Log.d("piru", "" + size);
            int i = 0;
            while(i < size){
                length = idata.readInt();
                byte[] buf = new byte[length];
                idata.read(buf);
                contact = new String (buf, "UTF-8");
                contList.add(contact);
                Log.d("piru", contact);
                i++;
            }
            if(size > 0){
                v.post(new showContact(act, contList));
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
}
