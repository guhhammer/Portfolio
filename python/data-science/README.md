# Data science (Python)

Coursework from the "Data Science" course at PUCPR (2020): exploratory data analysis with pandas and seaborn, correlation and multicollinearity, outliers, scaling and missing values, feature selection, PCA and t-SNE, hypothesis tests for comparing classifiers, and a semester-long team project on credit default prediction.

For a non-technical reader: this is about turning a spreadsheet with millions of cells into charts, findings and a model that predicts which loan applicants are likely to default.

| Folder | What it is |
| --- | --- |
| `class-notebooks/` | Eleven class notebooks (translated): pandas basics on Kobe Bryant's shots, groupby and describe, Anscombe's quartet, distributions and plot types, correlation and VIF, a written critique of misleading charts, outliers (Tukey, isolation forest), scaling and imputation, feature selection and resampling, PCA and t-SNE, and Wilcoxon / Friedman / Nemenyi / Bayesian tests between classifiers |
| `credit-default-project/` | Team project ("Maniacs", with Bruno Thuma, Leonardo Cleyton, Lucas Dall Agnol and Victor Vieira): 20 univariate and 8 multivariate analyses of a 100-column credit dataset built on a reusable plotting helper, three polished final plots, an XGBoost model (KS about 88 on the validation split) with median imputation, random oversampling and scaling, the exported default probabilities for the test set, the data dictionary, a baseline logistic regression, the peer review the team wrote for another team, and the final results screenshot |

The notebooks were run on Google Colab and keep their outputs; the class datasets are loaded from the professor's URLs and the project data from Google Drive (links inside; the data itself is not included). The professor's slides and the earlier versions of the project notebook were removed.
