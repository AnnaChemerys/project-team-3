package dao;

import model.Product;
import model.ProductCategories;
import util.FileOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoImpl implements ProductDao {

    private final String filename = "products.ser";
    private List<Product> products;
    private final FileOperation fileOperation = new FileOperation();

    public ProductDaoImpl() {
        List<Object> raw = fileOperation.readFromFile(filename);
        products = raw.stream()
                .map(p -> (Product) p)
                .collect(Collectors.toList());
    }

    private void writeToFile(List<Product> products) {
        List<Object> objects = products.stream()
                .map(p -> (Object) p)
                .collect(Collectors.toList());

        fileOperation.writeIntoFile(objects, filename);
    }

    @Override
    public void save(Product product) {
        products.add(product);
        writeToFile(products);
    }

    @Override
    public void update(Product product) {
        for (Product productTemp : products) {
            if (productTemp.getId() == product.getId()) {
                productTemp.setName(product.getName());
                productTemp.setPrice(product.getPrice());
                productTemp.setAmount(product.getAmount());
                productTemp.setCategory(product.getCategory());
                writeToFile(products);
            }
        }
    }

    @Override
    public void delete(Product product) {
        int deleteItem = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                deleteItem = i;
            }
        }

        if (deleteItem != -1) {
            products.remove(deleteItem);
            writeToFile(products);
        }
    }

    @Override
    public List<Product> searchBy(String text) {
        List<Product> productsYouLookingFor = new ArrayList<>();
        for (Product productTemp : products) {
            if (productTemp.getName().contains(text)) {
                productsYouLookingFor.add(productTemp);
            }
        }
        return productsYouLookingFor;
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public Product getById(long id) {
        for (Product productTemp : products) {
            if (productTemp.getId() == id) {
                return productTemp;
            }
        }
        return null;
    }

    @Override
    public List<Product> getByCategory(ProductCategories category) {
        List<Product> newProducts = new ArrayList<>();
        for (Product productTemp : products) {
            if (productTemp.getCategory() == category) {
                newProducts.add(productTemp);
            }
        }
        return newProducts;
    }
}
