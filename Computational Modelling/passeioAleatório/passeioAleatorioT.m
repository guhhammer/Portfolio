% Passeio Aleatório Solução Teórica

function probT = passeioAleatorioT(n, k)
    
    %Probabilidade analítica
    
    if( mod(n,2) == mod(k,2))
        
        probT = nchoosek(n, ((n+k)/2) ) / (2^n);
    
    else
        
        probT = 0;
        
    end

end