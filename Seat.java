
public class Seat {

	private SeatStatus seatStatus = SeatStatus.FREE;
	private String reserverName = null;

	public synchronized ReservationResult reserve(String name) {
		if (this.seatStatus == SeatStatus.RESERVED && !this.reserverName.equals(name)) {
			return ReservationResult.ALREADY_RESERVED;
		} else if (this.seatStatus == SeatStatus.BOOKED && !this.reserverName.equals(name)) {
			return ReservationResult.ALREADY_BOOKED;
		} else if (this.seatStatus == SeatStatus.FREE) {
			this.seatStatus = SeatStatus.RESERVED;
			this.reserverName = name;
			return ReservationResult.SUCCESS;
		}

		else {
			throw new RuntimeException();
		}
	};

	public synchronized void book(String name) {
		if (this.seatStatus == SeatStatus.RESERVED && this.reserverName.equals(name)) {
				this.seatStatus = SeatStatus.BOOKED;	
			}
		else{
			throw new RuntimeException();
			
		}
	}

	public synchronized boolean isBooked() {
		return this.seatStatus == SeatStatus.BOOKED;
	}

	public synchronized String getReserverName() {
		return this.reserverName;
	}

	public synchronized void removeIfOwner(String name) {
		if (this.reserverName!= null && this.reserverName.equals(name)) {
			this.seatStatus = SeatStatus.FREE;
			this.reserverName = null;
		}
	}

}
