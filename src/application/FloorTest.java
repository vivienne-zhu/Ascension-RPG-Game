package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FloorTest {

	@Test
	void testIncrementFloor() {
		Floor floor = new Floor();
		floor.setFloor(8);
		floor.incrementFloor();
		
		assertEquals(floor.getFloor(), 9);
	}

	@Test
	void testDecrementFloor() {
		Floor floor = new Floor();
		floor.setFloor(8);
		floor.decrementFloor();
		
		assertEquals(floor.getFloor(), 7);
	}

}
