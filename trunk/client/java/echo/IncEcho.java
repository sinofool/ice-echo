package echo;

import java.util.ArrayList;
import java.util.List;

public class EchoClient {

	private static final long BASIC_INTERVAL = 5000L;
	private static final long COLLECT_INTERVAL = 10L * 1000L;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		if (args.length != 4) {
			System.out.println("Usage: ReplyClient <ip> <port> <size> <delay>");
			System.exit(1);
		}

		String ip = args[0];
		String port = args[1];
		Integer size = Integer.valueOf(args[2]);
		Integer delay = Integer.valueOf(args[3]);

		ThreadGroup group = new ThreadGroup("EchoClient");
		List<Thread> threads = new ArrayList<Thread>();

		// for (int concurrent : new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20,
		// 30, 40, 50, 60, 70, 80, 90, 100, 150, 200, 250, 300, 350, 400,
		// 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000 }) {
		// for (int concurrent : new int[] { 1, 5, 10, 50, 100, 300 }) {
		for (int concurrent : new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20,
				30, 40, 50, 100, 300, 500, 1000 }) {
			System.out.println("Concurrent: " + concurrent);
			for (int x = threads.size(); x < concurrent; ++x) {

				Ice.Communicator ic = Factory.createCommunictor();
				EchoManagerPrx prx = Factory.createEchoManager(ic, ip, port);

				Thread t = new EchoThread(prx, x, size, delay, group);
				threads.add(t);
				t.setDaemon(true);
				t.start();
			}
			Thread.sleep(BASIC_INTERVAL);
			Stat.getInstance().clear();
			Thread.sleep(COLLECT_INTERVAL);
			Stat.getInstance().collect(String.valueOf(concurrent));
		}
		Stat.getInstance().print();
		System.exit(0);
	}

}
