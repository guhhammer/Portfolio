function [melhorEpsilon melhorF1] = selecionarThreshold(yval, pval)
%SELECIONARTHRESHOLD encontra o melhor threshold (epsilon) para usar na
% detecção de anomalia
%   [melhorEpsilon melhorF1] = selecionaThreshold(yval, pval) encontra o
%   melhor threshold que será usado para selecionar os pontos fora da
%   curva (outliers) com base nos resultados de um conjunto de validação 
%   (pval) e os valores verdadeiros (yval).
%   yval é um vetor com os valores reais
%      (y(i) == 1 corresponde a anomalia)
%      (y(i) == 0 corresponde a não anomalia)
%   pval é o vetor com as probabilidade
%       valores em pval menores do que epsilon serão anomalis


% Inicia os valores para obter o melhor valor no laço a seguir
melhorEpsilon = 0;
melhorF1 = 0;
F1 = 0;

passo = (max(pval) - min(pval)) / 1000;
for epsilon = min(pval):passo:max(pval)
    
    % ====================== COLOQUE SEU CÓDIGO AQUI ======================
    % Instruções: Calcule a métrica F1 escolhendo epsilon como o threshold
    %               e coloque o valor em F1. O código ao final do laço 
    %               vai comparar a métrica F1 para essa escolha de epsilon
    %               e atribuir esse valor como o melhor epsilon se for
    %               melhor do que a escolha atual de epsilon.
    %               
    % Observação:
    %   Para calcular tp, fp, fn 
    %   Use cvPredictions = (pval < epsilon) para obter um vetor
    %       binário de 0's e 1's a partir das previsões de anomalias
    %   O código para calcular cvPredictions a seguir está correto
    %   A expressão lógica entre parenteses gera um novo vetor lógico
    %       que contém apenas 0s e 1s
    %   Um componente do novo vetor é igual a 1 quando o componente de pval
    %       é menor que o epsilon, e zero caso contrário, ou seja:
    %       igual a 0 se for uma anomalia
    %       igual a 1 caso contrário
    %   Você deve alterar o código para calcular tp fp e fn abaixo

    cvPredictions = (pval < epsilon);
    
    
    % O código foi incluído para não  dar erro na chamada da função
    % Você pode usar uma operação lógica entre os vetores y e pval para
    %    encontrar os componentes de tp,fp, fn
    % tp deverá conter a quantidade de positivos verdadeiros
    % fp deverá conter a quantidade de positivos falsos
    % fn deverá conter a quantidade de negativos falsos
    
    tp = sum((cvPredictions(:) .* yval(:)) == 1);  % altere aqui para calcular tp corretamente
    fp = sum(((cvPredictions(:) == 0) .* (yval(:) == 1)) == 1 );  % altere aqui para calcular fp corretamente
    fn = sum(((cvPredictions(:) == 1) .* (yval(:) == 0)) == 1 );  % altere aqui para calcular fn corretamente



    % O código a seguir está correto (não precisa alterar)
    prec = tp/(tp+fp);
    if (tp+fn) != 0
        rec = tp/(tp+fn);   
    else    
        rec = 0;
    end
    F1 = (2*prec*rec) / (prec+rec);


    % =============================================================
    % O código abaixo verifica se conseguiu um F1 melhor
    % caso positivo salva o melhor F1 e o valor do melhor epsilon
    % O código aqui está correto, não precisa alterar

    if F1 > melhorF1
       melhorF1 = F1;
       melhorEpsilon = epsilon;
    end
end

end
