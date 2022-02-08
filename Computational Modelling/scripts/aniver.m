%% Cálculo de probabilidades por simulação
% 
%  %  Instruções
%  ------------
% 
%  Script para apoio de trabalho em sala de aula
%  Chama as seguintes funções que precisam ser programadas 
%
%     aniverA.m
%     aniverS.m
%
%  Para esse exercício será necessário aalterar algumas 
%  partes do código a seguir, conforme indicado.

%

%% Início
%% Limpar variáveis
clear ; close all; clc



% ALUNO: GUSTAVO HAMMERSCHMIDT.


%% Obtém quantidade quantas pessoas para a qual deve ser calculada a probabilidade
tamanho = input('Digite a quantidade de pessoas para a qual deve ser calculada a probabilidade: ');


%% Chama a função para cálculo analítico e imprime resultado

probA = aniverA(tamanho);
fprintf('Quantidade de pessoas: %d \n', tamanho);
fprintf('Probabilidade calculada pelo formula analitica: %f \n', probA);
fprintf('\n');

fprintf('Script suspenso \n');
fprintf('Digite qualquer tecla para continuar ou ctrl-c para abortar \n');
fprintf('\n');
pause;

%% Obtém quantidade de vezes que deve ser realizada a simulação

n = input('Digite a quantidade de vezes que deve ser realizada a simulação: ');
fprintf('\n');

%% Chama a função para cálculo por simulação e imprime resultado

probS = aniverS(tamanho, n);
fprintf('Quantidade de pessoas: %d \n', tamanho);
fprintf('Quantidade de simulacoes: %d \n', n);
fprintf('Probabilidade calculada pela simulação: %f \n', probS);
fprintf('\n');


