import java.util.ArrayList;

public class SeatGrid {
	private final ArrayList<ArrayList<Seat>> seats;
	public SeatGrid(int rowCount, int colCount) {
		this.seats = new ArrayList<>();
		for (int i=0 ; i < rowCount; i++) {
			ArrayList<Seat> newRow = new ArrayList<>();
			for (int j=0 ; j< colCount ; j++) {
				newRow.add(new Seat());
			}
			this.seats.add(newRow);
		}
		
		
	}
	public boolean isSeatBooked(String seat) {
		return getSeat(seat).isBooked();
	}
	
	
	public ReservationResult reserveSeat(String seat, String name) {
		
		return getSeat(seat).reserve(name);
	}
	
	public void bookSeat(String seat, String name) {
		
		 getSeat(seat).book(name);
	}
	
	public void notifyLogout( String seat, String name) {
		getSeat(seat).removeIfOwner(name);
	}
	
	private Seat getSeat(String seat) {
		char rowChar = seat.charAt(0);
		char colChar = seat.charAt(1);
		int row = rowChar - 'A';
		int col = colChar - '0';
		return this.seats.get(row).get(col);
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		for (ArrayList<Seat> row : seats) {
			for (Seat s: row) {
				char seatStatus= s.isBooked()? 'T' : 'E'; 
				String reserver = s.getReserverName()==null? "":s.getReserverName();
				out.append(seatStatus+":"+ reserver + " ");
			}
			out.append(System.getProperty("line.separator"));
		}
		return out.toString();
		
	}
}
