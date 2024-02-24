package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockController;

    @MockBean
    private CarService carService;

    private Car mockCar;
    private List<Car> carList;

    @BeforeEach
    public void setUp() {
        mockCar = new Car();
        mockCar.setCarId("a0f9de46-90b1-157d-a0bf-d0821dde1196");
        mockCar.setCarName("Test Car");
        mockCar.setCarColor("Red");
        mockCar.setCarQuantity(1);
        carList = new ArrayList<>();
        carList.add(mockCar);
    }

    @Test
    public void whenCreateCarPage_thenReturnsCreateCarView() throws Exception {
        mockController.perform(get("/car/createcar")).andExpect(status().isOk()).andExpect(model().attributeExists("car")).andExpect(view().name("createcar"));
    }

    @Test
    public void whenCreateCarPost_thenCarIsCreated() throws Exception {
        mockController.perform(post("/car/createcar").flashAttr("car", mockCar)).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:listcar"));
        verify(carService).create(any(Car.class));
    }

    @Test
    public void whenCarListPage_thenReturnsListViewWithCars() throws Exception {
        given(carService.findAll()).willReturn(carList);
        mockController.perform(get("/car/listcar")).andExpect(status().isOk()).andExpect(model().attribute("cars", carList)).andExpect(view().name("carlist"));
    }

    @Test
    public void whenEditCarPageWithValidId_thenReturnsEditCarView() throws Exception {
        given(carService.findById(mockCar.getCarId())).willReturn(mockCar);
        mockController.perform(get("/car/editcar/{carId}", mockCar.getCarId())).andExpect(status().isOk()).andExpect(model().attributeExists("car")).andExpect(view().name("editcar"));
    }

    @Test
    public void whenEditCarPageWithInvalidId_thenRedirectsToCarList() throws Exception {
        given(carService.findById("invalid-id")).willReturn(null);
        mockController.perform(get("/car/editcar/{carId}", "invalid-id")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:listcar"));
    }

    @Test
    public void whenEditCarPost_thenCarIsUpdated() throws Exception {
        mockController.perform(post("/car/editcar").flashAttr("car", mockCar)).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:listcar"));
        verify(carService).update(eq(mockCar.getCarId()), any(Car.class));
    }

    @Test
    public void whenDeleteProductPost_thenProductIsRemoved() throws Exception {
        mockController.perform(post("/car/deletecar").param("carId", mockCar.getCarId())).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:listcar"));
        verify(carService).deleteCarById(mockCar.getCarId());
    }
}