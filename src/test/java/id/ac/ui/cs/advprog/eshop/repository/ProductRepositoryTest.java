package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductID(), savedProduct.getProductID());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductID("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductID(), savedProduct.getProductID());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductID(), savedProduct.getProductID());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void findByIDTest() {
        Product product1 = new Product();
        String sProductID1 = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        product1.setProductID(sProductID1);
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        String sProductID2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        product2.setProductID(sProductID2);
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        String wrongID = "b3f9de46-90b1-437d-a0bf-d0822dgq9096";
        Product searchedProduct1 = productRepository.searchByID(sProductID1);
        assertNotNull(searchedProduct1);
        assertEquals(searchedProduct1.getProductID(),product1.getProductID());
        Product searchedProduct2 = productRepository.searchByID(sProductID2);
        assertNotNull(searchedProduct2);
        assertEquals(searchedProduct2.getProductID(),product2.getProductID());
        Product shouldBeNullAfterSearch = productRepository.searchByID(wrongID);
        assertNull(shouldBeNullAfterSearch);
    }

    @Test
    void removeByIDTest() {
        Product product1 = new Product();
        product1.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        String sProductID2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        product2.setProductID(sProductID2);
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        boolean removedSuccess = productRepository.removeByID(sProductID2);
        assertTrue(removedSuccess);
        boolean removedShouldNotBeSuccess = productRepository.removeByID("a1f9de46-90b1-411d-a0bf-d0821dde9096");
        assertFalse(removedShouldNotBeSuccess);
        assertNull(productRepository.searchByID(sProductID2));
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
    }

    @Test
    void editByIDTest() {
        Product product1 = new Product();
        String sProductID1 = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        product1.setProductID(sProductID1);
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        String sProductID2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        product2.setProductID(sProductID2);
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Product editResult = new Product();
        String newName = "Nama Bebas";
        int newQuantity = 242;
        editResult.setProductID(sProductID1);
        editResult.setProductName(newName);
        editResult.setProductQuantity(newQuantity);
        assertNotEquals(product1.getProductName(),editResult.getProductName());
        assertNotEquals(product1.getProductQuantity(),editResult.getProductQuantity());

        Product kr = productRepository.edit(sProductID1,editResult);
        assertNotNull(kr);
        assertEquals(editResult.getProductName(),kr.getProductName());
        assertEquals(editResult.getProductQuantity(),kr.getProductQuantity());
        Product checkedProduct = productRepository.searchByID(sProductID1);
        assertEquals(sProductID1,checkedProduct.getProductID());
        assertEquals(newName, checkedProduct.getProductName());
        assertEquals(newQuantity,checkedProduct.getProductQuantity());
    }


}
