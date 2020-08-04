import java.util.concurrent.*;

public class Main {

	public static void main(String[] args) {
		Logger.InitLogger();
		Session s = SessionParser.parse();
		ExecutorService executor = Executors.newFixedThreadPool(s.users.size());

		for (User u : s.users) {
			executor.execute(u);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			System.out.print(s.grid);
		}

	}

}
