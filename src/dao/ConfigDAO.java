package dao;

import entity.Config;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigDAO {
    public int getTotal(){
        int total = 0;
        try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT * FROM config";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }


    public void add(Config config){
        String sql = "INSERT INTO config VALUES(null,?,?";
        try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,config.key);
            ps.setString(2,config.value);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()){
                int id = rs.getInt(1);
                config.id = id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Config config){
         String sql = "UPDATE config SET key_=?, value=?, WHERE id = ?";
         try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
             ps.setString(1,config.key);
             ps.setString(2,config.value);
             ps.setInt(3,config.id);
             ps.execute();
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
    }

    public void delete(int id){
        String sql = "DELETE FROM config WHERE id=" + id;
        try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Config get(int id){
        String sql = "SELECT * FROM config WHERE id=" + id;
        Config config = new Config();

        try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement()){
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()){
                config.key = rs.getString("key_");
                config.value = rs.getString("value");
                config.id = id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return config;
    }

    public List<Config> list(int start,int count){
        List<Config> configs = new ArrayList<Config>();
        String sql = "SELECT * FROM config ORDER BY id DESC limit ?,?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Config config = new Config();
                int id = rs.getInt(1);
                String key = rs.getString("key_");
                String value = rs.getString("value");
                config.id = id;
                config.key = key;
                config.value = value;
                configs.add(config);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return configs;
    }

    public Config getByKey(String key) {
        Config config = null;
        String sql = "select * from config where key_ = ?" ;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setString(1, key);
            ResultSet rs =ps.executeQuery();

            if (rs.next()) {
                config = new Config();
                int id = rs.getInt("id");
                String value = rs.getString("value");
                config.key = key;
                config.value = value;
                config.id = id;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return config;
    }

}
