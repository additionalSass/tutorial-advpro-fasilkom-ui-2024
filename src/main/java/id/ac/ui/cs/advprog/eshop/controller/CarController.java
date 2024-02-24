package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    private static final String REDIRECT_TO_LIST_CAR = "redirect:listcar";
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/createcar")
    public String createCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "createcar";
    }

    @PostMapping("/createcar")
    public String createCar(@ModelAttribute Car car) {
        carService.create(car);
        return REDIRECT_TO_LIST_CAR;
    }

    @GetMapping("/listcar")
    public String carList(Model model) {
        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);
        return "carlist";
    }

    @GetMapping("/editcar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carService.findById(carId);
        if (car != null) {
            model.addAttribute("car",car);
            return "editcar";
        } else {
            return REDIRECT_TO_LIST_CAR;
        }
    }

    @PostMapping("/editcar")
    public String editCar(@ModelAttribute Car car) {
        carService.update(car.getCarId(), car);
        return REDIRECT_TO_LIST_CAR;
    }

    @PostMapping("/deletecar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carService.deleteCarById(carId);
        return REDIRECT_TO_LIST_CAR;
    }
}