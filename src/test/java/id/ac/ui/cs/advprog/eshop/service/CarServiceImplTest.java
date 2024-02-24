package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepoInterface;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepoInterface carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car testCar;

    @BeforeEach
    public void setUp() {
        testCar = new Car();
        testCar.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        testCar.setCarName("Test Car");
        testCar.setCarColor("Blue");
        testCar.setCarQuantity(2);
    }

    @Test
    public void testCreateCar() {
        when(carRepository.create(any(Car.class))).thenReturn(testCar);
        Car createdCar = carService.create(testCar);
        assertNotNull(createdCar);
        assertEquals(testCar.getCarId(), createdCar.getCarId());
        verify(carRepository,times(1)).create(testCar);
    }

    @Test
    public void testFindAllCars() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(testCar).iterator());
        List<Car> cars = carService.findAll();
        assertNotNull(cars);
        assertEquals(1, cars.size());
        assertEquals(testCar.getCarId(), cars.get(0).getCarId());
        verify(carRepository,times(1)).findAll();
    }

    @Test
    public void testFindCarById() {
        when(carRepository.findById(anyString())).thenReturn(testCar);
        Car foundCar = carService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(foundCar);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", foundCar.getCarId());
        verify(carRepository, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    public void testUpdateCar() {
        Car updatedCar = new Car();
        updatedCar.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedCar.setCarName("Updated Test Car");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(12);
        carService.update("eb558e9f-1c39-460e-8860-71af6af63bd6", updatedCar);

        // Verify that the repository's update method was called once with the correct parameters
        verify(carRepository, times(1)).update("eb558e9f-1c39-460e-8860-71af6af63bd6", updatedCar);
        }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        // Verify that the repository's update method was called once with the correct parameters
        verify(carRepository, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }
}