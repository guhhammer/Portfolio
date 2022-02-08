import cv2
import tensorflow as tf
import random
import numpy as np
from tensorflow.keras import datasets, layers, models
from tensorflow.keras.callbacks import TensorBoard, ModelCheckpoint
	

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
    
	np.set_printoptions(precision=4)
    
	stream = cv2.VideoCapture("/dev/video2")
	
	objs = []
	lbls = []
	
	
	class_names = ['C/ Mascara','Sem Mascara']
	
	haar_file = 'faces.xml'
	
	face_cascade = cv2.CascadeClassifier(haar_file)
	
	while(True):

		# Capture the video frame
		# by frame
		
		ret, frame = stream.read()
		
		
		if(frame is None):
			print("Fail to grab frame")
			continue
			
		gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
	
		faces = face_cascade.detectMultiScale(gray, 1.1, 3)
		if(len(faces) > 0):
			for (x, y, w, h) in faces:
				cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 0, 0), 2)
			
		
			face = frame[y:y+h,x:x+w]
			cv2.imshow("Face",face)
		
		cv2.imshow("Frame",frame)
		c = cv2.waitKey(5) & 0XFF			
			
		
		if c == ord('q'):
			break
			
		if c == ord('a'):
			print("Capturing ", class_names[0])			
			im = cv2.resize(face,(128,128))
			objs.append(im)
			lbls.append(0)
		
		if c == ord('b'):
			print("Capturing ", class_names[1])			
			im = cv2.resize(face,(128,128))
			objs.append(im)
			lbls.append(1)
		
		if c == ord('c'):
			print("Capturing ", class_names[2])			
			im = cv2.resize(face,(128,128))
			objs.append(im)
			lbls.append(2)
			
		
		
	#-----while-----
	# After the loop release the cap object
	stream.release()
	# Destroy all the windows	
	cv2.destroyAllWindows()
	
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
	
	
	checkpoint = ModelCheckpoint('model-faces.model',monitor='val_loss',verbose=0,save_best_only=True,mode='auto')

	
	history = model.fit(train_images, train_labels, epochs=10, 
					validation_data=(val_images, val_labels),
					callbacks=[checkpoint])
	
	
	
	
	print('Training Done! - You can now run test.py')
