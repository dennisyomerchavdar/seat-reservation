import java.util.ArrayList;
import java.util.Scanner;

public class SessionParser {
	public static Session parse() {
		try (Scanner inputScanner = new Scanner(System.in)) {
			int rowCount;
			int colCount;
			try (Scanner gridSizeScanner = new Scanner(inputScanner.nextLine())) {
				rowCount = gridSizeScanner.nextInt();
				colCount = gridSizeScanner.nextInt();
			}
			SeatGrid grid = new SeatGrid(rowCount, colCount);
			int userCount;
			try (Scanner userCountScanner = new Scanner(inputScanner.nextLine())) {
				userCount = userCountScanner.nextInt();
			}
			ArrayList<User> users = new ArrayList<>();
			for (int i = 0; i < userCount; i++) {
				String name;
				ArrayList<String> wantedSeats = new ArrayList<>(); 
				try (Scanner userScanner = new Scanner(inputScanner.nextLine())) {
					name= userScanner.next();
					while(userScanner.hasNext()) {
						wantedSeats.add(userScanner.next());
					}
				}
				users.add(new User(name, wantedSeats, grid));
			}
			
			return new Session(users, grid);

		}

	}
}
