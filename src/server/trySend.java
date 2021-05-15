package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class trySend {
    public static void main(String[] args) throws Exception {
        ServerSocket ss=new ServerSocket(9999);
        System.out.println("服务器端以及启动正在接受数据");
        Socket s=ss.accept();
        System.out.println("检测到客户端");
        InputStream in=s.getInputStream();
        FileOutputStream fos=new FileOutputStream("D:\\桌面\\a.jpg");
        byte[] buf=new byte[1024];
        int len=0;
        while((len=in.read(buf))!=-1) {
            fos.write(buf,0,len);
        }
        //收到图片，给客户端回送信息
        OutputStream out=s.getOutputStream();
        out.write("上传成功".getBytes());
        //关闭资源
        fos.close();
        in.close();
        out.close();
        s.close();
        ss.close();
    }
}

