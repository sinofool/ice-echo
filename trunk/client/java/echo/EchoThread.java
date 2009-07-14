package echo;

import Ice.LocalException;

public class EchoThread extends Thread {

	private static class AMI_EchoManager_echoI extends AMI_EchoManager_echo {
		private static AMI_EchoManager_echoI _instance = new AMI_EchoManager_echoI();

		public static AMI_EchoManager_echoI getInstance() {
			return _instance;
		}

		private AMI_EchoManager_echoI() {

		}

		@Override
		public void ice_exception(LocalException ex) {

		}

		@Override
		public void ice_response(byte[] __ret) {
			Stat.getInstance().inc();
		}

	}

	private final EchoManagerPrx _prx;
	private final int _size;
	private final int _delay;

	public EchoThread(EchoManagerPrx prx, int in, int size, int delay,
			ThreadGroup group) {
		super(group, "EchoThread-" + in);
		// _prx = prx;
		_prx = EchoManagerPrxHelper.uncheckedCast(prx
				.ice_connectionId("EchoConnection-" + in));
		_size = size;
		_delay = delay;
	}

	@Override
	public void run() {
		while (true) {
			try {
				_prx.echo(_size, _delay);
				
				Stat.getInstance().inc();
				// _prx.echo_async(AMI_EchoManager_echoI.getInstance(), _size,
				
				// _delay);
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
			}
		}
	}
}