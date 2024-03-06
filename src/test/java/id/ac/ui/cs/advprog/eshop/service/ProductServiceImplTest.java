package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testWhenNoIdGiven_Create() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(200);
        when(productRepository.create(any(Product.class))).thenReturn(product1);

        Product savedProduct = productService.create(product1);
        assertNotNull(savedProduct.getProductId());
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(200,savedProduct.getProductQuantity());
        verify(productRepository).create(product1);
    }

    @Test
    public void testWhenSomeIdGiven_Create() {
        Product product1 = new Product();
        String givenId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        product1.setProductId(givenId);
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(200);
        when(productRepository.create(any(Product.class))).thenReturn(product1);

        Product savedProduct = productService.create(product1);
        assertNotNull(savedProduct.getProductId());
        assertEquals(givenId,savedProduct.getProductId());
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(200,savedProduct.getProductQuantity());
        verify(productRepository).create(product1);
    }

    @Test
    public void testWhenFindAll_thenReturnProductRepo() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        List<Product> mockProducts = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(mockProducts.iterator());

        List<Product> result = productService.findAll();
        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    public void testSearchById() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);

        when(productRepository.searchById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product1);
        when(productRepository.searchById("a0f9de46-90b1-437d-a0bf-d0821dde9096")).thenReturn(product2);

        Product searchedProduct1 = productService.searchById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(product1.getProductId(), searchedProduct1.getProductId());
        assertEquals(product1.getProductName(), searchedProduct1.getProductName());
        assertEquals(product1.getProductQuantity(), searchedProduct1.getProductQuantity());
        verify(productRepository).searchById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        Product searchedProduct2 = productService.searchById("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        assertEquals(product2.getProductId(), searchedProduct2.getProductId());
        assertEquals(product2.getProductName(), searchedProduct2.getProductName());
        assertEquals(product2.getProductQuantity(), searchedProduct2.getProductQuantity());
        verify(productRepository).searchById("a0f9de46-90b1-437d-a0bf-d0821dde9096");
    }

    @Test
    public void testEditProduct() {
        Product oriProduct = new Product();
        oriProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        oriProduct.setProductName("Sampo Cap Bambang");
        oriProduct.setProductQuantity(100);

        Product editedProduct = new Product();
        editedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedProduct.setProductName("Sampo Cap Usep");
        editedProduct.setProductQuantity(50);

        when(productRepository.edit(oriProduct.getProductId(), editedProduct)).thenReturn(editedProduct);

        Product testedResult = productService.edit("eb558e9f-1c39-460e-8860-71af6af63bd6",editedProduct);

        assertEquals(editedProduct.getProductId(), testedResult.getProductId());
        assertEquals(editedProduct.getProductName(), testedResult.getProductName());
        assertEquals(editedProduct.getProductQuantity(), testedResult.getProductQuantity());
        verify(productRepository).edit("eb558e9f-1c39-460e-8860-71af6af63bd6",editedProduct);
    }

    @Test
    public void testRemoveByIdOfProduct() {
        when(productRepository.removeById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(true);
        boolean testedResult = productService.removeById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertTrue(testedResult);
        verify(productRepository).removeById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

}
