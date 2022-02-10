# Codigo principal

from dados_servidor import dadosServidor
from plotagem import figuraTreinamento, figuraTeste
from classificadorNB import classifica

# Chama dadosServidor para construir conjuntos de teste e treinamento
X_trein, Y_trein, X_test, Y_test = dadosServidor()

### Chama a funcao figuraTreinamento que usa o conjunto de treinamento
### para criar o scatter plot que sera salvo no arquivo treinamento.png
figuraTreinamento(X_trein, Y_trein)

# Aqui chama a funcao classifica que esta no arquivo classificadorNB
# Voce precisa alterar a funcao nesse arquivo
# para ela criar e retornar o classificador GaussianNB da biblioteca sklearn.naive_bayes
# Veja as instrucoes no proprio arquivo
clf = classifica(X_trein, Y_trein)
Y_previsto = clf.predict(X_test)


### Chama figuraTeste que esta no arquivo class_vi
### Recebe o classificador treinado e o conjunto de teste
### Usa o classificador treinado para colorir o limite de decisao
### Usa o conjunto de teste para mostrar como estao os pontos do conjunto com relacao ao limite
figuraTeste(clf, X_test, Y_test)


