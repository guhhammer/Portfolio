"""Webcam face detection with an OpenCV Haar cascade (class demo).

Reads frames from the camera, draws a rectangle around each detected face, shows the face crop and prints the frame rate once per second. Needs the OpenCV cascade file `haarcascade_frontalface_default.xml` saved as faces.xml next to the script. Press q to quit.
"""
import cv2
import tensorflow as tf
import random
import numpy as np
import time
from tensorflow.keras import datasets, layers, models





 
if __name__ == "__main__":
    
	# open a video file instead of the camera:
	#stream = cv2.VideoCapture("videoplayback.mp4")

	# open the camera
	stream = cv2.VideoCapture("/dev/video2") #Linux			
	
	#stream = cv2.VideoCapture(0) #Windows
	
	frame_count = 0;
	
	haar_file = 'faces.xml'	
	face_cascade = cv2.CascadeClassifier(haar_file)
	
	t1 = time.time()
	while(True):

		# Capture the video frame
		# by frame		
		ret, frame = stream.read() #== imread()
		
		frame_count += 1
		
		t2 = time.time()
		if(t2 - t1 > 1.):
			print("Total Frame = ",frame_count)
			frame_count =0
			t1 = time.time()
		
		
		if(frame is not None) :
			gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
		
			faces = face_cascade.detectMultiScale(gray, 1.1, 3)
			
			for (x, y, w, h) in faces:
				cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 255), 2)			
				img_face = frame[y:y+h,x:x+w]
				cv2.imshow("Faces",img_face)
		
		
		cv2.imshow("Frame",frame)
				
		c = cv2.waitKey(5) & 0XFF
		
		if c == ord('q'):
			print("Quitting: q pressed")
			break			
	
	
	stream.release()
	# Destroy all the windows
	
	cv2.destroyAllWindows()
