function [mu, sigma] = estimarGaussiana(X)
% Essa função estima os parâmetros de uma da distribuição Gaussiana
% usando os dados passados em X
%   O argumento X é o conjunto de dados
%   Cada coluna de X tem os valores de treinamento de uma característica
%   linha.
%   A função deve retornar:
%      O vetor com as médias das características (mu)
%      A matriz de covariância das características (sigma) 
% 

    % ====================== COLOQUE SEU CÓDIGO AQUI ======================
    % Instruções:  Calcule o vetor de médias e a matriz de covariância: 
    % O código abaixo foi incluído para não dar erro na chamada da função
    % Você dave substituir o código para ele retornar os valores corretamente
    n = size(X, 2);
    mu = zeros(1, n);
    sigma = eye(n, n);

end
