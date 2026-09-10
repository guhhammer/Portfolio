//
// Shared memory between processes (POSIX shm_open + mmap)
// Helper header provided in class by Prof. Luiz A. de P. Lima Jr., PUCPR;
// comments translated to English.
//
// Usage:
//
//    SharedMemory<int> mc("my_mem");
//    ...
//    *mc = 123;   // *mc is the shared "variable"
//    ...
//
// Constructor:
//    creates the segment if it does not exist, attaches to it if it does
//

#ifndef __SHARED_MEMORY_H__
#define __SHARED_MEMORY_H__

#include <string>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <unistd.h>
#include <iostream>
#include "util.h"

template<typename T>
class SharedMemory {
private:
	std::string id_;
	T* mem_;
public:
	SharedMemory(const std::string& id) : id_{id} {
		throwif(id_.empty(), "empty id");
		fix(id_);
		int shmid = shm_open(id_.c_str(), O_CREAT|O_RDWR, 0666);
		throwif(shmid==-1, "shm_open() failed");
		throwif(ftruncate(shmid, sizeof(T)) == -1, "ftruncate() failed");
		mem_ = static_cast<T*>(mmap(nullptr, sizeof(T),
			PROT_READ|PROT_WRITE, MAP_SHARED, shmid, 0));
	}

	SharedMemory& operator=(const T& x) {
		*mem_ = x;
		return *this;
	}

	T* operator->() { return mem_; }
	T& operator*() { return *mem_; }
	operator T&() { return *mem_; }
	operator T() const { return *mem_; }

	~SharedMemory() {
		shm_unlink(id_.c_str());
	}
};

#endif
