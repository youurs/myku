package server;


import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class tryFrame extends JFrame implements ActionListener{
    private JLabel lblName,lblPassword;
    private JButton btnOk,btnCancel;
    private JTextField jtfName;
    private JPasswordField jtfpassword;
    private JPanel jpbtn,jpMain;
    public static start a;
    public tryFrame() {
        // TODO 自动生成的构造函数存根
        super("登录");

        //按钮部分界面
        jpbtn=new JPanel();
        btnOk=new JButton("确定");
        btnCancel=new JButton("取消");
        //添加按钮
        jpbtn.add(btnOk);
        jpbtn.add(btnCancel);


        //登录信息部分界面
        jpMain=new JPanel();//新建面板
        lblName=new JLabel("用户名:");
        lblPassword=new JLabel("密   码:");
        jtfName=new JTextField(15);
        jtfpassword=new JPasswordField(15);
        //添加标签以及文本框
        jpMain.add(lblName);
        jpMain.add(jtfName);
        jpMain.add(lblPassword);
        jpMain.add(jtfpassword);

        //添加按钮监听器
        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);

        Container con =this.getContentPane();//顶级容器
        //使用边框式布局
        con.add(jpbtn,BorderLayout.SOUTH);
        con.add(jpMain,BorderLayout.CENTER);

        this.setSize(250,150);//窗口大小设置
        this.setLocation(500, 300);//窗口位置设置
        this.setResizable(false);//窗口大小不可变更
        this.setVisible(true);//窗口可见
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//EXIT_ON_CLOSE：使用System exit方法退出应用程序
    }

    //创建主函数
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        tryFrame sl=new tryFrame();
        a=new start();
        a.Start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 此处可以连接数据库改为按钮的具体事件，这里只做一个简单的测试
        if(e.getSource()==btnOk){
            String name=jtfName.getText();
            String password=new String(jtfpassword.getPassword());
            //this.name = name;
            try {
                if(a.userUp(a.client,name,password)){
                   a.startThread();
                }else{
                    JOptionPane.showMessageDialog(null,"登录失败");
                    jtfName.setText("");
                    jtfpassword.setText("");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if(e.getSource()==btnCancel){
            System.exit(0);
            try {
                a.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
