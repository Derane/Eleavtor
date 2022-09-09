package com.task.elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Elevator {
	private final int NUMBER_OF_FLOORS = ThreadLocalRandom.current().nextInt(5, 21);

	private int currentFloor = 1;

	private int maxFloor;

	private boolean goingToTheTop = true;

	private final List<Floor> floorList = new ArrayList<>();

	private final List<Passenger> passengers = new ArrayList<>();

	public int getMaxFloor() {
		return maxFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public boolean isGoingToTheTop() {
		return goingToTheTop;
	}

	public void setMaxFloor(int maxFloor) {
		this.maxFloor = maxFloor;
	}

	public List<Floor> getFloorList() {
		return floorList;
	}

	public int getNUMBER_OF_FLOORS() {
		return NUMBER_OF_FLOORS;
	}

	public int getCAPACITY() {
		return 5;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setGoingToTheTop(boolean goingToTheTop) {
		this.goingToTheTop = goingToTheTop;
	}

	public void removePlayersThatComeOut() {
		List<Passenger> passengers = this.passengers.stream()
				.filter(passenger -> passenger.getFloorThatIWant() == this.currentFloor)
				.toList();
		this.passengers.removeAll(passengers);
		this.floorList.get(this.currentFloor - 1).getArrivedToThisFloat().addAll(passengers);
	}
}
