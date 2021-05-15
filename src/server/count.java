package server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class count {
    public static Map<String, Socket> map = new HashMap<String, Socket>();//很关键这个HashMap//很关键这个static
    public void up(String useName, Socket client) {
        map.put(useName,client);
    }
    public void down(String userName){
        map.remove(userName);
    }

    public void viewMap(){
        for(Map.Entry<String, Socket> all : map.entrySet()) {
            System.out.println("k:" + all.getKey() + "\nv:" + all.getValue());
       }
    }
    public Map<String,Socket> get() {
        return map;
    }
    public int view() {
        return map.size();
    }
}
