#ifndef __UTIL_H__
#define __UTIL_H__

#include <string>
#include <stdexcept>
#include <chrono>
#include <thread>

// funções auxiliares

inline void throwif(bool cond, const std::string& msg)
{
	if (cond)
		throw std::runtime_error{msg};
}

inline void fix(std::string& id)
{
	if (id[0] != '/')
		id.insert(0,"/");
}

inline void msleep(unsigned int ms) {
	std::this_thread::sleep_for(std::chrono::milliseconds{ms});
}

#endif
