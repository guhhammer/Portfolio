
from flask import Flask, jsonify, request

import keras, os, zipfile, random, joblib
import numpy as np
from datetime import date

import matplotlib.pyplot as plt
import matplotlib.image as mpimg

from tensorflow.keras import layers, Model
from keras.models import load_model

from tensorflow.keras.utils import plot_model
from keras.utils import CustomObjectScope

from tensorflow.keras.optimizers import RMSprop
from tensorflow.keras.preprocessing import image
from tensorflow.keras.preprocessing.image import ImageDataGenerator, img_to_array, load_img
from keras.initializers import glorot_uniform

def load_image(img_path):
        img = image.load_img(img_path, target_size=(150,150))
        img_tensor = image.img_to_array(img)
        img_tensor = np.expand_dims(img_tensor, axis=0)
        img_tensor /= 255.

        return img_tensor

def positive(num):
        return num if num > 0 else num * (-1)

def label_me(num):
        return "dog" if positive(num-1) < num else "cat"

app = Flask(__name__)

@app.route('/', methods=['GET'])
def get_samples():
        return ("<HTML>"+
                     "<BODY>"+
                "<p> Gustavo Hammerschmidt </p><br>"+
                "<p> Cat vs. Dog image classification server </p><br>"+
                "<p>"+str(date.today())+"<p><br>"+
                     "</BODY>"+
                "</HTML>"
        )



@app.route('/', methods=['POST'])
def post_samples():
        if not request.json:
                abort(400)
                
        model = None
        with CustomObjectScope({'GlorotUniform': glorot_uniform()}):
	        model = load_model("./model.h5", custom_objects=None, compile=False)
		
        return ("<HTML>"+
                     "<BODY>"+
                "<p> Gustavo Hammerschmidt </p><br>"+
                "<p> Cat vs. Dog image classification server. </p><br>"+
		"<p> The image you've submitted is classified as a: "+ label_me(model.predict(load_image(request.json['image'])))+"</p><br>"+
		"<p>"+str(date.today())+"<p><br>"+
                     "</BODY>"+
                "</HTML>"

        )


if __name__ == "__main__":
        app.run(host="127.0.0.1", port=5000)
