
package application;
/**
 * @author JiayuZhu
 *
 * This is the floor class
 */

public class Floor {
	private int floor = 1;
	
	/**
	 * This is the constructor for the floor class.
	 * A new floor object is on the first floor.
	 */
	public Floor() {
		this.floor = 1;
	}
	/**
	 * This method increment the floor by 1
	 */
	public void incrementFloor() {
		this.floor++;
	}
	
	/**
	 * This method decrement the floor by 1 
	 */
	public void decrementFloor() {
		this.floor--;
	}

	/**
	 * @return the floor
	 */
	public int getFloor() {
		return this.floor;
	}

	/**
	 * @param floor the floor to set
	 */
	
	public void setFloor(int floor) {
		this.floor = floor;
	}
	/**
	 * This method prints out a message, "Floor " + floor
	 */
	@Override
	public String toString() {
		return "Floor " + floor;
	}
}
