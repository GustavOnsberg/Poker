package server;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class NonGameConnectionsHandler implements Runnable{
    ArrayList<BlockingQueue> queues = new ArrayList<BlockingQueue>();

    public NonGameConnectionsHandler(){

    }


    @Override
    public void run() {
        while(true){
            for(int i = 0; i < queues.size(); i++){
                if (!queues.get(i).isEmpty()){
                    try {
                        serverDo(queues.get(i).take().toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    public void serverDo(String input){
        System.out.println("Do: " + input);
    }
}
