function pXdY = PXdadoY1(X, Y, x, y)
% X dados   x observado de X
% Y label   valores da classe Y
    pXdY = zeros(size(y,2), size(x,2));
    for i = 1:size(y,2)
        temp = (XdadoY(X, Y, y(i)));
        for j=1:size(x,2)
            pXdY(i, j) = sum((temp(:,j)==x(j)))/sum((Y==y(i)));
        end
    end
end