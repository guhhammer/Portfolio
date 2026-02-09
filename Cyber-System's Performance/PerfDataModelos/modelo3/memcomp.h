//
// Memória compartilhada
// PSCF - PUCPR
// Prof. Luiz A. de P. Lima Jr.
//
// Exemplo de uso:
//
//    MemComp<int> mc("minha_mem");
//    ...
//    *mc = 123;   // *mc é a "variável"
//    ...
//
// Contrutor:
//    reserva se nao existe, anexa se já existe
//

#ifndef __MEMCOMP_H__
#define __MEMCOMP_H__

#include <string>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <unistd.h>
#include <iostream>
#include "util.h"

// MemComp

template<typename T>
class MemComp {
private:
	std::string id_;
	T* mem_;
public:
	MemComp(const std::string& id) : id_{id} {
		throwif(id_.empty(), "id vazio");
		fix(id_);
		int shmid = shm_open(id_.c_str(), O_CREAT|O_RDWR, 0666);
		throwif(shmid==-1, "erro no shm_open()");
		ftruncate(shmid,sizeof(T));
		mem_ = static_cast<T*>(mmap(nullptr, sizeof(T),
			PROT_READ|PROT_WRITE,MAP_SHARED,shmid,0));
	}

	MemComp& operator=(const T& x) {
		*mem_ = x;
		return *this;
	}

	T* operator->() { return mem_; }
	T& operator*() { return *mem_; }
	operator T&() { return *mem_; }
	operator T() const { return *mem_; }

	~MemComp() {
		shm_unlink(id_.c_str());
	}
};

#endif
