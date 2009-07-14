
#include <Echo.h>
#include <boost/lexical_cast.hpp>
#include <Ice/Ice.h>

using echo::EchoManagerPrx;
class HLThread : virtual public IceUtil::Thread {
private:
        EchoManagerPrx _prx;
        int _size;
        int _delay;
public:
        HLThread(EchoManagerPrx prx, int in, int size, int delay) :
		_size(size), _delay(delay)  {
                _prx = EchoManagerPrx::uncheckedCast(prx
                                ->ice_connectionId("EchoConnection-" + in));
        }

        virtual void run() {
                while (true) {
                        try {
                                _prx->echo(_size, _delay);
                        } catch (...) {
                        }
                }
        }
};

int main(int argc, char* argv[]){
	if (argc != 6) {
		std::cout << "Usage: " << argv[0] << " <host> <port> <size> <delay> <concurrent>" << std::endl;
		return 1;
	}

	std::string host(argv[1]);
	std::string port(argv[2]);
	int size = boost::lexical_cast<int>(argv[3]);
	int delay = boost::lexical_cast<int>(argv[4]);
	int concurrent = boost::lexical_cast<int>(argv[5]);

	Ice::InitializationData initData;
	Ice::CommunicatorPtr ic = Ice::initialize(initData);
	EchoManagerPrx prx = EchoManagerPrx::uncheckedCast(ic->stringToProxy("M:default -h " + host + " -p " + port));
	std::vector<IceUtil::ThreadPtr> threads;
	for(int i=0;i<concurrent;++i){
		IceUtil::ThreadPtr thread = new HLThread(prx, i, size, delay);
		thread->start().detach();
		threads.push_back(thread);
	}

	ic->waitForShutdown();
	return 0;
}
