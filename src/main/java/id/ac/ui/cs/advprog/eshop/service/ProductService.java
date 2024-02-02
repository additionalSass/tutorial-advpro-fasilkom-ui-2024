package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();
    public Product searchByID(String sProductID);
    public Product edit(String sProductID, Product newProduct);
    public boolean removeByID(String sProductID);
}
