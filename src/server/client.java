package server;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.Scanner;
//发
class clientSend implements Runnable{
    private Socket client;
    private String name;
    sqlConnect con=new sqlConnect();
    public clientSend(Socket client,String name){
        this.name=name;
        this.client = client;
    }
    public void run() {
        try{
            Scanner sc = new Scanner(System.in);//放入 输入数据
            PrintStream out = new PrintStream(client.getOutputStream());//输出数据
            while(true) {
                while (sc.hasNext()) {
                    String str = sc.next();
                    out.println(str);//向服务器发送数据
                    if(str.startsWith("historyMsg")){
                        //con.historyMsg(name);
                        continue;
                    }
                    if ("bye".equals(str)) {
                        out.close();
                        sc.close();
                        break;
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
//收
class clientReserve implements Runnable{
    private Socket client;
    private String name;
    public clientReserve(Socket client, String name){
        this.name= name;
        this.client = client;
    }
    @Override
    public void run() {
        Scanner sc;
        try {
            sc= new Scanner(client.getInputStream());
           while(sc.hasNext()) {
               java.awt.Toolkit.getDefaultToolkit().beep();
               System.out.println(sc.next());
           }
            sc.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

public class client {
    //主函数
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        start a=new start();
        a.Start();
    }

}
