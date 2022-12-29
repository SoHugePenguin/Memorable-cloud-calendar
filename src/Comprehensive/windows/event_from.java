/**
 * @Project dingLaoZuoYe
 * @File event_from
 * @Time 2022/12/27 18:05
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.windows;

import Comprehensive.Utils.ImageUtils;
import Comprehensive.Utils.Location;
import Comprehensive.Utils.SpringLocation;
import Comprehensive.Utils.componentDisable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class event_from extends JFrame {
    public static String date;
    static JPanel controlPanel;
    ArrayList<String> array = new ArrayList<>();
    int showPage = 0;

    public event_from(int year, int mouth, int day) {
        this.setBounds(850, 120, 600, 600);
        this.setTitle("新增备忘录：" + year + "年" + mouth + "月" + day + "日 の 事件薄");
        date = year + "-" + mouth + "-" + day;
        //禁用调整大小
        this.setResizable(false);
        this.setIconImage(ImageUtils.book);
        //保持置顶
        this.setAlwaysOnTop(true);

        controlPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        controlPanel.setLayout(layout);
        controlPanel.setBackground(new Color(143, 103, 78, 255));

        //////记录点滴组件
        //带Scroll滚动条的多行文本域
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setLineWrap(true);        //激活自动换行功能
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);   //垂直滚动条永久显示
        JButton submitButton = new JButton("提交到云端");
        submitButton.setFont(new Font("楷体", Font.BOLD, 32));
        submitButton.addActionListener(e -> {
            try {
                String text = Location.LoadEvent(textArea.getText(), Location.UPLOAD);
                new Prompt(text);
                if (text.contains("成功")) {
                    scrollPane.setVisible(false);
                    submitButton.setVisible(false);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        scrollPane.setVisible(false);
        submitButton.setVisible(false);
        //////查看当日事件组件
        JButton left = new JButton("上一篇");
        left.addActionListener(e -> {
            if (showPage > 0) {
                textArea.setText(array.get(--showPage));
            } else {
                showPage = array.size() - 1;
                textArea.setText(array.get(showPage));
            }
        });
        JButton right = new JButton("下一篇");
        right.addActionListener(e -> {
            if (showPage < array.size() - 1) {
                textArea.setText(array.get(++showPage));
            } else {
                showPage = 0;
                textArea.setText(array.get(showPage));
            }
        });
        JButton updateButton = new JButton("修改并更新云端");
        updateButton.setFont(new Font("楷体", Font.BOLD, 32));
        left.setVisible(false);
        right.setVisible(false);
        updateButton.setVisible(false);
        //////主按钮组件
        JButton searchButton = new JButton("查看当日事件");
        searchButton.addActionListener(e -> {
            left.setVisible(true);
            right.setVisible(true);
            updateButton.setVisible(true);
            scrollPane.setVisible(false);
            submitButton.setVisible(false);
            //禁用按钮十秒
            componentDisable.disable(searchButton, 10);
            try {
                array = Location.LoadEvent(Location.GET);
                searchButton.setVisible(false);
                if (array.size() > 0) {
                    showPage = 0;
                    textArea.setText(array.get(showPage));
                    scrollPane.setVisible(true);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton writeButton = new JButton("记录点滴");
        writeButton.addActionListener(e -> {
            scrollPane.setVisible(true);
            submitButton.setVisible(true);
            left.setVisible(false);
            right.setVisible(false);
            updateButton.setVisible(false);
            textArea.setText("  ");
        });

        controlPanel.add(scrollPane);
        controlPanel.add(writeButton);
        controlPanel.add(searchButton);
        controlPanel.add(submitButton);
        controlPanel.add(left);
        controlPanel.add(right);
        controlPanel.add(updateButton);

        SpringLocation.setLocation(layout, writeButton, 10, 10);
        SpringLocation.setLocation(layout, searchButton, 100, 10);
        SpringLocation.setLocation(layout, scrollPane, 20, 100);
        SpringLocation.setLocation(layout, submitButton, 150, 460);
        SpringLocation.setLocation(layout, left, 20, 50);
        SpringLocation.setLocation(layout, right, 440, 50);
        SpringLocation.setLocation(layout, updateButton, 150, 460);

        this.add(controlPanel);
        this.setVisible(true);
    }
}
