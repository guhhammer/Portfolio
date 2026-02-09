import cv2
import tensorflow as tf
import random
import numpy as np
from tensorflow.keras import datasets, layers, models




def create_model(n_classes):
	model = models.Sequential()
	model.add(layers.Conv2D(32, (3, 3), activation='relu', input_shape=(128, 128, 3)))
	model.add(layers.MaxPooling2D((2, 2)))
	model.add(layers.Conv2D(64, (3, 3), activation='relu'))
	model.add(layers.MaxPooling2D((2, 2)))
	model.add(layers.Conv2D(64, (3, 3), activation='relu'))	
	model.add(layers.Flatten())
	model.add(layers.Dense(64, activation='relu'))
	model.add(layers.Dense(n_classes,activation='softmax'))
	
	return model


	
    
 
if __name__ == "__main__":
    
	stream = cv2.VideoCapture("/dev/video2")
	#stream = cv2.VideoCapture("videoplayback.mp4")
	
	objs = []
	lbls = []
	
	
	class_names = ['Caderno','Garafa','Fundo']
	
	haar_file = 'faces.xml'
	
	face_cascade = cv2.CascadeClassifier(haar_file)
	
	while(True):

		# Capture the video frame
		# by frame
		
		ret, frame = stream.read()
		
		
		if(frame is not None) :
			gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
		
			faces = face_cascade.detectMultiScale(gray, 1.1, 3)
			for (x, y, w, h) in faces:
				cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 0, 0), 2)
			
		
		
		cv2.imshow("Frame",frame)
		c = cv2.waitKey(10) & 0XFF
		
		if c == ord('q'):
			break
			
			
		if c == ord('a'):
			print("Capturing A")
			im = cv2.resize(frame,(128,128))
			objs.append(im)
			lbls.append(0)
			
		if c == ord('b'):
			print("Capturing B")
			im = cv2.resize(frame,(128,128))
			objs.append(im)
			lbls.append(1)
			
		if c == ord('c'):
			print("Capturing C")
			im = cv2.resize(frame,(128,128))
			objs.append(im)
			lbls.append(2)
		
	
	# After the loop release the cap object
	
	l = list(zip(objs,lbls))
	random.shuffle(l)
	
	objs, lbls = zip(*l)
	
	tam = int(len(objs)*.8);
	train_images, train_labels = np.array(objs[:tam]), np.array(lbls[:tam])
	val_images, val_labels = np.array(objs[tam:]), np.array(lbls[tam:])
	
	print("Vamos treinar o modelo")
	model = create_model(len(class_names));
	model.compile(optimizer='adam',
			  loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
			  metrics=['accuracy'])

	
	
	history = model.fit(train_images, train_labels, epochs=3, 
					validation_data=(val_images, val_labels))

	
	
	while(True):

		# Capture the video frame
		# by frame
		
		ret, frame = stream.read()
		
		im = cv2.resize(frame,(128,128))
		preds = model.predict(np.expand_dims(im,axis=0))			
		
		pred = np.argmax(preds)
		
		print(class_names[pred],preds)
		
		cv2.imshow("Frame",frame)
		c = cv2.waitKey(10) & 0XFF
		
		if c == ord('q'):
			break
	
	
	stream.release()
	# Destroy all the windows
	
	cv2.destroyAllWindows()
