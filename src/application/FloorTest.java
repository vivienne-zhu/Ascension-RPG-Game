package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This test the methods of the floor class
 * 
 * @author JiayuZhu
 *
 */
class FloorTest {

    	/**
    	 * This test the increment floor method
    	 */
	@Test
	void testIncrementFloor() {
		Floor floor = new Floor();
		floor.setFloor(8);
		floor.incrementFloor();
		
		assertEquals(9, floor.getFloor());
	}

	/**
	 * This test the decrement floor method 
	 */
	@Test
	void testDecrementFloor() {
		Floor floor = new Floor();
		floor.setFloor(8);
		floor.decrementFloor();
		
		assertEquals(7, floor.getFloor());
	}

}
