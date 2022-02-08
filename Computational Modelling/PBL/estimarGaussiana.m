function [mu sigma2] = estimarGaussiana(X)
%estimarGaussiana Essa função estima os parâmetros de uma 
%distrubuição Gaussian usando os dados passados em X
%   [mu sigma2] = estimarGaussiana(X), 
%   A entrada X é o conjunto de dados com cada ponto n-dimensional em uma
%   linha.
%   A saída é o vetor n-dimensional  mu, com as médias dos dados e as
%   varianciass sigma^2, em vetores n x 1.
% 

% Variáveis úteis
[m, n] = size(X);

% ====================== COLOQUE SEU CÓDIGO AQUI ======================
% Instruções:  Calcules as médias e variâncias: mu(i) deve conter
%               a média dos dados para a i-ésima feature e sigma2(i)
%               deve conter a variancia dos dados para a i-ésima feature.
%
% O código a seguir foi colocado para que a função possa ser chamada ser
% dar erro
% Você dave alterar esse código para retornar esses valores corretamente
    
mu = sum(X)/m;

sigma2 = sum((X-mu).^2)/m;


% =============================================================

end
