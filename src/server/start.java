package server;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

//启动
class start{
    String name;
    Socket client;
    sqlConnect con=new sqlConnect();
    public void Start()throws IOException, SQLException, ClassNotFoundException{
        Socket client = new Socket("localhost", 8888);
        this.client=client;
    }
    //启动收发线程
    public void startThread(){
        String name=this.name;
        Thread send = new Thread(new clientSend(client,name));
        // Thread read = new Thread(new clientReserve(client,name));
        //启动线程
        send.start();
        // read.start();
    }
    //关闭
    public void close() throws IOException {
        client.close();
    }
    //登录检查账号密码
    public boolean userUp(Socket client,String name,String password) throws IOException, SQLException, ClassNotFoundException {
        this.name=name;
        sqlConnect con=new sqlConnect();
        try {
            if(con.start(name,password)) {
                PrintStream out = new PrintStream(client.getOutputStream());//输出数据
                out.println(name);
                JOptionPane.showMessageDialog(null,"登录成功");
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    //注册
    public boolean Register(String name,String password) throws IOException, SQLException, ClassNotFoundException {
        check(name,password);
        sqlConnect con=new sqlConnect();
        return con.register(name,password);
    }
    //找回密码
    public void findPassword() throws SQLException, ClassNotFoundException {
        Scanner sc=new Scanner(System.in);
        System.out.print("请输入你的用户名：");
        String name=sc.next();
        sqlConnect con=new sqlConnect();
        con.find(name);
    }
    public String check(String name,String password){
        if(!name.equals("")||name.length()<=16) {
            if(password.length()<=20 && password.length()>=6) {
                System.out.println("注册成功");
                return "注册成功!";
            }else{
                System.out.println("注册失败");
                return "注册失败!";
            }
        }else {
            System.out.println("注册失败");
            return "注册失败!";
        }
    }
}

