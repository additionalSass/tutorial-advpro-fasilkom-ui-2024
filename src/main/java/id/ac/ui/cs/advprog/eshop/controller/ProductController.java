package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService service;

    @Autowired
    public ProductController(ProductService productService) {
        this.service = productService;
    }

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product",product);
        return "createproduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products",allProducts);
        return "productlist";
    }

    @GetMapping("/edit/{productID}")
    public String editProductPage(@PathVariable String productID, Model model) {
        Product product = service.searchByID(productID);
        if (product != null) {
            model.addAttribute("product",product);
            return "editproduct";
        } else {
            return "redirect:list";
        }
    }
    @GetMapping("/delete/{productID}")
    public String deleteProductPost(@PathVariable("productID") String productID, Model model) {
        service.removeByID(productID);
        return "redirect:/product/list";
    }

    @PostMapping("/edit/{productID}")
    public String editProductPost(@PathVariable("productID") String productID, @ModelAttribute Product product, Model model) {
        service.edit(productID, product);
        return "redirect:/product/list";
    }
}