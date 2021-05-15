package server;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class JFchat extends JFrame implements ActionListener {
    private JLabel lblpeo;
    private JTextArea read, write;
    public static start a;
    private JFrame frame;
    private JScrollPane src;
    private JButton JBsend,his;
    private JButton[] peo=new JButton[20];
    private Container con;
    public JFchat(start a) {
        this.a=a;
        //a.startThread();
        Thread read1 = new Thread(new clientReserve1(a.client,a.name));
        read1.start();
        frame = new JFrame("聊天室");
        con = frame.getContentPane();
        frame.setLayout(null);
        //初始化
        read=new JTextArea();
        write=new JTextArea(20,40);
        peo[0] =new JButton(a.name);
        JBsend = new JButton("发送");
        src = new JScrollPane();
        his=new JButton("historyMsg");
        //样式
        Font font=new Font("宋体",Font.PLAIN,25);
        lblpeo=new JLabel("用户列表",JLabel.CENTER);
        lblpeo.setFont(font);
        read.setFont(font);
        read.setEditable(false);
        write.setFont(font);
        peo[0].setContentAreaFilled(false);//设置按钮透明
        peo[0].setFont(font);
        peo[0].setForeground(Color.red);
        read.setLineWrap(true);//一般的换行。
        read.setWrapStyleWord(true);//以单词边缘为界限分行。
        write.setLineWrap(true);
        write.setWrapStyleWord(true);
        his.setContentAreaFilled(false);//设置按钮透明
        //加监听器
        JBsend.addActionListener(this);
        his.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    history();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //位置
        his.setBounds(700,470,100,30);
        src.setBounds(0,0,800,470);
        write.setBounds(0,500,800,300);
        read.setBounds(0,0,800,470);
        lblpeo.setBounds(800,0,200,800);
        peo[0].setBounds(800,0,200,40);
        JBsend.setBounds(800,700,200,60);
        con.add(read);
        con.add(write);
        con.add(lblpeo);
        con.add(peo[0]);
        con.add(JBsend);
        con.add(src);
        con.add(his);
        src.setViewportView(read);
        frame.setSize(1000,800);//窗口大小设置
        frame.setLocation(500, 100);//窗口位置设置
        frame.setResizable(false);//窗口大小不可变更
        frame.setVisible(true);//窗口可见
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void history() throws IOException, SQLException, ClassNotFoundException {
        a.con.historyMsg(a.name,read);
    }

    //发送图片
    public void sendImg() throws IOException {

    }
    //上线初始化用户
    public void people() throws IOException {
        String s ="people";
        PrintStream writer =new PrintStream(a.client.getOutputStream());
        writer.println(s);
    }
    //添加用户按钮
    int i=1;
    int h=43;
    public void add(String name){
        Font font=new Font("宋体",Font.PLAIN,25);
        peo[i] =new JButton(name);
        peo[i].setContentAreaFilled(false);//设置按钮透明
        peo[i].setFont(font);
        peo[i].setForeground(Color.blue);
        peo[i].setBounds(800,h,200,40);
        con.add(peo[i]);
        i++;
        h=h+40;
    }
    @Override
    //发
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==JBsend){
            String msg = write.getText();
            PrintStream writer;
            write.setText("");
            try {
                if ("bye".equals(msg)) {
                    writer = new PrintStream(a.client.getOutputStream());//写消息
                    writer.println(msg);
                    a.client.close();
                    System.exit(0);
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                read.append(df.format(new Date())+"  "+a.name+"\n\t"+msg+"\n");
                writer = new PrintStream(a.client.getOutputStream());//写消息
                writer.println(msg);
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    //收
    class clientReserve1 implements Runnable{
        private Socket client;
        private String name;
        public clientReserve1(Socket client,String name){
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
                    String msg=sc.next();
                    if(msg.startsWith("用户上线了:")){
                        add(msg.split(":")[1]);
                        continue;
                    }
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    read.append(df.format(new Date())+msg+"\n");
                }
                sc.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        start a=new start();
        JFchat x = new JFchat(a);
    }
}
