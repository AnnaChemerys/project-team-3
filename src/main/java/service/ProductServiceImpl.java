package service;

import dao.ProductDao;
import model.Product;
import model.ProductCategories;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao = new ProductDao();

    public void saveProduct(Product product) {
        if (isValidProduct(product) && isExistProduct(product)) {
            productDao.save(product);
        }
    }

    public void updateProduct(Product product) {
        if (isValidProduct(product)) {
            productDao.update(product);
        }
    }

    public void removeProduct(Product product) {
        if (isValidProduct(product)) {
            productDao.delete(product);
        }
    }

    private boolean isValidProduct(Product product) {
        boolean isValid = product.getPrice() > 0 && product.getAmount() >= 0 && product.getCategory() != null;
        if (!isValid) {
            System.out.println("Product data is not valid.");
            return false;
        }
        System.out.println("Product data is valid.");
        return true;
    }

    private boolean isExistProduct(Product product) {
        for (Product tempProduct : productDao.getAll()) {
            if (tempProduct.getName().equals(product.getName()) && tempProduct.getCategory().equals(product.getCategory())) {
                System.out.println("Product data is exist");
                return false;
            }
        }
        return true;
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

    public Product getProductById(String Id) {
        return productDao.getById(Id);
    }
}
