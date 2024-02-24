package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    @InjectMocks
    CarRepository carRepository;

    Car mockCar;

    @BeforeEach
    public void setUp() {
        mockCar = new Car();
        mockCar.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        mockCar.setCarName("Test Car");
        mockCar.setCarColor("Blue");
        mockCar.setCarQuantity(2);
    }

    @Test
    public void testCreateANewCarNoId() {
        Car creation = carRepository.create(new Car());
        assertNotNull(creation.getCarId());
    }

    @Test
    public void testCreateANewCarWithExistingId() {
        Car creation = carRepository.create(mockCar);
        assertNotNull(creation.getCarId());
        assertEquals(mockCar.getCarId(),creation.getCarId());
    }

    @Test
    public void testCreateAndFind() {
        Car creation = carRepository.create(mockCar);
        assertNotNull(creation);
        assertEquals(mockCar.getCarId(),creation.getCarId());
        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(mockCar.getCarId(),savedCar.getCarId());
        assertEquals(mockCar.getCarName(),savedCar.getCarName());
        assertEquals(mockCar.getCarColor(),savedCar.getCarColor());
        assertEquals(mockCar.getCarQuantity(),savedCar.getCarQuantity());
    }

    @Test
    public void testFindAll_IfEmptyCars() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    public void testFindAllCars() {
        carRepository.create(mockCar);
        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
    }

    @Test
    void testFindById_IfCarExists() {
        carRepository.create(mockCar);
        Car foundCar = carRepository.findById(mockCar.getCarId());
        assertNotNull(foundCar);
        assertEquals(mockCar.getCarId(), foundCar.getCarId());
    }

    @Test
    void testFindById_IfCarNotExists() {
        Car foundCar = carRepository.findById("invalid-id-test");
        assertNull(foundCar);
    }

    @Test
    void testUpdate_IfCarExists() {
        carRepository.create(mockCar);
        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Test Car");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(3);
        Car result = carRepository.update(mockCar.getCarId(), updatedCar);

        assertNotNull(result);
        assertEquals("Updated Test Car", result.getCarName());
        assertEquals("Green", result.getCarColor());
        assertEquals(3, result.getCarQuantity());
    }

    @Test
    void testUpdate_IfCarNotExists() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Test Car");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(3);
        Car result = carRepository.update("non-existing-id", updatedCar);
        assertNull(result);
    }

    @Test
    void testDelete_IfCarExists() {
        carRepository.create(mockCar);
        carRepository.delete(mockCar.getCarId());
        assertNull(carRepository.findById(mockCar.getCarId()));
    }

}
