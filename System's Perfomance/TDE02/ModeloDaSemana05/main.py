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


from sklearn.metrics import f1_score, accuracy_score


acc = accuracy_score(Y_test, Y_previsto)



vp = 0
vn = 0
fp = 0
fn = 0


for i in range(len(Y_previsto)):

	if int(Y_previsto[i]) == int(Y_test[i]) == 1:

		vp += 1
		continue

	if int(Y_previsto[i]) == int(Y_test[i]) == 0:

		vn += 1
		continue

	if int(Y_previsto[i]) == 0 and 1 == int(Y_test[i]):

		fn += 1
		continue

	if int(Y_previsto[i]) == 1 and 0 == int(Y_test[i]):

		fp += 1
		continue


print(vp, vn, fp, fn)


support_0 = fp + vn
support_1 = vp + fn
  
  
prec_0 = vn/(vn+fn)
rec_0 = vn/(vn+fp)

F1_0 = (2*prec_0*rec_0) / (prec_0+rec_0)
  
prec_1 = vp/(vp+fp)
rec_1 = vp/(vp+fn)
F1_1 = (2*prec_1*rec_1) / (prec_1+rec_1)


F1_mp = (F1_0 * support_0 + F1_1 * support_1) / (support_0 + support_1)


#f1_mp = f1_score(Y_test, Y_previsto, average='weighted')


print('acc: ',acc, '\nf1_mp: ', F1_mp)



### Chama figuraTeste que esta no arquivo class_vi
### Recebe o classificador treinado e o conjunto de teste
### Usa o classificador treinado para colorir o limite de decisao
### Usa o conjunto de teste para mostrar como estao os pontos do conjunto com relacao ao limite
figuraTeste(clf, X_test, Y_test)


