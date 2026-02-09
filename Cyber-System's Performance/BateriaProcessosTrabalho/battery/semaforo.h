//
// Operacoes com SEMÁFOROS
// PSCF - PUCPR
// Prof. Luiz A. de P. Lima Jr.
//
// Exemplo de uso:
//
//    Semaforo s("meu_sem", 1);
//    ...
//    s.down();
//    ...
//    s.up();
//    ...
//
// construtor:
//    cria se nao existe, anexa se já existe (neste caso, valor_inicial é ignorado)
//

#ifndef __SEMAFORO_H__
#define __SEMAFORO_H__

#include "util.h"
#include <fcntl.h>
#include <sys/stat.h>
#include <semaphore.h>

class Semaforo {
private:
	std::string id_;
	sem_t* sem_;
public:
	Semaforo(const std::string& id, unsigned int valor_inicial, bool novo=false) : id_{id} {
		throwif(id_.empty(), "id invalido");
		fix(id_);
		if (novo)	// destroi antigo?
			sem_unlink(id_.c_str());
		sem_ = sem_open(id_.c_str(), O_CREAT | O_EXCL | O_RDWR, 0666, valor_inicial);
		if (sem_ == SEM_FAILED)
			sem_ = sem_open(id_.c_str(), O_RDWR);
		throwif(sem_ == SEM_FAILED, "erro no sem_open()");
	}
	~Semaforo() { sem_unlink(id_.c_str()); }

	void up() { sem_post(sem_); }
	void down() { sem_wait(sem_); }
};

#endif
