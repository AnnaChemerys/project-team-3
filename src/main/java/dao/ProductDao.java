package dao;

import model.Product;
import model.ProductCategories;

import java.util.ArrayList;
import java.util.List;

public class ProductDao extends AbstractDao<Product> {

    @Override
    protected String getFileName() {
        return FileNames.PRODUCTS.getFileName();
    }

    @Override
    public void update(Product product) {
        List<Product> tempList = items;
        for (Product productTemp : tempList) {
            if (productTemp.getId() == product.getId()) {
                productTemp.setName(product.getName());
                productTemp.setPrice(product.getPrice());
                productTemp.setAmount(product.getAmount());
                productTemp.setCategory(product.getCategory());
            }
        }
        fileOperation.writeIntoFile(tempList, filename);
        items = tempList;
    }


    public List<Product> searchBy(String text) {
        List<Product> productsYouLookingFor = new ArrayList<>();
        List<Product> tempList = items;
        for (Product productTemp : tempList) {
            if (productTemp.getName().contains(text)) {
                productsYouLookingFor.add(productTemp);
            }
        }
        return productsYouLookingFor;
    }


    public List<Product> getByCategory(ProductCategories category) {
        List<Product> newProducts = new ArrayList<>();
        List<Product> tempList = items;
        for (Product productTemp : tempList) {
            if (productTemp.getCategory() == category) {
                newProducts.add(productTemp);
            }
        }
        return newProducts;
    }
}
