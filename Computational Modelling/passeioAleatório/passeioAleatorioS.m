% Passeio Aleatório

function probS = passeioAleatorioS(n, k, nsimul)
    
    % Probabilidade simulada
    acertos = 0;
    
    if(mod(n,2) == mod(k,2))
     
        for i = 1 : nsimul
            
            posicao = 0;
            
            for j = 1 : n
            
                if randi(2) == 1
                
                    posicao -= 1;
                
                else
                
                    posicao += 1;
                
                end
                
            end
        
            if posicao == k
        
                acertos += 1;
        
            end
        
        end
        
        probS = acertos/nsimul; 
  
    else
      
        probS = 0;

    end
  
end
