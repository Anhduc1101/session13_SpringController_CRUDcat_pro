package com.ra.model.dao.product;

import com.ra.model.entity.Product;
import com.ra.model.service.category.CategoryService;
import com.ra.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {
    @Autowired
    CategoryService categoryService;

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        Connection con = ConnectionDB.openCon();
        try {
            CallableStatement cs = con.prepareCall("call proc_show_list_product()");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Product pro = new Product();
                pro.setProductID(rs.getInt("id"));
                pro.setProductName(rs.getString("name"));
                pro.setPrice(rs.getDouble("price"));
                pro.setCategory(categoryService.findById(rs.getInt("category_id")));
                products.add(pro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeCon(con);
        }
        return products;
    }

    @Override
    public void delete(Integer id) {
        Connection con = ConnectionDB.openCon();
        try {
            CallableStatement cs = con.prepareCall("call proc_delete_product(?)");
            cs.setInt(1, id);
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeCon(con);
        }
    }

    @Override
    public boolean save(Product product) {
        Connection con = ConnectionDB.openCon();
        CallableStatement cs = null;
        int check;
        try {
            if (product.getProductID() == 0) {
                cs = con.prepareCall("call proc_add_new_product(?,?,?)");
                cs.setString(1, product.getProductName());
                cs.setDouble(2, product.getPrice());
                cs.setInt(3, product.getCategory().getCategoryID());
            } else {
                cs = con.prepareCall("call proc_update_product(?,?,?,?)");
                cs.setString(1, product.getProductName());
                cs.setDouble(2, product.getPrice());
                cs.setInt(3, product.getCategory().getCategoryID());
                cs.setInt(4, product.getProductID());
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
    public Product findById(Integer id) {
        Product pro = new Product();
        Connection con = ConnectionDB.openCon();
        try {
            CallableStatement cs = con.prepareCall("call proc_find_product_by_id(?)");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                pro.setProductID(rs.getInt("id"));
                pro.setProductName(rs.getString("name"));
                pro.setPrice(rs.getDouble("price"));
                pro.setCategory(categoryService.findById(rs.getInt("category_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeCon(con);
        }
        return pro;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        Connection con = ConnectionDB.openCon();
        try {
            CallableStatement cs = con.prepareCall("call proc_find_by_name(?)");
            cs.setString(1, name);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Product pro = new Product();
                pro.setProductID(rs.getInt("id"));
                pro.setProductName(rs.getString("name"));
                pro.setPrice(rs.getDouble("price"));
                pro.setCategory(categoryService.findById(rs.getInt("category_id")));
                products.add(pro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeCon(con);
        }
        return products;
    }
}
