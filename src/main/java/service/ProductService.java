package service;

import model.Product;
import model.ProductCategories;

import java.util.List;

/**
 * Created by Igor on 10/8/2019.
 */
public interface ProductService {

    void saveProduct(Product product) ;

    void updateProduct(Product product);

    void removeProduct(Product product);

    List<Product> getAllProducts();

    List<Product> searchProducts(String ssearchText);

    List<Product> searchProductsByCategory(ProductCategories category);

}
