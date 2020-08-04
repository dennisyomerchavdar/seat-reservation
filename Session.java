import java.util.ArrayList;

public class Session {
	public final ArrayList<User> users;
	public final SeatGrid grid;

	Session(ArrayList<User> users, SeatGrid grid) {
		this.users=users;
		this.grid=grid;
	}
}
