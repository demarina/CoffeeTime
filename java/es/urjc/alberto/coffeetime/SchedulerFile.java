package es.urjc.alberto.coffeetime;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class SchedulerFile {

    public static void writeMessage(String message, Context ctx) {

        File externalStorageDir = ctx.getExternalFilesDir("myapp");
        File myFile = new File(externalStorageDir, "Messages");

        if (!myFile.exists()) {
            Log.d("piru", "No Existe!!");
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            RandomAccessFile access = new RandomAccessFile(myFile, "rw");
            int size = (int) myFile.length();
            access.seek(size);
            access.writeBytes(message);
            access.writeBytes("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            FileOutputStream fOut = new FileOutputStream(myFile);
//            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
//            myOutWriter.append(message);
//            myOutWriter.append("\n");
//            myOutWriter.close();
//            fOut.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try{
//            FileOutputStream fOut = ctx.openFileOutput("Messages", ctx.MODE_APPEND);
//            OutputStreamWriter osw = new OutputStreamWriter(fOut);
//            try{
//                osw.write(message);
//                osw.write("\n");
//                osw.flush();
//                osw.close();
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//        } catch(FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    public static int getLastId(String name, Context ctx) {
        int last = 0;

        File externalStorageDir = ctx.getExternalFilesDir("myapp");
        File myFile = new File(externalStorageDir, "Messages");

        String line;

        try {
            RandomAccessFile access = new RandomAccessFile(myFile, "rw");
            while ((line = access.readLine()) != null) {
                while ((line = access.readLine()) != null) {
                    if (line.equals(""))
                        continue;
                    String[] spliteado = line.split("%");
                    if (spliteado[1].equals(name)) {
                        int id = Integer.parseInt(spliteado[0]);
                        if (id > last)
                            last = id;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return last;

    }

//        try {
//            InputStream inputStream = ctx.openFileInput("Messages");
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader br = new BufferedReader(inputStreamReader);
//            String line;
//            try {
//                while ((line = br.readLine()) != null) {
//                    while ((line = br.readLine()) != null) {
//                    if (line.equals(""))
//                        continue;
//                    String[] spliteado = line.split("%");
//                    if (spliteado[1].equals(name)) {
//                        int id = Integer.parseInt(spliteado[0]);
//                        if (id > last)
//                            last = id;
//                    }
//                }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (br != null) br.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    public static List<String> readMessages(Context ctx, String name) {
        File externalStorageDir = ctx.getExternalFilesDir("myapp");
        File myFile = new File(externalStorageDir, "Messages");

        String line;
        List<String> messages = new ArrayList<String>();

        try {
            RandomAccessFile access = new RandomAccessFile(myFile, "rw");
            while ((line = access.readLine()) != null) {
                if (line.equals(""))
                    continue;
                String[] spliteado = line.split("%");
                if (spliteado[1].equals(name)) {
                    messages.add(spliteado[2] + "%" + spliteado[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
    }

}

//        List<String> messages = new ArrayList<String>();
//        try{
//            InputStream inputStream = ctx.openFileInput("Messages");
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader br = new BufferedReader(inputStreamReader);
//            String line;
//            try {
//                while ((line = br.readLine()) != null) {
//                    if(line.equals(""))
//                        continue;
//                    String[] spliteado = line.split("%");
//                    if(spliteado[1].equals(name)){
//                        messages.add(spliteado[2]+"%"+spliteado[3]);
//                    }
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (br != null)br.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        } catch(FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return messages;
