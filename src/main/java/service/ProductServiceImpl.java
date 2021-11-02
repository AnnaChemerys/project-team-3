package service;

import dao.ProductDao;
import model.Product;
import model.ProductCategories;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDao productDao = new ProductDao();

    public void saveProduct(Product product) {
        isValidProduct(product);
        productDao.save(product);
    }

    public void updateProduct(Product product) {
        isValidProduct(product);
        productDao.update(product);
    }

    public void removeProduct(Product product) {
        isValidProduct(product);
        productDao.delete(product);
    }

    private void isValidProduct(Product product) {
        boolean isValid = product.getPrice() > 0 && product.getAmount() >= 0 && product.getCategory() != null;
        if (!isValid) {
            System.out.println("Product data is not valid.");
        }
    }

    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    public List<Product> searchProducts(String searchText) {
        return productDao.searchBy(searchText);
    }

    public List<Product> searchProductsByCategory(ProductCategories category) {
        return productDao.getByCategory(category);
    }
}
