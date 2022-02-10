function [melhorEpsilon, melhorF1] = selecionarLimiar(yval, pval)
% Encontra o melhor threshold (epsilon) para usar na detec��o de anomalia
%   [melhorEpsilon melhorF1] = selecionaThreshold(yval, pval) encontra o
%   melhor threshold que ser� usado para selecionar os pontos fora da
%   curva (outliers) com base nos valores rotulados do conjunto de valida��o 
%   (yval) e os valores obtidos com a Gaussiana Multivariada (pval).
%   yval � um vetor com os valores rotulados reais
%      (y(i) == 1 corresponde a anomalia)
%      (y(i) == 0 corresponde a n�o anomalia)
%   pval � o vetor com o valores da pdf da Gaussina Multivarida treinada
%      valores em pval menores do que epsilon ser�o considerados anomalia

% =================================================================
% Algoritmo para determinar o melhor valor de episilon 
% Variar os valores de epsilon do menor ao maior valor em pval
% Para cada valor de epsilon
%   Calcular o vetor de previs�es (prev) do modelo
%       prev = 1 se pval < epsilon
%       prev = 0 se pval >= epsilon
%   Calcular a quantidade de verdadeiros positivos (vp)
%       Calcular um vetor cujo valores estejam de acordo com a seguinte 
%          express�o l�gica: prev == 1 & yval == 1 
%       vp = soma dos valores no vetor descrito
%   Calcular a quantidade de falsos positivos (fp)
%       Calcular um vetor cujo valores estejam de acordo com a seguinte 
%          express�o l�gica: prev == 1 & yval == 0 
%       fp = soma dos valores no vetor descrito
%   Calcular a quantidade de falsos negativos (fn)
%       Calcular um vetor cujo valores estejam de acordo com a seguinte 
%          express�o l�gica: prev == 0 & yval == 1 
%       fn = soma dos valores no vetor descrito
% ==========================================================

% Inicia os valores para obter o melhor valor no la�o a seguir
melhorEpsilon = 0;
melhorF1 = 0;
F1 = 0;

% Determina o passo para escolha de epsilon
% Os valores testados v�o do menor valor ao maior valor de pval 
passo = (max(pval) - min(pval)) / 1000;

% Aqui inicia o algoritmo descrito acima
for epsilon = min(pval):passo:max(pval)
    
    prev = (pval < epsilon);
    
    % ====================== COLOQUE SEU C�DIGO AQUI ======================
    % Voc� deve substituir o comando vp = 0 pelo calculo vetorial da 
    % quantidade de verdadeiros positovos, fp = 0 pelo calculo da
    % quantidade de falsos positivos e fn = 0 pelo c�lculo da quantidade de
    % falsos negativos
    vp = 0;
    fp = 0;
    fn = 0;
    % =====================================================================
    
    % ====================================================================
    % A partir daqui est� correto, n�o � necess�rio modificar 
    % C�lculo de prec
    prec = vp/(vp+fp);
    % C�lculo de rec
    rec = vp/(vp+fn);
    % C�lculo de F1
    F1 = (2*prec*rec) / (prec+rec);

    % Se o novo valor de F1 � melhor do que o melhor at� agora
    % Salva o valor atual de F1 e de epsilon em melhorF1 e melhorEpisilon
    if F1 > melhorF1
       melhorF1 = F1;
       melhorEpsilon = epsilon;
    end
    % =============================================================
end

end
