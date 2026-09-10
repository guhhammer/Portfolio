# Systems performance modelling (Python)

Coursework from the "Systems Performance Modelling" course at PUCPR (2021): probability by simulation (iterative vs. vectorised with NumPy), random-variate generation, Bayesian classification and anomaly detection, queueing theory, reliability and Markov chains. Team problem sets with Eduardo Eiji Goto and João Vitor Andrioli.

For a non-technical reader: this is the maths used to predict how a computer system behaves under load, e.g. how long requests wait in a queue, how likely a server is to fail, or whether a measurement is abnormal, checked by writing small simulations and comparing them with the formulas.

| Folder | What it is | Run |
| --- | --- | --- |
| `probability-simulations/` | Dice simulation vectorised with NumPy; inverse-transform generators for the Poisson and exponential distributions checked against SciPy; two properties of the exponential distribution (minimum of two exponentials, Erlang sum) simulated iteratively and vectorised; quick SciPy calculations from the problem sets | `python3 dice_simulation.py`, open the notebooks |
| `random-walk-simulation/` | Assignment 01: probability that a symmetric random walk of n steps ends at position k, computed in closed form, by iterative simulation and by vectorised simulation, with timing comparison and the written report | `random_walk.ipynb`, `report.pdf` |
| `naive-bayes-server-load/` | Gaussian Naive Bayes classifier of CPU/memory utilisation pairs into normal and high load, with accuracy and per-class and weighted F1 computed by hand and the decision boundary plotted | `python3 main.py` (needs `scikit-learn`, `matplotlib`) |
| `anomaly-detection-naive-bayes/` | Assignment 02: Naive Bayes anomaly detector on 11 server metrics, precision/recall/F1 by hand and via scikit-learn, and the report comparing it with the multivariate-Gaussian detector of problem set 06 | `naive_bayes_anomaly_detection.ipynb`, `report.pdf` |
| `queueing/` | M/M/m queue metrics in closed form (P0, Erlang C, mean queue length, waiting and response times, distributions of N, W and R) and the berth-sizing example of problem set 09 | `python3 mmm_queue.py` |
| `markov-chains/` | Steady-state probabilities of discrete- and continuous-time Markov chains via the pseudo-inverse, n-step transition probabilities, and the transient regime through the matrix exponential | `python3 dtmc_steady_state.py`, `ctmc_transient.py`, ... |
| `team-problem-sets/` | The twelve weekly team problem sets with worked answers (DOCX and PDF, in Portuguese): probability, discrete and continuous random variables, conditional probability, Bayes' theorem, multivariate distributions and outlier detection, gamma distribution, Poisson processes, M/M/m queues, reliability, discrete- and continuous-time Markov chains | read |
| `assessments/` | The two individual assessments with the SciPy one-liners used to answer them | `python3 assessment_1_calculations.py` |

Scripts need `numpy`, `scipy` (and `scikit-learn`, `matplotlib` for the classifier); all compile with Python 3.13. The professor's slides, code templates and MATLAB skeletons were removed; the data generator and plotting helpers in `naive-bayes-server-load/` are adapted from Udacity's machine-learning course as handed out in class.
