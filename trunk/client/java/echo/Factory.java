package echo;

import Ice.InitializationData;

public class Factory {
	public static Ice.Communicator createCommunictor() {
		Ice.InitializationData initData = new InitializationData();
		initData.properties = Ice.Util.createProperties();
		initData.properties.setProperty("Ice.ThreadPool.Client.Size", "1");
		initData.properties
				.setProperty("Ice.ThreadPool.Client.SizeMax", "1300");
		initData.properties.setProperty("Ice.ThreadPool.Client.StackSize",
				"102400");
		initData.properties.setProperty("Ice.ThreadPool.Server.Size", "1");
		initData.properties
				.setProperty("Ice.ThreadPool.Server.SizeMax", "1300");
		initData.properties.setProperty("Ice.ThreadPool.Server.StackSize",
				"102400");
		return Ice.Util.initialize(initData);
	}

	public static echo.EchoManagerPrx createEchoManager(Ice.Communicator ic,
			String ip, String port) {
		return EchoManagerPrxHelper.uncheckedCast(ic
				.stringToProxy("M:default -h " + ip + " -p " + port));
	}
}
