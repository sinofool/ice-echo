#ifndef __EchoI_h__
#define __EchoI_h__

#include <Echo.h>
#include <IceUtil/IceUtil.h>

namespace echo
{
class Deter {
public:
	Deter():_count(0), _last(0){};
	void inc(int det){
		IceUtil::Mutex::Lock lock(_mutex);
		_count++;
	};
	int det(){
		IceUtil::Mutex::Lock lock(_mutex);
		int det = _count - _last;
		_last = _count;
		return det;
	};
private:
	IceUtil::Mutex _mutex;
	int _count;
	int _last;
};

class DeterPrinter : virtual public IceUtil::Thread {
public:
	DeterPrinter(Deter& deter) : _deter(deter){};
	virtual void run(){
		while(true){
			int det = _deter.det();
			if (det > 0) std::cout << det << std::endl;
			sleep(1);
		}
	}
private:
    Deter& _deter;
};

class EchoManagerI : virtual public EchoManager
{
public:
    EchoManagerI(Deter& deter) : _deter(deter){};
    virtual ::echo::Bytes echo(::Ice::Int,
                               ::Ice::Int,
                               const Ice::Current&);
private:
    Deter& _deter;
};

}

#endif
