% aniverA.m
%
function probA = aniverA( K )
    
    % K é o tamanho do grupo.
    
    probAux = 1;  % Probabilidade complementar.
    
    for i = (365-K+1):365  
        probAux = probAux * (i/365);
    end  
    
    probA = 1 - probAux;  % Probabilidade Analitica.
    
end