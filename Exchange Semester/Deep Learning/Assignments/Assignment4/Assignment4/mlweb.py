#!flask/bin/python
from flask import Flask, jsonify
from flask import request
import joblib
#from sklearn.linear_model import LogisticRegression
from datetime import date
import Image

from keras.models import load_model
from keras.preprocessing import image
import matplotlib.pyplot as plt
import numpy as np
import os

def load_image(img_path, show=False):

    img = image.load_img(img_path, target_size=(150, 150))
    img_tensor = image.img_to_array(img)                    # (height, width, channels)
    img_tensor = np.expand_dims(img_tensor, axis=0)         # (1, height, width, channels), add a dimension because the model expects this shape: (batch_size, height, width, channels)
    img_tensor /= 255.                                      # imshow expects values in the range [0, 1]

    if show:
        plt.imshow(img_tensor[0])                           
        plt.axis('off')
        plt.show()

    return img_tensor

app = Flask(__name__)

@app.route('/', methods=['GET'])
def get_samples():
    return  ("<HTML>"+
                "<BODY>"+
                    "<p> Gustavo Hammerschmidt </p><br>"+
                    "<p> Cat Vs Dog Image Prediction ML Server </p><br>"+
                    "<p>"+str(date.today())+"</p><br>"+
                "</BODY>"+
             "</HTML>")
 
@app.route('/', methods=['POST'])
def predict():
    if not request.json:
        abort(400)
    
    image_path = request.json['image']
        
    new_image = load_image(img_path)
    
    #load the saved model
    model1 = joblib.load('./finalized_model.pkl')
    
    #predicting
    return jsonify({'result': model1.predict(new_image)})


if __name__ == '__main__':
    app.run(debug=True)

