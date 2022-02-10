function [mu, sigma] = estimarGaussiana(X)
% Essa fun��o estima os par�metros de uma da distribui��o Gaussiana
% usando os dados passados em X
%   O argumento X � o conjunto de dados
%   Cada coluna de X tem os valores de treinamento de uma caracter�stica
%   linha.
%   A fun��o deve retornar:
%      O vetor com as m�dias das caracter�sticas (mu)
%      A matriz de covari�ncia das caracter�sticas (sigma) 
% 

    % ====================== COLOQUE SEU C�DIGO AQUI ======================
    % Instru��es:  Calcule o vetor de m�dias e a matriz de covari�ncia: 
    % O c�digo abaixo foi inclu�do para n�o dar erro na chamada da fun��o
    % Voc� dave substituir o c�digo para ele retornar os valores corretamente
    n = size(X, 2);
    mu = zeros(1, n);
    sigma = eye(n, n);

end
