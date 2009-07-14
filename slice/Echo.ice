
module echo {
	sequence<byte> Bytes;
	
	interface EchoManager {
		["ami"] Bytes echo(int count, int delay);
	};
};

