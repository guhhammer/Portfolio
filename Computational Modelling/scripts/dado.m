function prob = dado(n)
    % Gustavo Hammerschmidt.
    Eventos = 0;
    for i = 1:n
        y = unidrnd(6,1,4);
        pos = find(y==6);
        x = length(pos);
        if x == 1
            if pos == 4
                Eventos = Eventos + 1;
            end
        end
    end
    prob = Eventos/n;
end
