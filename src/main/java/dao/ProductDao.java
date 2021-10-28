package dao;

import model.Product;
import model.ProductCategories;

import java.util.List;

/**
 * Created by Igor on 10/8/2019.
 */
public interface ProductDao {

    void save(Product product);

    void update(Product product);

    void delete(Product product);

    List<Product> searchBy(String name);

    List<Product> getAll();

    Product getById(long id);

    List<Product> getByCategory(ProductCategories category);
}
