import java.util.ArrayList;

public class User implements Runnable {
	private final String name;
	private final ArrayList<String> wantedSeats;
	private final String wantedSeatsOutput; //Directly written for time accuracy, computing everytime would be bad.
	private final SeatGrid grid;

	public User(String name, ArrayList<String> wantedSeats, SeatGrid grid) {
		this.name = name;
		this.wantedSeats = wantedSeats;
		this.grid = grid;
		this.wantedSeatsOutput= stringifyWantedSeats();
	}

	@Override
	public void run() {
		while (true) {
			if (!canBookAllSeats()) { //Check if one of wanted seats is booked. Note that being booked is next step of being reserved.
				Logger.LogFailedReservation(this.name, this.wantedSeatsOutput, System.nanoTime());
				break;
			} else if (isDatabaseFailure()) {
				Logger.LogDatabaseFailiure(this.name, this.wantedSeatsOutput, System.nanoTime());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				if (reserveAllSeats()) { //reserve them, and return true if reservation is successful.
					Logger.LogSuccessfulReservation(this.name, this.wantedSeatsOutput, System.nanoTime());
					bookAllSeats(); // now book them, since we reserved them successfully we can book them.
					break; // the user is done booking. Leave.

				} else {
					logout(); // remove all reservations and start over, since we couldnt reserve all of them at this time.
					// This has to continue looping because, it is already reserved but it does not mean it is booked.
					// The current owner of the wanted reservation might logout too. so keep looping until they are booked. 

				}
			}
		}
	}

	private void logout() {
		for (String seat : this.wantedSeats) {
			this.grid.notifyLogout(seat, this.name);
		}
	}

	private static boolean isDatabaseFailure() {
		return Math.random() < 0.1;
	}

	private boolean reserveAllSeats() {
		boolean successfulReservations = true;
		for (String seat : this.wantedSeats) {
			if (this.grid.reserveSeat(seat, this.name) != ReservationResult.SUCCESS) {
				successfulReservations = false;
			}
		}
		return successfulReservations;

	}

	private void bookAllSeats() {
		for (String seat : this.wantedSeats) {
			this.grid.bookSeat(seat, this.name);
		}
	}

	private boolean canBookAllSeats() {
		boolean canBookAllSeats = true;
		for (String seat : this.wantedSeats) {
			if (this.grid.isSeatBooked(seat))
				canBookAllSeats = false;
		}

		return canBookAllSeats;

	}

	private String stringifyWantedSeats() {
		StringBuilder wantedSeatsStringBuilder = new StringBuilder("[");
		for (int i = 0; i < this.wantedSeats.size(); i++) {
			if (i != 0) {
				wantedSeatsStringBuilder.append(", ");
			}
			wantedSeatsStringBuilder.append(this.wantedSeats.get(i));
		}
		wantedSeatsStringBuilder.append("]");
		return wantedSeatsStringBuilder.toString();

	}
}
