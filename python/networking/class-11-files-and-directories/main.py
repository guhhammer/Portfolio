import os, shutil

def exercise1():
	print("Current directory: ", os.getcwd(), "\n")

	for root, dirs, files in os.walk(".", topdown=False):
		for name in files:
			print(os.path.join(root, name))
		for name in dirs:
			print(os.path.join(root, name))


def exercise2():
	#a) Print the current working directory
	print("Current directory: ", os.getcwd(), "\n")

	#b) Create the subdirectories SUBDIR1 and SUBDIR2
	try:
		os.makedirs('SUBDIR1')
		os.makedirs('.\\SUBDIR2')
	except OSError:
		print("The folders already exist!")

	#c) Copy the file text_file.txt to SUBDIR1
	shutil.copy2('text_file.txt', 'SUBDIR1\\text_file2.txt') # The interpreter requires the backslash to be escaped.

	#d) Move the file from SUBDIR1 to SUBDIR2
	shutil.move('SUBDIR1\\text_file2.txt', "SUBDIR2\\text_file2.txt")

	#e) Delete SUBDIR1 and everything inside it
	shutil.rmtree('SUBDIR1')


def exercise3():

	f = open('test.txt', 'w')
	f.write('First line\n')
	f.write('Second line\n')
	try:
		f.read()
	except OSError:
		print("Reading not allowed.")
	f.close()


	f = open('test.txt', 'r')
	print(f.readline())
	print(f.readline())
	try:
		f.write('Third line\n')
	except OSError:
		print("Writing not allowed.")
	f.close()


	f = open('test.txt', 'r+')
	print(f.readline())
	f.write('Fourth line\n')
	print(f.readline())
	f.seek(0, 0)
	print(f.readlines())


def exercise4():
	f = open('test.txt', 'a+')
	f.write('Fifth line\n')
	f.seek(0, 0)
	print(f.readlines())
	f.close()

	f = open('test.txt', 'w+')
	f.write('Sixth line\n')
	f.seek(0, 0)
	print(f.readlines())
	f.close()

	f = open('test.txt', 'r+')
	f.write('Seventh line\n')
	print(f.readlines())
	f.seek(0, 0)
	print(f.readlines())
	f.close()


def exercise5():
	f = open('test.bin', 'wb+')
	print(f.write(bytearray([254])))
	print(f.write(bytearray([10, 13])))
	print(f.write(bytearray([48, 49, 50])))
	f.seek(0, 0)
	print(f.read())
	try:
		f.write('012')
		f.close()
	except TypeError:
		print("The file is binary.")

	f = open('test.bin', 'r+')
	print(f.read())
	try:
		f.write(bytearray([48, 49, 50]))
	except TypeError:
		print("The file is binary.")

	print(f.write('254'))
	print(f.write('012\n'))
	f.seek(0, 0)
	print(f.read())
	f.close()


#exercise1()
#exercise2()
#exercise3()
#exercise4()
#exercise5()



# ANSWERS:

## Question 1: '.' means the current directory.

## Question 2: seek moves the file pointer.
###            The pointer moves to the end after any write operation.

## Question 3: 'a': creates the file if it does not exist and appends if it does.
###            'w': creates a new file whether it exists or not.
###            'r+': does not create the file if it is missing and modifies it if it exists.

## Question 4: 254 in binary is a single byte (0 to 255); "254" as text is 3 bytes.
###            And the number pi? Floating-point numbers are represented with 64 bits => binary.
