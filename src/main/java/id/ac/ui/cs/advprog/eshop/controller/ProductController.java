package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

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

@Controller
@RequestMapping("/car")
class CarController extends ProductController{

    @Autowired
    private CarServiceImpl carservice;

    @GetMapping("/createcar")
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createcar";
    }

    @PostMapping("/createcar")
    public String createCarPost(@ModelAttribute Car car, Model model){
        carservice.create(car);
        return "redirect:listcar";
    }

    @GetMapping("/listcar")
    public String carListPage(Model model){
        List<Car> allCars = carservice.findAll();
        model.addAttribute("cars", allCars);
        return "carlist";
    }

    @GetMapping("/editcar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carservice.findById(carId);
        model.addAttribute("car", car);
        return "editcar";
    }

    @PostMapping("/editcar")
    public String editCarPost(@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarId());
        carservice.update(car.getCarId(), car);
        return "redirect:listcar";
    }

    @PostMapping("/deletecar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carservice.deleteCarById(carId);
        return "redirect:listcar";
    }

}

