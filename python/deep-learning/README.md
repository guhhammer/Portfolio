# Deep learning (Python, TensorFlow/Keras)

Coursework from CS497 "Deep Learning", taken in English during the exchange semester in the United States (2020): Keras fundamentals, convolutional and recurrent networks, a cat-vs-dog classifier served over HTTP, and the team project **Cheesy Identifier**.

For a non-technical reader: these programs learn from example images or texts and then answer questions about new ones, such as "is this a cat or a dog?" or "which kind of cheese is this?", the second one packaged as a small web application.

| Folder | What it is |
| --- | --- |
| `cheesy-identifier/` | Team project (with Andre Wlodkovski and Darren Greene): a CNN that identifies 13 kinds of cheese from a photo. `app/Database/` holds the Google Images scraper (Python driving a PHP extractor under XAMPP) used to build the training set, `app/Server/` the Keras training script and the Flask prediction API, `app/UserInterface/` the HTML/JS front end; plus the status report, the final report, the presentation and the report figures. The scraped photo dataset (1,688 images) and the trained model are not included |
| `assignments/` | Assignment 1 (written answers), assignment 2 (a functional-style calculator in Python), assignment 3 (linear regression on health-insurance data), assignment 4 (a CNN trained on the cats-and-dogs dataset and served by a Flask web server that classifies a posted image, with shell clients, notebooks, README and screenshots) |
| `class-notebooks/` | Keras and TensorFlow exercises from class: hello world, house prices, activation functions, Fashion-MNIST, convolution and pooling, LSTM / GRU / Conv1D, a bidirectional LSTM on the sarcasm dataset, an IMDB LSTM, pickling |
| `midterm/` | The three midterm notebooks, the written answers and screenshots |
| `reading/` | Summary of a paper on deep-learning detection of COVID-19 pneumonia on CT scans, and two reading quizzes |
| `skills-exercises/` | Screenshots of the skill exercises |

Everything is in English as submitted. The textbooks, the professor's templates and the jQuery copy were removed; `simple_html_dom.php` is the third-party HTML parser used by the scraper.
