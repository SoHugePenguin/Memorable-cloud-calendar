/**
 * @Project dingLaoZuoYe
 * @File login
 * @Time 2022/12/28 16:45
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.windows;

import Comprehensive.Utils.ImageUtils;
import Comprehensive.Utils.Location;
import Comprehensive.Utils.SpringLocation;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class login extends JFrame {
    Image offscreen = null;
    JPanel jPanel;
    int mouseX, mouseY;

    public login() {
        final boolean[] f11 = {true};
        this.setBounds(0, 0, 1980, 800);
        this.setIconImage(ImageUtils.fall_icon);
        this.setTitle("万年表记事薄V1.0.5                     tip：需联网");
        this.setResizable(false);
        addComponents();
        //去除边框
        this.setUndecorated(true);
        //透明度
        this.setOpacity(1f);
        //SPECIAL 此处背景设置alpha为0，有效避免屏幕闪烁！但好像只能全屏？
        this.setBackground(new Color(0, 0, 0, 0));
        this.setVisible(true);  //这个放在组件的后面，在创建JPanel后子类isVisible仍为false如果不这样做！
        //鼠标坐标
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

    @Override
    public void paint(@NotNull Graphics g) {
        //图层1
        offscreen = createImage(2500, 1800);
        Graphics gImage = offscreen.getGraphics();
        gImage.drawImage(ImageUtils.bg1, (int) (-300 + mouseX * 0.15), (int) (-300 + mouseY * 0.15), null);
        g.drawImage(offscreen, 0, 0, this);

        //图层2
        paintComponents(g);
    }

    private void addComponents() {
        jPanel = new JPanel(true);
        SpringLayout layout = new SpringLayout();
        //设置背景为透明，保留控件显示
        jPanel.setOpaque(false);
        jPanel.setLayout(layout);

        JLabel title = new JLabel("万 年 历");
        title.setFont(new Font("楷体", Font.BOLD, 80));
        title.setForeground(new Color(34, 66, 222, 168));
        title.setIcon(new ImageIcon(ImageUtils.book));
        JLabel title2 = new JLabel("请在网络环境下使用！");
        title2.setFont(new Font("仿宋", Font.BOLD, 24));
        title2.setForeground(new Color(250, 57, 57));
        JLabel userName = new JLabel("用户名：");
        userName.setFont(new Font("仿宋", Font.BOLD, 30));
        userName.setForeground(new Color(250, 57, 57));
        JTextField inputBox1 = new JTextField();
        inputBox1.setFont(new Font("仿宋", Font.BOLD, 20));
        inputBox1.setForeground(new Color(7, 243, 173));
        inputBox1.setOpaque(false);
        JLabel userPwd = new JLabel("密码：");
        userPwd.setFont(new Font("仿宋", Font.BOLD, 31));
        userPwd.setForeground(new Color(250, 57, 57));
        JTextField inputBox2 = new JTextField();
        inputBox2.setFont(new Font("仿宋", Font.BOLD, 20));
        inputBox2.setForeground(new Color(7, 243, 173));
        inputBox2.setOpaque(false);

        JButton login = new JButton(new ImageIcon(ImageUtils.apple));
        login.setText(" 登录 ");
        login.setFont(new Font("仿宋", Font.BOLD, 40));
        login.addActionListener(e -> {
            String text = Location.login(inputBox1.getText(), inputBox2.getText(), Location.LOGIN);
            new Prompt(text);
            if (text.equals("登录成功！")) {
                this.setVisible(false);
                //登录成功
                perpetual_calendar.Registered = true;
                perpetual_calendar.main.setVisible(true);
                perpetual_calendar.userName = inputBox1.getText();
            }
        });
        JButton register = new JButton(new ImageIcon(ImageUtils.bookshelf));
        register.setText(" 注册 ");
        register.setFont(new Font("仿宋", Font.BOLD, 40));
        register.addActionListener(e -> new Prompt(Location.login(inputBox1.getText(), inputBox2.getText(), Location.REGISTER)));

        jPanel.add(title);
        jPanel.add(title2);
        jPanel.add(userName);
        jPanel.add(inputBox1);
        jPanel.add(userPwd);
        jPanel.add(inputBox2);
        jPanel.add(login);
        jPanel.add(register);

        SpringLocation.setLocation(layout, title, 540, 60);
        SpringLocation.setLocation(layout, userName, 420, 240);
        SpringLocation.setLocation(layout, inputBox1, 550, 230);
        inputBox1.setPreferredSize(new Dimension(400, 60));
        SpringLocation.setLocation(layout, userPwd, 420, 310);
        SpringLocation.setLocation(layout, inputBox2, 550, 300);
        inputBox2.setPreferredSize(new Dimension(400, 60));
        SpringLocation.setLocation(layout, login, 550, 400);
        SpringLocation.setLocation(layout, register, 770, 400);

        this.add(jPanel);
    }
}
