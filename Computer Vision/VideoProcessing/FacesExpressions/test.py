import cv2
import tensorflow as tf
import random
import numpy as np
from tensorflow.keras import datasets, layers, models
from tensorflow.keras.models import load_model


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


def draw_result(img,point,preds,class_names):

	cls = np.argmax(preds)	
	info = class_names[cls] + ' ' + str(preds[0][cls])	
	font                   = cv2.FONT_HERSHEY_SIMPLEX
	bottomLeftCornerOfText = point
	fontScale              = 1
	fontColor              = (255,0,0)
	lineType               = 2

	cv2.putText(img,info, 
		bottomLeftCornerOfText, 
		font, 
		fontScale,
		fontColor,
		lineType)	
    
 
if __name__ == "__main__":
    
	np.set_printoptions(precision=4)
    
	stream = cv2.VideoCapture("/dev/video2")
	
	objs = []
	lbls = []
	
	
	class_names = ['C/ Mascara','S/ Mascara']
	
	haar_file = 'faces.xml'
	
	face_cascade = cv2.CascadeClassifier(haar_file)
	
	model=load_model("model-faces.model")
	
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
			im = cv2.resize(face,(128,128))
			preds = model.predict(np.expand_dims(im,axis=0))			
		
			pred = np.argmax(preds)
		
			draw_result(frame,(x,y),preds,class_names)
		
			
		cv2.imshow("Frame",frame)
		
		
		c = cv2.waitKey(10) & 0XFF
		
		if c == ord('q'):
			break
			
			
		if c == ord('a'):
			print("Capturing A")
			
			im = cv2.resize(face,(128,128))
			objs.append(im)
			lbls.append(0)
			
		if c == ord('b'):
			print("Capturing B")
			im = cv2.resize(face,(128,128))
			objs.append(im)
			lbls.append(1)
		
		if c == ord('c'):
			print("Capturing C")			
			im = cv2.resize(face,(128,128))
			objs.append(im)
			lbls.append(2)
		
	#-----while-----
	# After the loop release the cap object
	stream.release()
	# Destroy all the windows	
	cv2.destroyAllWindows()
