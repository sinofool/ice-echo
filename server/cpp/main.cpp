#include <iostream>
#include "EchoI.h"
#include <Ice/Service.h>

class EchoSvc : virtual public Ice::Service {
public:

virtual bool start(int, char*[]) {
	_adapter = communicator()->createObjectAdapter("EchoSvc");
	_adapter1 = communicator()->createObjectAdapter("EchoSvc1");
	_adapter2 = communicator()->createObjectAdapter("EchoSvc2");
	_adapter3 = communicator()->createObjectAdapter("EchoSvc3");
	_adapter4 = communicator()->createObjectAdapter("EchoSvc4");

	_manager = new ::echo::EchoManagerI(_deter);
	_printer = new ::echo::DeterPrinter(_deter);
	_printer->start().detach();

	_adapter->add(_manager, communicator()->stringToIdentity("M"));
	_adapter1->add(_manager, communicator()->stringToIdentity("M"));
	_adapter2->add(_manager, communicator()->stringToIdentity("M"));
	_adapter3->add(_manager, communicator()->stringToIdentity("M"));
	_adapter4->add(_manager, communicator()->stringToIdentity("M"));

	_adapter->activate();
	_adapter1->activate();
	_adapter2->activate();
	_adapter3->activate();
	_adapter4->activate();
	return true;
};

private:
	Ice::ObjectAdapterPtr _adapter;
	Ice::ObjectAdapterPtr _adapter1;
	Ice::ObjectAdapterPtr _adapter2;
	Ice::ObjectAdapterPtr _adapter3;
	Ice::ObjectAdapterPtr _adapter4;
	::echo::Deter _deter;
	::echo::EchoManagerI* _manager;
	::echo::DeterPrinter* _printer;
};

int main(int argc, char* argv[]){
	EchoSvc app;
	app.main(argc, argv);
}
