package com.task.elevator;

public record Passenger(int floorThatIWant) {

	public int getFloorThatIWant() {
		return floorThatIWant;
	}

}
