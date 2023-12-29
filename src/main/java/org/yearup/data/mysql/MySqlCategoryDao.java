package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        List<Category> categories = null;
        String query = "SELECT * FROM easyshop.categories;";

        try(Connection connection = getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(query);
            ResultSet resultSet = selectStatement.executeQuery()){
            do {
              int categoryID = resultSet.getInt("category_id");
              String name = resultSet.getString("name");
              String description = resultSet.getString("description");
              categories.add(new Category(categoryID, name, description));
            } while (resultSet.next());
        } catch (SQLException e){
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public Category getById(int categoryId)
    {
        Category category = new Category();
        String sql = "SELECT * FROM easyshop.categories WHERE category_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

             statement.setInt(1, categoryId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category.setCategoryId(resultSet.getInt("category_id"));
                    category.setName(resultSet.getString("name"));
                    category.setDescription(resultSet.getString("description"));
                    return category;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public Category create(Category category)
    {
        // create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
