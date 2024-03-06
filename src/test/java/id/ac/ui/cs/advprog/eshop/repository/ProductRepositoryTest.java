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

    Product mockProduct;

    @BeforeEach
    public void setUp() {
        mockProduct = new Product();
        mockProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        mockProduct.setProductName("Sampo Cap Bambang");
        mockProduct.setProductQuantity(100);
    }

    @Test
    public void testCreateAndFind() {
        productRepository.create(mockProduct);
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(mockProduct.getProductId(), savedProduct.getProductId());
        assertEquals(mockProduct.getProductName(), savedProduct.getProductName());
        assertEquals(mockProduct.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    public void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    public void testFindAllIfMoreThanOneProduct() {
        productRepository.create(mockProduct);
        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(mockProduct.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    public void findByIdTest() {
        productRepository.create(mockProduct);

        Product product2 = new Product();
        String sProductID2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        product2.setProductId(sProductID2);
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        String wrongID = "b3f9de46-90b1-437d-a0bf-d0822dgq9096";
        Product searchedProduct1 = productRepository.searchById(mockProduct.getProductId());
        assertNotNull(searchedProduct1);
        assertEquals(searchedProduct1.getProductId(),mockProduct.getProductId());
        Product searchedProduct2 = productRepository.searchById(sProductID2);
        assertNotNull(searchedProduct2);
        assertEquals(searchedProduct2.getProductId(),product2.getProductId());
        Product shouldBeNullAfterSearch = productRepository.searchById(wrongID);
        assertNull(shouldBeNullAfterSearch);
    }

    @Test
    public void removeByIdTest() {
        productRepository.create(mockProduct);

        Product product2 = new Product();
        String sProductID2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        product2.setProductId(sProductID2);
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        boolean removedSuccess = productRepository.removeById(sProductID2);
        assertTrue(removedSuccess);
        boolean removedShouldNotBeSuccess = productRepository.removeById("a1f9de46-90b1-411d-a0bf-d0821dde9096");
        assertFalse(removedShouldNotBeSuccess);
        assertNull(productRepository.searchById(sProductID2));
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
    }

    @Test
    public void WhenIDExists_ForEditByIDTest() {
        String sProductID1 = mockProduct.getProductId();
        productRepository.create(mockProduct);

        Product product2 = new Product();
        String sProductID2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        product2.setProductId(sProductID2);
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Product editResult = new Product();
        String newName = "Nama Bebas";
        int newQuantity = 242;
        editResult.setProductId(sProductID1);
        editResult.setProductName(newName);
        editResult.setProductQuantity(newQuantity);
        assertNotEquals(mockProduct.getProductName(),editResult.getProductName());
        assertNotEquals(mockProduct.getProductQuantity(),editResult.getProductQuantity());

        Product kr = productRepository.edit(sProductID1,editResult);
        assertNotNull(kr);
        assertEquals(editResult.getProductName(),kr.getProductName());
        assertEquals(editResult.getProductQuantity(),kr.getProductQuantity());
        Product checkedProduct = productRepository.searchById(sProductID1);
        assertEquals(sProductID1,checkedProduct.getProductId());
        assertEquals(newName, checkedProduct.getProductName());
        assertEquals(newQuantity,checkedProduct.getProductQuantity());
    }

    @Test
    public void whenIDDoesNotExist_ForEditByIDTest() {
        productRepository.create(mockProduct);

        Product product2 = new Product();
        String sProductID2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
        product2.setProductId(sProductID2);
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Product editResult = new Product();
        String newName = "Nama Bebas";
        int newQuantity = 242;
        editResult.setProductId("a0f9de46-90b1-157d-a0bf-d0821dde1196");
        editResult.setProductName(newName);
        editResult.setProductQuantity(newQuantity);
        assertNotEquals(mockProduct.getProductName(),editResult.getProductName());
        assertNotEquals(mockProduct.getProductQuantity(),editResult.getProductQuantity());
        Product kr = productRepository.edit("bc39de46-90b1-157d-a0bf-d0821dde1196",editResult);
        assertNull(kr);
    }


}
