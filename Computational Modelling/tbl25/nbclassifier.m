function class = nbclassifier(X, Y, x, y)
  % X recebe os valores das características observadas
  % Y recebe os valores dos rotulos observados
  % x recebe o vetor a ser classificado
  % y recebe o vetor com o valor das classes (numeros inteiros, maiores que 0)
  
    PXdadoY(X, Y, x,y) .* PY(Y,y)
    [c, i] = max(PXdadoY(X, Y, x,y) .* PY(Y,y));
    class = y(i);
end