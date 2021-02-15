package org.example.dao;

import org.example.util.DBUtil;
import org.example.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DeleteDAO {
    public void delete(List<Integer> idList) {
        try {
            //数据库连接时有限制的，如果连接不关闭，而且使用的人比较多，那么系统很快就down掉了
            //由于实现成，一个线程只有一个Connection对象，因此直接用就行可以不用关闭Connection对象，不用担心使用的对象很多
            Connection c = DBUtil.getConnection();
            //JDK8中支持的 stream 用法，简化代码
            List<String> placeholderList = idList.stream().map(id -> "?").collect(Collectors.toList());
            String placeHolder = String.join(",", placeholderList);
            String sql = String.format("DELETE FROM file_meta WHERE id IN (%s)", placeHolder);
            try (PreparedStatement s = c.prepareStatement(sql)) {
                for (int i = 0; i < idList.size(); i++) {
                    int id = idList.get(i);
                    s.setInt(i+1, id);
                }
                int i = s.executeUpdate();
                LogUtil.log("执行SQL：%s,%s,受到影响的行数是：%d", sql, idList, i);

            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Integer> idList = new ArrayList<>();
        for (int i = 1946;i <= 1962;i++) {
            idList.add(i);
        }
        DeleteDAO deleteDAO = new DeleteDAO();
        deleteDAO.delete(idList);
    }
}
