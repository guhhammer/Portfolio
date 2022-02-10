function visualizarAderencia(X, mu, sigma2)
% Visualizar os dados e a distribuição estimada
%   visualizarAderencia(X, p, mu, sigma2) 
% Mostra a aderência dos dados com o modelo Gaussiano Multivariado
%

[X1,X2] = meshgrid(0:.5:35);
Z = mvnpdf([X1(:) X2(:)], mu, sigma2);
% Caso não tenha mvnpdf use o comando a seguir
%Z = multivariateGaussian([X1(:) X2(:)],mu,sigma2)

Z = reshape(Z,size(X1));
plot(X(:, 1), X(:, 2),'bx');
hold on;
% Não plote se houver valores infinitos
if (sum(isinf(Z)) == 0)
    contour(X1, X2, Z, 10.^(-20:3:0)');
end
xlabel('Latência (ms)');
ylabel('Throughput (mb/s)');
hold off;

end