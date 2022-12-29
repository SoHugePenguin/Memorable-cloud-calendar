/**
 * @Project calendarDemo
 * @File loginLocation
 * @Time 2022/12/28 21:38
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.Utils;

import Comprehensive.windows.event_from;
import Comprehensive.windows.perpetual_calendar;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static Comprehensive.jdbc.sqlMain.connection;

public class Location {
    final static String salt = "cc114514";
    final static String sql_table = "calendar_user";
    public final static int LOGIN = 0;
    public final static int REGISTER = 1;
    public final static int UPLOAD = 2;
    public final static int GET = 3;

    public static @NotNull String login(String userName, String userPwd, int type) {
        if (userName == null || "".equals(userName.trim())) {
            return "用户名不能为空";
        }
        if (userPwd == null || "".equals(userPwd.trim())) {
            return "密码不能为空";
        }
        if (userPwd.length() < 8) {
            return "密码至少为8位数！";
        }
        //MD5加密+加盐二次加密
        userPwd = md5.transform(userPwd, salt);
        if (type == LOGIN) {
            try {
                //在数据库对应的数据表(login_List)查询数据，如果查到就是正确反之错误
                String select = "select * from " + sql_table + " where user =  ? and password = ?";
                PreparedStatement st = connection.prepareStatement(select);
                st.setString(1, userName);
                st.setString(2, userPwd);
                //防sql注入原理：遇到特殊字符自动\转义
                ResultSet set = st.executeQuery();
                if (set.next()) {
                    set.close();
                    //send服务器前往登录成功界面
                    return "登录成功！";
                } else {
                    set.close();
                    return "用户名或密码有误！";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (type == REGISTER) {
            try {
                String select = "select * from " + sql_table + " where user =  ?";
                PreparedStatement st = connection.prepareStatement(select);
                st.setString(1, userName);

                String addSelect = "insert into " + sql_table + "(id,user,password) values( ? , ? , ? )";
                PreparedStatement st2 = connection.prepareStatement(addSelect);

                //防sql注入原理：遇到特殊字符自动\转义
                ResultSet set = st.executeQuery();
                if (set.next()) {
                    return "该用户名已存在！";
                } else {
                    String search = "SELECT COUNT(*)FROM calendar_user;";
                    PreparedStatement st3 = connection.prepareStatement(search);
                    ResultSet st3_r = st3.executeQuery();
                    if (st3_r.next()) {
                        st2.setInt(1, (st3_r.getInt(1)));
                    }
                    st2.setString(2, userName);
                    st2.setString(3, userPwd);
                    st2.executeUpdate();
                    st.close();
                    st2.close();
                    st3.close();
                    set.close();
                    return "你已经成功注册！";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return "ERROR";
        }
    }

    public static @NotNull String LoadEvent(String text, int type) throws Exception {
        if (perpetual_calendar.userName == null) {
            return "未登录状态！";
        }
        if (type == UPLOAD) {
            if (text.length() < 10) {
                return "字数少于10字！";
            }
            String sql = "insert into calendar_data values ( ? ,DATE(?), ?);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, perpetual_calendar.userName);
            st.setString(2, event_from.date);
            st.setString(3, text);
            //防sql注入原理：遇到特殊字符自动\转义
            if (st.executeUpdate() > 0) {
                return "上传成功！";
            }
        }
        return "ERROR";
    }

    public static @NotNull ArrayList<String> LoadEvent(int type) throws Exception {
        if (type == GET) {
            ArrayList<String> array = new ArrayList<>();
            String sql = "SELECT event FROM calendar_data where user = ? and date = DATE(?);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, perpetual_calendar.userName);
            st.setString(2, event_from.date);
            ResultSet set = st.executeQuery();
            int i = 1;
            if (set.next()) {
                do {
                    array.add(set.getString(i));
                } while (set.next());
            }
            return array;
        }
        return new ArrayList<>();
    }
}
