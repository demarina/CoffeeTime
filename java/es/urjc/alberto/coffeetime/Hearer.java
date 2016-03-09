package es.urjc.alberto.coffeetime;

import java.net.Socket;

public class Hearer implements Runnable{

    private Socket socket;

    public Hearer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
