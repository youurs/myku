package server;


import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.net.URL;

public class test {
    public static void main(String[] args) throws Exception {
        //1.连接诶服务器
        Socket s = new Socket("127.0.0.1",9999);
        System.out.println("已连接到服务器9999端口，准备传送图片...");
        //获取图片字节流
        FileInputStream fis = new FileInputStream("D:\\桌面\\考试.jpg");
        //获取输出流
        //OutputStream out = s.getOutputStream();
        BufferedOutputStream bos=new BufferedOutputStream(s.getOutputStream());
        byte[] buf = new byte[1024];
        int len = 0;
        //2.往输出流里面投放数据
        while ((len = fis.read(buf)) != -1)
        {
            bos.write(buf,0,len);
        }
        //通知服务端，数据发送完毕
        s.shutdownOutput();
        //3.获取输出流，接受服务器传送过来的消息“上传成功”
        //InputStream in = s.getInputStream();
        BufferedInputStream bis=new BufferedInputStream(s.getInputStream());
        byte[] bufIn = new byte[1024];
        int num = bis.read(bufIn);
        System.out.println(new String(bufIn,0,num));
        //关闭资源
        fis.close();
        bos.close();
        bis.close();
        s.close();

    }
}

