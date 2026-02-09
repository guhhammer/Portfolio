%% Cálculo de probabilidades por simulação
% 
%  Script para apoio de trabalho em sala de aula
%  Chama as seguintes funções que precisam ser programadas 
%
%     passeioAleatorioS.m
%     passeioAleatorioT.m
%

clear ; close all; clc

% ALUNO: GUSTAVO HAMMERSCHMIDT.


%% Obtém quantidade quantas pessoas para a qual deve ser calculada a probabilidade
n = input('Digite o tamanho(n) do caminho para o cálculo da probabilidade: ');

k = input('Digite a posição final(k): ');


%% Chama a função para cálculo analítico e imprime resultado

probT = passeioAleatorioT(n, k);

fprintf('Tamanho caminho(n): %d \n', n);
fprintf('Posição final(k): %d \n', k);
fprintf('Probabilidade calculada pela fórmula teórica: %f \n', probT);
fprintf('\n');

fprintf('Script suspenso \n');
fprintf('Digite qualquer tecla para continuar ou ctrl-c para abortar \n');
fprintf('\n');
pause;

nsimul = input('Digite a quantidade de vezes que deve ser realizada a simulação(nsimul): ');
fprintf('\n');

probS = passeioAleatorioS(n,k,nsimul);


fprintf('Tamanho caminho(n): %d \n', n);
fprintf('Posição final(k): %d \n', k);
fprintf('Quantidade de simulações: %d \n', nsimul);

fprintf('Probabilidade calculada pela simulação: %f \n', probS);
fprintf('\n');


