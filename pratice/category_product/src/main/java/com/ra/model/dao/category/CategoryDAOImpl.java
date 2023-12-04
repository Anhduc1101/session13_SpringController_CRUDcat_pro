package com.ra.model.dao.category;


import com.ra.model.entity.Category;
import com.ra.util.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public List<Category> findAll() {
        Connection con = ConnectionDB.openCon();
        List<Category> categories = new ArrayList<>();
        try {
            CallableStatement cs = (CallableStatement) con.prepareCall("call proc_show_list_category");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Category cat = new Category();
                cat.setCategoryID(rs.getInt("id"));
                cat.setCategoryName(rs.getString("name"));
                cat.setCategoryStatus(rs.getBoolean("status"));
                categories.add(cat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeCon(con);
        }
        return categories;
    }

    @Override
    public Category findById(Integer id) {
        Connection con = ConnectionDB.openCon();
        Category  cat = new Category();
        try {
            CallableStatement cs = (CallableStatement) con.prepareCall("call proc_find_by_id(?)");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                cat.setCategoryID(rs.getInt("id"));
                cat.setCategoryName(rs.getString("name"));
                cat.setCategoryStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeCon(con);
        }
        return cat;
    }

    @Override
    public boolean save(Category category) {
        Connection con = ConnectionDB.openCon();
        CallableStatement cs = null;
        int check;
        try {
            if (category.getCategoryID() == 0) {
                cs = (CallableStatement) con.prepareCall("call proc_add_category(?,?)");
                cs.setString(1, category.getCategoryName());
                cs.setBoolean(2, category.isCategoryStatus());

            } else {
                cs = (CallableStatement) con.prepareCall("call proc_update_category(?,?,?)");
                cs.setString(1, category.getCategoryName());
                cs.setBoolean(2, category.isCategoryStatus());
                cs.setInt(3, category.getCategoryID());
            }
            check = cs.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeCon(con);
        }
        return false;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection con = ConnectionDB.openCon();
        try {
            CallableStatement cs = con.prepareCall("Call proc_change_status(?)");
            cs.setInt(1,id);
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDB.closeCon(con);
        }
    }
}
