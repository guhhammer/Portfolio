//
// Named POSIX semaphores shared between processes
// Helper header provided in class by Prof. Luiz A. de P. Lima Jr., PUCPR;
// comments translated to English.
//
// Usage:
//
//    NamedSemaphore s("my_sem", 1);
//    ...
//    s.down();
//    ...
//    s.up();
//    ...
//
// Constructor:
//    creates the semaphore if it does not exist, attaches to it if it does
//    (in that case initial_value is ignored)
//

#ifndef __NAMED_SEMAPHORE_H__
#define __NAMED_SEMAPHORE_H__

#include "util.h"
#include <fcntl.h>
#include <sys/stat.h>
#include <semaphore.h>

class NamedSemaphore {
private:
	std::string id_;
	sem_t* sem_;
public:
	NamedSemaphore(const std::string& id, unsigned int initial_value, bool fresh=false) : id_{id} {
		throwif(id_.empty(), "invalid id");
		fix(id_);
		if (fresh)	// destroy a previous one?
			sem_unlink(id_.c_str());
		sem_ = sem_open(id_.c_str(), O_CREAT | O_EXCL | O_RDWR, 0666, initial_value);
		if (sem_ == SEM_FAILED)
			sem_ = sem_open(id_.c_str(), O_RDWR);
		throwif(sem_ == SEM_FAILED, "sem_open() failed");
	}
	~NamedSemaphore() { sem_unlink(id_.c_str()); }

	void up() { sem_post(sem_); }
	void down() { sem_wait(sem_); }
};

#endif
