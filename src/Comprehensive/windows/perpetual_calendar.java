/**
 * @Project dingLaoZuoYe
 * @File 万年表
 * @Time 2022/12/27 11:05
 * @ToDo 简单的能记事的日历
 * @Author SoHugePenguin
 */
package Comprehensive.windows;

import Comprehensive.Utils.ImageUtils;
import Comprehensive.Utils.objs.ballObject;
import Comprehensive.Utils.table;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class perpetual_calendar extends JFrame {
    public static String userName;
    public static boolean Registered = false;
    final String day_of_week = "日一二三四五六";
    public static perpetual_calendar main;
    static int year, mouth, day, week, hour, minute, second;
    static int yearT, mouthT, weekT;
    Image offScreen;
    Calendar c;
    int mouseX, mouseY;
    iconRightWindow window;

    public perpetual_calendar() {
        //鼠标光标坐标持续获取
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

    public void launch() {
        this.setSize(1920, 1080);
        this.setBackground(new Color(0, 0, 0, 255));
        this.setLocationRelativeTo(null);
//        //EXIT_ON_CLOSE JFrame界面关闭后自动退出本程序 -> 计时器isShowing替代方案
//        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //伪全屏
        //this.getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
        this.setTitle("万年历");
        //自定义icon
        this.setIconImage(ImageUtils.ball);
        //按钮碰撞面积注册
        addButtons();
        //鼠标事件注册

        //先触发登录界面
        login login = new login();
        login.setVisible(true);

        this.addMouseListener(new MouseListener() {
            ballObject releaseObj;

            event_from from;

            @Override
            public void mouseClicked(MouseEvent e) {
                //鼠标点击
                ArrayList<ballObject> list = table.dayList;
                if (list == null) return;
                for (ballObject obj : list) {
                    if (e.getX() > obj.getStartX() - 25 && e.getX() < obj.getStartX() + 25 && e.getY() > obj.getStartY() - 25 && e.getY() < obj.getStartY() + 25) {
                        //TODO 生成新组件
                        if (from != null) {
                            from.setVisible(false);
                        }
                        from = new event_from(yearT, mouthT, obj.getDay());
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //鼠标按下
                ArrayList<ballObject> list = table.dayList;
                if (list == null) return;
                for (ballObject obj : list) {
                    if (e.getX() > obj.getStartX() - 25 && e.getX() < obj.getStartX() + 25 && e.getY() > obj.getStartY() - 25 && e.getY() < obj.getStartY() + 25) {
                        obj.setColor(Color.red);
                        releaseObj = obj;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //鼠标释放
                if (releaseObj != null) {
                    releaseObj.setColor(Color.cyan);
                    releaseObj = null;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //鼠标进入
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //鼠标退出
            }
        });
        //系统托盘
        SystemTray systemTray;
        if (SystemTray.isSupported()) {
            systemTray = SystemTray.getSystemTray();
            TrayIcon trayIcon = new TrayIcon(ImageUtils.bookshelf);
            trayIcon.setImageAutoSize(true);    //图片自适应
            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
            //最小化销毁资源事件
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowIconified(WindowEvent e) {
                    perpetual_calendar.this.dispose();
                    main.setVisible(false);
                }
            });
            //系统托盘对象唤醒事件
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //鼠标左击
                    if (e.getClickCount() == 1 && e.getButton() == 1) {
                        if (Registered) {
                            perpetual_calendar.this.setExtendedState(JFrame.NORMAL);
                            main.setVisible(true);  //销毁过了Visible会变false需要重新给true
                        } else {
                            login.setVisible(true);
                        }
                        if (window != null) window.dispose();
                    }
                    //鼠标右键事件
                    if (e.getButton() == 3) {
                        if (window != null) window.dispose();
                        window = new iconRightWindow(
                                MouseInfo.getPointerInfo().getLocation());
                        window.setFocusable(true);
                        //右键菜单组件注册焦点监听
                        window.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                super.focusLost(e);
                                if (MouseInfo.getPointerInfo().getLocation().x > window.getX() &&
                                        MouseInfo.getPointerInfo().getLocation().x < window.getX() + window.getWidth() &&
                                        MouseInfo.getPointerInfo().getLocation().y > window.getY() &&
                                        MouseInfo.getPointerInfo().getLocation().y < window.getY() + window.getHeight()) {
                                    System.exit(0);
                                } else {
                                    window.dispose();
                                }
                                //失去焦点的时候摧毁资源
                            }
                        });
                    }
                }
            });
        }
        this.setVisible(false);
        //循环绘画
        while (true) {
            if (userName != null && !this.getTitle().contains(userName)) {
                this.setTitle("万年历    当前用户：" + userName);
            }
            try {
                //北京时间
                c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
                year = c.get(Calendar.YEAR);
                mouth = c.get(Calendar.MONTH) + 1;  //这里一月返回0
                day = c.get(Calendar.DATE);
                week = c.get(Calendar.DAY_OF_WEEK);//周日到周六依次为12345678,周六是最后一天
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                second = c.get(Calendar.SECOND);
                //重新绘制
                repaint();
                login.repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    ballObject light_button;

    @Override
    public void paint(@NotNull Graphics g) {
        offScreen = createImage(1920, 1080);

        Graphics gImage = offScreen.getGraphics();
        //绘制日历表
        if (table.dayList == null && c != null) {
            yearT = c.get(Calendar.YEAR);
            mouthT = c.get(Calendar.MONTH) + 1;  //这里一月返回0
            //获取当前月第一天的星期
            weekT = (c.get(Calendar.DATE) - c.get(Calendar.DAY_OF_WEEK)) % 7;
            if (weekT == 0) weekT = 6;
            table.tablePrint(yearT, mouthT, weekT);
        }

        gImage.drawImage(ImageUtils.bg3, (int) (-200 + mouseX * 0.04), (int) (0 + mouseY * 0.04), null);

        gImage.setColor(Color.pink);
        gImage.drawString("一", 100, 200);
        gImage.drawString("二", 220, 200);
        gImage.drawString("三", 340, 200);
        gImage.drawString("四", 460, 200);
        gImage.drawString("五", 580, 200);
        gImage.drawString("六", 700, 200);
        gImage.drawString("日", 820, 200);

        gImage.setFont(new Font("楷体", Font.BOLD, 30));
        gImage.setColor(Color.orange);
        gImage.drawString(yearT + "年 | " + mouthT + "月   日历", 400, 150);

        ArrayList<ballObject> list = table.dayList;
        if (list == null) return;
        gImage.setFont(new Font("楷体", Font.BOLD, 20));
        for (ballObject ballObject : list) {
            gImage.setColor(ballObject.getColor());
            gImage.drawImage(ballObject.getImage(), ballObject.getStartX(), ballObject.getStartY(), null);
            gImage.drawString("" + ballObject.getDay(), ballObject.getStartX(), ballObject.getStartY());

            //鼠标经过组件时的高亮显示
            if (mouseX > ballObject.getStartX() - 25 && mouseX < ballObject.getStartX() + 25 &&
                    mouseY > ballObject.getStartY() - 25 && mouseY < ballObject.getStartY() + 25) {
                if (light_button != ballObject) {
                    ballObject.setStartY(ballObject.startY -= 8);
                    light_button = ballObject;
                }
            }
            //修复长按时的paint失效BUG
            if ((ballObject.startY - 250) % 80 < 72 && (ballObject.startY - 250) % 80 > 40) {
                ballObject.setStartY(ballObject.startY + 80 - (ballObject.startY - 250) % 80);
                light_button = null;
            }
        }

        //鼠标离开组件时的还原显示
        if (light_button != null && !(mouseX > light_button.getStartX() - 25 && mouseX < light_button.getStartX() + 25 &&
                mouseY > light_button.startY - 25 && mouseY < light_button.startY + 25)) {
            light_button.setStartY(light_button.startY += 8);
            light_button = null;
        }

        gImage.setColor(Color.white);
        gImage.setFont(new Font("仿宋", Font.BOLD, 30));
        gImage.drawString("浏览上月", 150, 100);
        gImage.drawString("浏览下月", 1150, 100);
        gImage.setFont(new Font("仿宋", Font.BOLD, 20));
        gImage.drawString("浏览上年", 20, 100);
        gImage.drawString("浏览下年", 1300, 100);
        gImage.setFont(new Font("仿宋", Font.BOLD, 25));
        gImage.drawString("企鹅出品", 1270, 750);
        gImage.setColor(Color.green);
        gImage.setFont(new Font("仿宋", Font.BOLD, 20));
        gImage.drawString("当前时间：" + year + "年" + mouth + "月" + day + "日" +
                hour + "时" + minute + "分" + second + "秒  星期" + day_of_week.charAt(week - 1), 30, 750);
        g.drawImage(offScreen, 0, 0, null);
    }

    public void addButtons() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        //按钮1   上一月
        JButton button = new JButton("");
        button.setBackground(Color.green);
        button.setBounds(150, 47, 120, 35);
        button.setBorder(null);
        button.addActionListener(e -> {
            mouthT--;
            if (mouthT <= 0) {
                mouthT = 12;
                yearT--;
            }
            weekT = weekT - table.getMouthDay(yearT, mouthT) % 7;
            if (weekT < 0) weekT = 7 + weekT;
            table.tablePrint(yearT, mouthT, weekT);
        });
        //按钮2   下一月
        JButton button2 = new JButton("");
        button2.setBackground(Color.green);
        button2.setBounds(1150, 47, 120, 35);
        button2.setBorder(null);
        button2.addActionListener(e -> {
            weekT = weekT + table.getMouthDay(yearT, mouthT) % 7;
            if (weekT > 6) weekT = weekT - 7;
            mouthT++;
            //星期前后不同计算顺序相反
            if (mouthT > 12) {
                mouthT = 1;
                yearT++;
            }
            table.tablePrint(yearT, mouthT, weekT);
        });
        //按钮3   下一年
        JButton button3 = new JButton("");
        button3.setBackground(Color.green);
        button3.setBounds(1300, 50, 80, 26);
        button3.setBorder(null);
        button3.addActionListener(e -> {
            for (int i = 0; i < 12; i++) {
                weekT = weekT + table.getMouthDay(yearT, mouthT) % 7;
                if (weekT > 6) weekT = weekT - 7;
                mouthT++;
                //星期前后不同计算顺序相反
                if (mouthT > 12) {
                    mouthT = 1;
                    yearT++;
                }
            }
            table.tablePrint(yearT, mouthT, weekT);
        });
        //按钮4   上一年
        JButton button4 = new JButton("");
        button4.setBackground(Color.green);
        button4.setBounds(20, 50, 80, 26);
        button4.setBorder(null);
        button4.addActionListener(e -> {
            for (int i = 0; i < 12; i++) {
                mouthT--;
                if (mouthT <= 0) {
                    mouthT = 12;
                    yearT--;
                }
                weekT = weekT - table.getMouthDay(yearT, mouthT) % 7;
                if (weekT < 0) weekT = 7 + weekT;
            }
            table.tablePrint(yearT, mouthT, weekT);
        });
        //按钮5   jdbc列出所有事件
        JButton button5 = new JButton("");
        button5.setBackground(Color.green);
        button5.setBounds(1270, 700, 150, 26);
        button5.setBorder(null);
        button5.addActionListener(e -> {
            //TODO TABLE
        });

        //注册按钮事件
        controlPanel.add(button);
        controlPanel.add(button2);
        controlPanel.add(button3);
        controlPanel.add(button4);
        controlPanel.add(button5);
        main.add(controlPanel);
    }
}
