package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.io.PrintStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServerThread implements Runnable {
    private Socket client;
    private String name;
    private count c = new count();
    private static Map<String, Socket> clientMap = new HashMap<String, Socket>();//很关键这个HashMap

    public ServerThread(Socket client)  {
        this.client = client;
        //读取用户名
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.name = buf.readLine();
            userUp(name, client);
        } catch (Exception e) {
        }

    }

    @Override
    public void run() {
        PrintStream out = null;
        BufferedReader buf = null;
        sqlConnect con=new sqlConnect();
        try {
            buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintStream(client.getOutputStream());
            while (true) {
                String str = buf.readLine();
                System.out.println(name + "消息" + str);
                //out.println(name+ str);
                if (str.startsWith("user")) {
                    privateSend(str.split(":")[1], str.split(":")[2]);
                    con.keepMsg(name,str);
                    continue;

                }
                if (str.startsWith("people")) {
                    System.out.println(c.view(

                    ));
                    c.viewMap();
                    continue;
                }
                if("bye".equals(str)){
                    userDown();
                    break;
                }
                allSend(str);
                con.keepMsg(name,str);
            }
            out.close();
            client.close();
        } catch (Exception e) {
        }

    }

    //上线
    public void userUp(String useName, Socket client) throws IOException {
        c.up(useName, client);
        clientMap=c.get();
        System.out.println("用户姓名为：" + useName + "用户socket为：" + client + "上线了！");
        System.out.println("当前用户数为：" + clientMap.size() + "人");
        for(Map.Entry<String, Socket> all : clientMap.entrySet()){
            String username = all.getKey();
            if(username.equals(name)) {
            }else{
                client = clientMap.get(username);
                //获取私聊客户端的输出流，将私聊信息发送到指定客户端
                PrintStream out = new PrintStream(client.getOutputStream());
                out.println("用户上线了:"+name);
            }
        }
    }

    //下线
    public void userDown() throws IOException {
        c.down(name);
        clientMap=c.get();
        for(Map.Entry<String, Socket> all : clientMap.entrySet()){
            String username = all.getKey();
            if(username.equals(name)) {
            }else{
                client = clientMap.get(username);
                //获取私聊客户端的输出流，将私聊信息发送到指定客户端
                PrintStream out = new PrintStream(client.getOutputStream());
                out.println(name+"下线了");
            }
        }
    }
    //私发
    public void privateSend(String userName, String msg) throws IOException {
        clientMap=c.get();
        String username = null;
        //取得私聊用户名对应的客户端
        Socket client = null;
        if(clientMap.containsKey(userName)) {
             client = clientMap.get(userName);
        }else{
            System.out.println("没有找到该用户");
        }
        //获取私聊客户端的输出流，将私聊信息发送到指定客户端
        PrintStream out = new PrintStream(client.getOutputStream());
        System.out.println("正在私聊"+userName);
        out.println("["+name+"]:"+ msg);
    }
    //群聊
    public void allSend(String msg)throws IOException{
        for(Map.Entry<String, Socket> all : clientMap.entrySet()){
                String username = all.getKey();
            if(username.equals(name)) {
            }else{
                Socket client = clientMap.get(username);
                //获取私聊客户端的输出流，将私聊信息发送到指定客户端
                PrintStream out = new PrintStream(client.getOutputStream());
                out.println("[世界]"+name +":"+ msg);
            }
        }
    }
}

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        ExecutorService executorService =Executors.newFixedThreadPool(20);
        Map<String, Socket> map = new HashMap<String, Socket>();
        boolean f = true;
        for (int i=0;i<20;i++) {
            System.out.println("服务器正在等待连接");
            Socket client = server.accept();
            executorService.execute(new ServerThread(client));
        }
        executorService.shutdown();
        server.close();
    }
}
