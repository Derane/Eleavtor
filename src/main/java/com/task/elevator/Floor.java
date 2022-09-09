package com.task.elevator;

import java.util.ArrayList;
import java.util.List;

public class Floor {
	private final int number;
	private final List<Passenger> arrivedToThisFloat = new ArrayList<>();
	private final List<Passenger> waitingPassengers = new ArrayList<>();

	public List<Passenger> getArrivedToThisFloat() {
		return arrivedToThisFloat;
	}

	public Floor(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public List<Passenger> getWaitingPassengers() {
		return waitingPassengers;
	}

}
