function pXdY = PXdadoY(X, Y, x, y)
% X dados   valores das características observadas
% Y label   valores das classes observadas
% x vetor com as características a ser classificado
% y valor da classe (1 ou 2)
    pXdY = ones(size(y));
    for i = 1:size(y,2)
        temp = (XdadoY(X, Y, y(i)));
        for j=1:size(x,2)
            pXdY(i) = pXdY(i) * sum((temp(:,j)==x(j)))/sum((Y==y(i)));
        end
    end
end