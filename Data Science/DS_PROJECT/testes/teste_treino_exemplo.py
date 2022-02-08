import pandas as pd
from sklearn.linear_model import LogisticRegression
import pandas as pd
import numpy as np


df = pd.read_csv('train.csv')
df_test = pd.read_csv('test.csv')
df = pd.get_dummies(df)

cols_treino = ['TARGET', 'TEMPOCPF', 'SOMARENDACASA', 'ESTIMATIVARENDA', 'PERCENTBOLSAFAMILIACEP',
        'QTDDECLARACAO10', 'QTDDECLARACAOPAGAR10']
cols_teste = cols_treino.copy()
cols_teste.remove('TARGET')
df = df[cols_treino]
df_test = df_test[cols_teste]

df_train = df

X, y = df.drop(['TARGET'], axis=1), df['TARGET']
X_train, y_train = df_train.drop(['TARGET'], axis=1), df_train['TARGET']
X_test = df_test

lr = LogisticRegression(max_iter=10000)
lr.fit(X_train, y_train)

prob = lr.predict_proba(X_test)
prob = [x[1] for x in prob] #obtendo apenas classificacoes da classe 1 (default)

df_output_jpb = pd.DataFrame({'TEAM_JPB': prob})
df_output_jpb.to_csv('TEAM_JPB.csv', index=False)