%% Detec��o de anomalia
%  script det_an0
%
%  Instru��es
%  ------------
%
%  Esse arquivo cont�m c�digo para auxiliar no trabalho.
%  Voc� deve completar as seguiontes fun��es:
%
%     estimarGaussiana.m
%     selecionarThreshold.m
%
%  N�o � necess�rio alterar esse arquivo
%

%% Inicializa��o
clear ; close all; clc

%% ================== Parte 1: Carregar o dataset exemplo ===================
%  Iniciamos com o dataset pequeno que pode ser visualizado.
%
%  Cont�m a estat�stica de dois servidores de rede sobre v�rias m�quinas
%  com medidas de latencia e throughput para cada m�quina.
%

fprintf('Visualiza��o do dataset de exemplo para detect��o das anomalias (outliers).\n\n');

%  Carrega dataset nas vari�veis X, Xval, yval
load('dadosrede.mat');

%  Visualiza o dataset
plot(X(:, 1), X(:, 2), 'bx');
axis([0 30 0 30]);
xlabel('Lat�ncia (ms)');
ylabel('Throughput (mb/s)');

fprintf('Programa interrompido. Pressione enter para continuar.\n');
pause


%% ================== Parte 2: Estimar as estat�sticas da distribui��o  ===================
%  Assume-se uma distribui��o Gaussiana para os dados.
%
%  Para estimar os par�meteros da distribui��o Gaussiana, calculam-se as
%  probabilidades para cada um dos pontos.
%  Na sequ�ncia isualizamos a distribui��o e onde cada um dos pontos
%  encontra-se em termos dessa distribui��o>
%
fprintf('Visualiza��o da ader�ncia dos dados com a distribui��o Gaussiana.\n\n');

%  Estima mu e sigma2
[mu sigma2] = estimarGaussiana(X);

%  Na fun��o estimarGaussiana calgulamos um vetor com as vari�ncias
%  A fun��o mvnpdf que ser� chamada abaixo recebe como argumento um vetor
%    com as m�dias (mu) e uma matriz de covari�ncia
%  O pr�ximo comando contrio a matriz de covari�ncia (Sigma2)
%    colocando o vetor sigma2 na diagonal e zeros nas demais posi��es
Sigma2 = diag(sigma2);

%  Retorna a densidade da normal multivariada de cada linha de X
p = mvnpdf(X, mu', Sigma2);
% Se voc� estiver usando o Octave use a seguinte fun��o 
% p = multivariateGaussian(X, mu, sigma2);

%  Visualizar a ader�ncia
visualizarAderencia(X,  mu, sigma2);
xlabel('Lat�ncia (ms)');
ylabel('Throughput (mb/s)');

fprintf('Programa interrompido. Pressione enter para continuar.\n\n');
pause;

%% ================== Parte 3: Encontrar as anomalias ===================
%  Encontrar um bom valor para o threshold epsilon usando as probabilidades
%  dadas pela distribui��o Gaussiana estimada quando aplicada em um  
%  conjunto cross-validation
% 

pval = mvnpdf(X, mu', Sigma2);
% Se voc� estiver usando o Octave use a seguinte fun��o 
% pval = multivariateGaussian(Xval, mu, sigma2);

[epsilon F1] = selecionarThreshold(yval, pval);
fprintf('Melhor epsilon encontrado usando cross-validation: %e\n', epsilon);
fprintf('Melhor F1 obtido com os dados de cross-validation:  %f\n', F1);
fprintf('   (o valor de epsilon deve ser aproximadamente 8.99e-05)\n\n');

%  Encontrar as anomalias no conjunto de treinamento e mostrar no gr�fico
anomalias = find(p < epsilon);

%  Desenhar um c�rculo vermelho em trono dos outliers
hold on
plot(X(anomalias, 1), X(anomalias, 2), 'ro', 'LineWidth', 2, 'MarkerSize', 10);
hold off

fprintf('Programa interrompido. Pressione enter para continuar.\n\n');
pause;

%% ================== Parte 4: Anomalias multidimensionais  ===================
%  Usaremos o c�digo anterior a aplicaremos em um problema mais dif�cil, no qual 
%  mais features descrivem cada dado e apenas algumas features indicam de o ponto 
%  � uma anomalia.
%

% Carrega o dataset
load('dadosrede2.mat');

% Aplicar os mesmos passos do conjunto de treinamento 
[mu sigma2] = estimarGaussiana(X);

Sigma2 = diag(sigma2);
p = mvnpdf(X, mu', Sigma2);
% Se voc� estiver usando o Octave use a seguinte fun��o 
% p = multivariateGaussian(X, mu, sigma2);

% Usa o conjunto cross-validation para calcular as probabilidade de cada
%    valor em Xval salvando em pval
pval = mvnpdf(Xval, mu', Sigma2);
% Se voc� estiver usando o Octave use a seguinte fun��o 
% pval = multivariateGaussian(Xval, mu, sigma2);

% Encontrar o melhor threshold
% Os valores de yval e pval v�o ser comparados para selecionar o Threshold
[epsilon F1] = selecionarThreshold(yval, pval);

fprintf('Melhor epsilon encontrado usando cross-validation: %e\n', epsilon);
fprintf('Melhor F1 obtido com os dados de cross-validation:  %f\n', F1);
fprintf('# Anomalias encontradas: %d\n', sum(p < epsilon));
fprintf('   (o valor de epsilon deve ser aproximadamente 1.38e-18)\n\n');
pause



