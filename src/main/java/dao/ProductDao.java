package dao;

import model.Product;
import model.ProductCategories;

import java.util.List;
import java.util.stream.Collectors;

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
        return items.stream()
                .filter(item -> item.getName().contains(text))
                .collect(Collectors.toList());
    }


    public List<Product> getByCategory(ProductCategories category) {
        return items.stream()
                .filter(item -> item.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}