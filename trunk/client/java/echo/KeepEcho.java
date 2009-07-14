package echo;

import java.util.ArrayList;
import java.util.List;

public class HLClient {

	public static class StatThread extends Thread {
		@Override
		public void run() {
			while (true) {
				Stat.getInstance().clear();
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Stat.getInstance().collect("HL");
				Stat.getInstance().print();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 5) {
			System.out
					.println("Usage: ReplyClient <ip> <port> <size> <concurrent> <delay>");
			System.exit(1);
		}

		String ip = args[0];
		String port = args[1];
		Integer size = Integer.valueOf(args[2]);
		Integer concurrent = Integer.valueOf(args[3]);
		Integer delay = Integer.valueOf(args[4]);

		ThreadGroup group = new ThreadGroup("HLClient");
		List<Thread> threads = new ArrayList<Thread>();

		for (int x = threads.size(); x < concurrent; ++x) {

			Ice.Communicator ic = Factory.createCommunictor();
			EchoManagerPrx prx = Factory.createEchoManager(ic, ip, port);
			
			Thread t = new EchoThread(prx, x, size, delay, group);
			threads.add(t);
			t.setDaemon(true);
			t.start();
		}

		Thread st = new StatThread();
		st.start();
		try {
			st.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
