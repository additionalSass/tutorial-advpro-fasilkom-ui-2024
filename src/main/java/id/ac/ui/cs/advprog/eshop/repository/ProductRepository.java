package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product searchByID(String sProductID) {
        Iterator<Product> productIterator = findAll();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            if (product.getProductID().equals(sProductID)) {
                return product;
            }
        }
        return null;
    }
    public Product edit(String sProductID, Product newProduct) {
        Iterator<Product> productIterator = findAll();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            if (product.getProductID().equals(sProductID)) {
                product.setProductName(newProduct.getProductName());
                product.setProductQuantity(newProduct.getProductQuantity());
                return product;
            }
        }
        return null;
    }
}
