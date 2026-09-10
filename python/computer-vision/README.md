# Computer vision (Python)

Coursework from the "Computer Vision" course at PUCPR (2021): classical image processing with scikit-image and OpenCV, classical machine learning on image features, convolutional networks for segmentation and classification with TensorFlow/Keras, and generative adversarial networks. Team projects with Eduardo Eiji Goto and João Vitor Andrioli.

For a non-technical reader: these notebooks teach a computer to find a coin in a photo and say how much it is worth, to repaint a photo in van Gogh's style, and to tell whether a face on a webcam is wearing a mask.

| Folder | What it is |
| --- | --- |
| `coin-detection-classical-ml/` | TDE 01 on the Brazilian coin dataset: coins are located with HSV saturation, a median filter, Yen thresholding and region properties (square bounding boxes only), the crops are described with local binary patterns, and five classifiers (KNN, linear SVM, random forest, decision tree, AdaBoost) are compared with confusion matrices and per-coin error analysis. No deep learning allowed |
| `coin-segmentation-unet-cnn/` | TDE 02: a U-Net trained on the coin masks to segment the coin, bounding boxes derived from the predicted masks and a CNN classifying the crops; a second version with a MobileNetV2 U-Net (from the TensorFlow tutorial as adapted by the professor) plus a comparison of a small CNN, LeNet and AlexNet on the crops, with conclusions |
| `cyclegan-style-transfer/` | Group project on CycleGAN (photo to van Gogh painting) with the team's own photos in `sample-images/`, a discussion of the results and written answers on GANs, conditional GANs and CycleGANs |
| `gan-red-blood-cells-report/` | Final report (Portuguese, SBC and LaTeX layouts) on using GANs to generate synthetic red-blood-cell images for data augmentation, with figures |
| `class-exercises/` | Class 03 exercise: first attempt at locating coins with Otsu thresholding and connected components |
| `webcam-cnn-demos/` | Class demos run on the webcam: Haar-cascade face detector, a mask / no-mask CNN trained from live captures, and a two-step object classifier (notebook, bottle, background) |

The notebooks were run on Google Colab and keep their outputs; they download the datasets themselves (links inside). Requirements: `scikit-image`, `opencv-python`, `scikit-learn`, `tensorflow`, `seaborn`. The professor's tutorial notebooks and slides, the reference papers, the trained models and the OpenCV cascade file were removed.
