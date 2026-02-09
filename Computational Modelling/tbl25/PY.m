function pY = PY(Y, y)
    pY = zeros(size(y));
    for i = 1:size(y,2)
        pY(i) = sum((Y==y(i)))/size(Y,1);
    end
end

