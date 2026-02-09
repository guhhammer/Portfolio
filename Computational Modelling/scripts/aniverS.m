% aniverS.m
%
function probS = aniverS( K, N )

    % K é o tamanho do grupo
    % N é a quantidade de simulações                                  
    
    evento = zeros(N, 1); % iniciado com N eventos nulos.
    for sorteio = 1:N
        
        y = unidrnd(365,1,K);  % vetor de datas.
        
        aux = unique(y);    % vetor sem repeticao.
        
        if length(aux) == length(y) 
        
            evento(sorteio) = 1;
        
        end
    
    end

    probS = 1 - sum(evento)/N; % fim da simulação e impressão do resultado.

end

%{
    Olá, Professor.
    
    Eu notei que no seu código o resultado retornaria a probabilidade
    complementar:  probS = sum(evento)/N !!! Então, tomei a liberdade
    de retornar a probabilidade de 2 pessoas fazerem aniversário no
    mesmo dia:  probS = 1 - sum(evento)/N pois creio que essa era 
    intenção do exercício comparar as duas formas de se analisar o 
    mesmo caso (Analítica e Simulacionalmente).
    
    
    
%}