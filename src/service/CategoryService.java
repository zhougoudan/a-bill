package service;

import dao.CategoryDAO;
import dao.RecordDAO;
import entity.Category;
import entity.Record;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CategoryService {
    CategoryDAO categoryDAO = new CategoryDAO();
    RecordDAO recordDAO = new RecordDAO();
    public List<Category> list(){
        List<Category> cs = categoryDAO.list();
        for(Category c : cs){
            List<Record> rs = recordDAO.list(c.id);
            c.recordNumber = rs.size();
        }
        Collections.sort(cs,(c1,c2) -> c1.recordNumber - c2.recordNumber);
        return cs;
    }

    public void add(String name){
        Category category = new Category();
        category.setName(name);
        categoryDAO.add(category);
    }

    public void update(int id,String name){
        Category category = new Category();
        category.setName(name);
        category.setId(id);
        categoryDAO.update(category);
    }

    public void delete(int id){
        categoryDAO.delete(id);
    }
}
