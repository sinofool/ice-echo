
#include <EchoI.h>

::echo::Bytes
echo::EchoManagerI::echo(::Ice::Int count,
                         ::Ice::Int delay,
                         const Ice::Current& current)
{
    ::echo::Bytes ret;
    ret.resize(count);
    if(delay>0)usleep(1000*delay);
    _deter.inc(1);
    return ret;
}
