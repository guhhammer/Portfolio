function XdY = XdadoY(X, Y, y)
    XdY = X .* repmat((Y==y),1,size(X,2));
end