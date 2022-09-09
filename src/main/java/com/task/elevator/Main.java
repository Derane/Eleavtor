package com.task.elevator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

	private static final int MAX_PASSENGER_ON_FLOOR = 10;
	private static final Elevator elevator = new Elevator();

	public static void main(String[] args) {
		init();
		for (int i = 0; i < elevator.getFloorList().size(); i++) {
			if (actionWithElevatorAndIsThisFloatSkipped(i)) continue;
			if (elevator.getCurrentFloor() == elevator.getMaxFloor()) {
				elevator.setGoingToTheTop(false);
				break;
			}
		}
		for (int i = elevator.getCurrentFloor() - 1; i >= 0; i--) {
			actionWithElevatorAndIsThisFloatSkipped(i);
		}

	}

	private static void init() {
		for (int i = 0; i < elevator.getNUMBER_OF_FLOORS(); i++) {
			int number = i + 1;
			elevator.getFloorList().add(new Floor(number));
		}
		elevator.getFloorList().forEach(floor -> {
			for (int j = 0; j < ThreadLocalRandom.current().nextInt(1, MAX_PASSENGER_ON_FLOOR + 1); j++) {
				floor.getWaitingPassengers()
						.add(new Passenger((Main.randomWantFloor(floor.getNumber()))));
			}
		});
	}

	private static boolean actionWithElevatorAndIsThisFloatSkipped(int i) {
		driveCurrentSituation();
		Floor currentFloor = elevator.getFloorList().get(i);
		elevator.setCurrentFloor(currentFloor.getNumber());
		if (isThisFloorSkip(currentFloor)) {
			return true;
		}
		elevator.removePlayersThatComeOut();
		List<Passenger> passengers = goToTheElevatorAllPassengers(currentFloor);
		elevator.getPassengers().addAll(passengers);
		elevator.getFloorList()
				.get(i)
				.getWaitingPassengers()
				.removeAll(passengers);
		elevator.setMaxFloor(getMaxFloor());
		return false;
	}

	private static void driveCurrentSituation() {
		System.out.println("===============================================");
		String stringWhereElevatorGoing;
		if (elevator.isGoingToTheTop()) {
			stringWhereElevatorGoing = "going to the top";
		} else {
			stringWhereElevatorGoing = "going to the bot";
		}
		for (int i = 0; i < elevator.getFloorList().size(); i++) {

			System.out.printf("On %d float is %d waiting passengers they want to  " + elevator.getFloorList()
							.get(i)
							.getWaitingPassengers()
							.stream()
							.map(Passenger::getFloorThatIWant)
							.toList() +
							" and " + elevator.getFloorList().get(i)
							.getArrivedToThisFloat().stream().map(Passenger::getFloorThatIWant).toList()
							+ " passengers arrived to this floor %n",
					elevator.getFloorList().get(i).getNumber(),
					elevator.getFloorList().get(i).getWaitingPassengers().size());

		}
		System.out.printf("Lift on %d floor and %s and have %d passengers that want to such floors " +
						elevator.getPassengers().stream().map(Passenger::getFloorThatIWant).toList() + "%n",
				elevator.getCurrentFloor(), stringWhereElevatorGoing, elevator.getPassengers().size());
		System.out.println("===============================================");
	}

	private static List<Passenger> goToTheElevatorAllPassengers(Floor currentFloor) {
		return currentFloor.getWaitingPassengers()
				.stream()
				.filter(passenger -> isPassengerWantToEnterInElevator(passenger, currentFloor, elevator.isGoingToTheTop()))
				.limit(elevator.getCAPACITY() - elevator.getPassengers().size())
				.toList();
	}

	private static boolean isPassengerWantToEnterInElevator(Passenger passenger, Floor floor, boolean isGoingToTheTop) {
		if (isGoingToTheTop)
			return passenger.getFloorThatIWant() > floor.getNumber();
		return passenger.getFloorThatIWant() < floor.getNumber();
	}

	private static Integer getMaxFloor() {
		return elevator.getPassengers()
				.stream()
				.map(Passenger::getFloorThatIWant)
				.max(Integer::compareTo)
				.orElse(elevator.getMaxFloor());
	}

	private static boolean isThisFloorSkip(Floor currentFloor) {
		return elevator.getPassengers().size() == elevator.getCAPACITY() && elevator.getPassengers()
				.stream()
				.noneMatch(passenger -> passenger.getFloorThatIWant() == currentFloor.getNumber());
	}

	private static int randomWantFloor(int number) {
		int floorThatPassengerWant;
		do {
			floorThatPassengerWant =
					ThreadLocalRandom.current().nextInt(1, elevator.getNUMBER_OF_FLOORS() + 1);
		} while (number == floorThatPassengerWant);
		return floorThatPassengerWant;
	}
}

