package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    Car car;

    @BeforeEach
    public void setUp() {
        this.car = new Car();
        this.car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.car.setCarName("Model X");
        this.car.setCarColor("Red");
        this.car.setCarQuantity(8);
    }

    @Test
    public void testGetCarId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.car.getCarId());
    }

    @Test
    public void testGetCarName() {
        assertEquals("Model X", this.car.getCarName());
    }

    @Test
    public void testGetCarColor() {
        assertEquals("Red", this.car.getCarColor());
    }

    @Test
    public void testGetCarQuantity() {
        assertEquals(8, this.car.getCarQuantity());
    }
}
