function visualizarAderencia(X, mu, sigma2)
%VISUALIZARADERENCIA Visualizar os dados e a distribuição estimada.
%   visualizarAderencia(X, p, mu, sigma2) Mostra a a aderência dos dados
%   com função de densidade de probabilidade da distribuição Gaussiana. 
%   Cada exemplo tem uma localização (x1, x2) que depende dos valores
%   das suas features.
%

[X1,X2] = meshgrid(0:.5:35);

Sigma2 = diag(sigma2);
Z = mvnpdf([X1(:) X2(:)], mu', Sigma2);

% Caso esteja usando Octave, use o comando a seguir
%Z = multivariateGaussian([X1(:) X2(:)],mu,sigma2)
Z = reshape(Z,size(X1));

plot(X(:, 1), X(:, 2),'bx');
hold on;
% Não plote se houver valores infinitos
if (sum(isinf(Z)) == 0)
    contour(X1, X2, Z, 10.^(-20:3:0)');
end
hold off;

end