package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockController;

    @MockBean
    private ProductService productService;

    private Product mockProduct;
    private List<Product> productList;

    @BeforeEach
    public void setUp() {
        mockProduct = new Product();
        mockProduct.setProductId("a0f9de46-90b1-157d-a0bf-d0821dde1196");
        mockProduct.setProductName("Test Product");
        mockProduct.setProductQuantity(29);

        productList = new ArrayList<>();
        productList.add(mockProduct);
    }

    @Test
    public void whenCreateProductPage_thenReturnsCreateProductView() throws Exception {
        mockController.perform(get("/product/create")).andExpect(status().isOk()).andExpect(model().attributeExists("product")).andExpect(view().name("createproduct"));
    }

    @Test
    public void whenCreateProductPost_thenProductIsCreated() throws Exception {
        mockController.perform(post("/product/create").flashAttr("product", mockProduct)).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:list"));
        verify(productService).create(any(Product.class));
    }

    @Test
    public void whenProductListPage_thenReturnsListViewWithProducts() throws Exception {
        given(productService.findAll()).willReturn(productList);
        mockController.perform(get("/product/list")).andExpect(status().isOk()).andExpect(model().attribute("products", productList)).andExpect(view().name("productlist"));
    }

    @Test
    public void whenEditProductPageWithValidId_thenReturnsEditProductView() throws Exception {
        given(productService.searchById(mockProduct.getProductId())).willReturn(mockProduct);
        mockController.perform(get("/product/edit/{productId}", mockProduct.getProductId())).andExpect(status().isOk()).andExpect(model().attributeExists("product")).andExpect(view().name("editproduct"));
    }

    @Test
    public void whenEditProductPageWithInvalidId_thenRedirectsToProductList() throws Exception {
        given(productService.searchById("invalid-id")).willReturn(null);
        mockController.perform(get("/product/edit/{productId}", "invalid-id")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:list"));
    }

    @Test
    public void whenEditProductPost_thenProductIsUpdated() throws Exception {
        mockController.perform(post("/product/edit/{productID}", mockProduct.getProductId()).flashAttr("product", mockProduct)).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/product/list"));
        verify(productService).edit(eq(mockProduct.getProductId()), any(Product.class));
    }

    @Test
    public void whenDeleteProduct_thenProductIsRemoved() throws Exception {
        mockController.perform(get("/product/delete/{productID}", mockProduct.getProductId())).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/product/list"));
        verify(productService).removeById(mockProduct.getProductId());
    }
}
