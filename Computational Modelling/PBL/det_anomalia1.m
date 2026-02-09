%% Detecção de anomalia
%  script det_an0
%
%  Instruções
%  ------------
%
%  Esse arquivo contém código para auxiliar no trabalho.
%  Você deve completar as seguiontes funções:
%
%     estimarGaussiana.m
%     selecionarThreshold.m
%
%  Não é necessário alterar esse arquivo
%

%% Inicialização
clear ; close all; clc

%% ================== Parte 1: Carregar o dataset exemplo ===================
%  Iniciamos com o dataset pequeno que pode ser visualizado.
%
%  Contém a estatística de dois servidores de rede sobre várias máquinas
%  com medidas de latencia e throughput para cada máquina.
%

fprintf('Visualização do dataset de exemplo para detectção das anomalias (outliers).\n\n');

%  Carrega dataset nas variáveis X, Xval, yval
load('dadosrede.mat');

%  Visualiza o dataset
plot(X(:, 1), X(:, 2), 'bx');
axis([0 30 0 30]);
xlabel('Latência (ms)');
ylabel('Throughput (mb/s)');

fprintf('Programa interrompido. Pressione enter para continuar.\n');
pause


%% ================== Parte 2: Estimar as estatísticas da distribuição  ===================
%  Assume-se uma distribuição Gaussiana multivariada para os dados.
%
%  Para estimar os parâmeteros da distribuição Gaussiana, calculam-se as
%  probabilidades para cada um dos pontos.

%  Na sequência isualizamos a distribuição e onde cada um dos pontos
%  encontra-se em termos dessa distribuição>
%
fprintf('Visualização da aderência dos dados com a distribuição Gaussiana.\n\n');

%  Estima mu e sigma
[mu sigma] = estimarGaussiana1(X);

%  Retorna a densidade da normal multivariada de cada linha de X
p = mvnpdf(X, mu', sigma);
% Se você estiver usando o Octave use a seguinte função 
% p = multivariateGaussian(X, mu, sigma2);

%  Visualizar a aderência
visualizarAderencia1(X,  mu, sigma);
xlabel('Latência (ms)');
ylabel('Throughput (mb/s)');

fprintf('Programa interrompido. Pressione enter para continuar.\n\n');
pause;

%% ================== Parte 3: Encontrar as anomalias ===================
%  Encontrar um bom valor para o threshold epsilon usando as probabilidades
%  dadas pela distribuição Gaussiana estimada quando aplicada em um  
%  conjunto cross-validation
% 

pval = mvnpdf(X, mu', sigma);
% Se você estiver usando o Octave use a seguinte função 
% pval = multivariateGaussian(Xval, mu, sigma);

[epsilon F1] = selecionarThreshold(yval, pval);
fprintf('Melhor epsilon encontrado usando cross-validation: %e\n', epsilon);
fprintf('Melhor F1 obtido com os dados de cross-validation:  %f\n', F1);
fprintf('   (o valor de epsilon deve ser aproximadamente 8.99e-05)\n\n');

%  Encontrar as anomalias no conjunto de treinamento e mostrar no gráfico
anomalias = find(p < epsilon);

%  Desenhar um círculo vermelho em trono dos outliers
hold on
plot(X(anomalias, 1), X(anomalias, 2), 'ro', 'LineWidth', 2, 'MarkerSize', 10);
hold off

fprintf('Programa interrompido. Pressione enter para continuar.\n\n');
pause;

%% ================== Parte 4: Anomalias multidimensionais  ===================
%  Usaremos o código anterior a aplicaremos em um problema mais difícil, no qual 
%  mais features descrivem cada dado e apenas algumas features indicam de o ponto 
%  é uma anomalia.
%

%  Carrega o dataset
load('dadosrede2.mat');

%  Aplicar os mesmos passos
[mu sigma] = estimarGaussiana1(X);

%  Conjunto de treinamento 
%Sigma2 = diag(sigma2);
p = mvnpdf(X, mu', sigma);
% Se você estiver usando o Octave use a seguinte função 
% p = multivariateGaussian(X, mu, sigma);

%  Conjunto cross-validation
pval = mvnpdf(Xval, mu', sigma);
% Se você estiver usando o Octave use a seguinte função 
% pval = multivariateGaussian(Xval, mu, sigma);

%  Encontrar o melhor threshold
[epsilon F1] = selecionarThreshold(yval, pval);

fprintf('Melhor epsilon encontrado usando cross-validation: %e\n', epsilon);
fprintf('Melhor F1 obtido com os dados de cross-validation:  %f\n', F1);
fprintf('# Anomalias encontradas: %d\n', sum(p < epsilon));
fprintf('   (o valor de epsilon deve ser aproximadamente 1.38e-18)\n\n');
pause



