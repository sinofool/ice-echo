#include <iostream>
#include "EchoI.h"
#include <Ice/Service.h>

class EchoSvc : virtual public Ice::Service {
public:

virtual bool start(int, char*[]) {
	_adapter = communicator()->createObjectAdapter("EchoSvc");

	_manager = new ::echo::EchoManagerI(_deter);
	_printer = new ::echo::DeterPrinter(_deter);
	_printer->start().detach();
	
	_adapter->add(_manager, communicator()->stringToIdentity("M"));
	
	_adapter->activate();
	return true;	
};

private:
	Ice::ObjectAdapterPtr _adapter;
	::echo::Deter _deter;
	::echo::EchoManagerI* _manager;
	::echo::DeterPrinter* _printer;
};

int main(int argc, char* argv[]){
	EchoSvc app;
	app.main(argc, argv);
}
