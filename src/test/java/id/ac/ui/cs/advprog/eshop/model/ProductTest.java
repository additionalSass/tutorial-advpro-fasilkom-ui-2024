package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @BeforeEach
    public void setUp() {
        this.product = new Product();
        this.product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    public void testGetProductID() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductID());
    }

    @Test
    public void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    public void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }
}

