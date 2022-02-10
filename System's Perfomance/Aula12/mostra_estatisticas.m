function mostra_estatisticas (lambda, mu, m, nsim)

% Variaveis globais
global tamanho_da_fila
global limite_da_fila
global perda
global tamanho_fila_x_tempo
global tempo_do_ultimo_evento
global tempo_de_chegada_na_fila
global tempo_total_de_espera
global tempo
global Agenda
global nServidores
global estado_servidor
global tempo_de_servico_medio
global total_de_tempo_de_servico
global tempo_medio_entre_chegadas
global numero_de_eventos
global servidor
global estado_do_sistema
global estado_sistema_x_tempo



% ***********************************************************************
%                         Final da simulação
% ***********************************************************************
ro = lambda / (m*mu);
fprintf('--------------------------------------------------------------\n');
fprintf('Numero de eventos                                 : %d\n', numero_de_eventos);
fprintf('Lambda                                            : %.4f\n', lambda);
fprintf('Mu                                                : %.4f\n', mu);
fprintf('Nservidores                                       : %.4f\n', m);
fprintf('Ro                                                : %.4f\n', ro);
[ p0 eh ] = calcula_p0_eh (ro, m);
fprintf('epsilon                                           : %.4f\n', eh);
fprintf('P0                                                : %.4f\n', p0);
fprintf('Taxa de perda                                     : %.4f\n', perda/nsim);

fprintf('--------------------------------------------------------------\n');

% Estatisticas da simulação 
tempo_medio_na_fila_simulado = tempo_total_de_espera/numero_de_eventos;
tamanho_medio_da_fila_simulado = tamanho_fila_x_tempo / tempo;
tempo_medio_de_servico_simulado = total_de_tempo_de_servico/numero_de_eventos;
tempo_medio_de_resposta_simulado = tempo_medio_na_fila_simulado + tempo_medio_de_servico_simulado;

taxa_de_utilizacao_sistema = estado_sistema_x_tempo/tempo;


% Reporta as estatisticas da simulacao
   fprintf('Tempo medio na fila (simulado)                    : %.4f\n', tempo_medio_na_fila_simulado);
   fprintf('Numero medio de elementos na fila (simulado)      : %.4f\n', tamanho_medio_da_fila_simulado);
   fprintf('Tempo medio de servico (simulado)                 : %.4f\n', tempo_medio_de_servico_simulado);
   fprintf('Numero medio de elementos servidos (simulado)     : %.4f\n', lambda*tempo_medio_de_servico_simulado);
   fprintf('Tempo medio de resposta (simulado)                : %.4f\n', tempo_medio_de_resposta_simulado);
   fprintf('Numero medio de elemento no sistema (simulado)    : %.4f\n', lambda*tempo_medio_de_resposta_simulado);

   fprintf('Taxa de utilizacao do sistema (simulado)          : %.4f\n', taxa_de_utilizacao_sistema);
   
   fprintf('--------------------------------------------------------------\n');

% Calcula os parametros de retorno pelas formulas analiticas
tempo_medio_na_fila_analitico = eh /(m*mu*(1-ro));	% Tempo medio na fila
tamanho_medio_da_fila_analitico = (ro*eh) / (1-ro);		% Tamanho medio da fila
tempo_medio_de_resposta_analitico = (1/mu) + tempo_medio_na_fila_analitico;  % Tempo médio de resposta


% Reporta os valores analíticos
   fprintf('Tempo medio na fila (analítico)                   : %.4f\n', tempo_medio_na_fila_analitico);
   fprintf('Numero medio de elementos na fila (analitico)     : %.4f\n', tamanho_medio_da_fila_analitico);
   fprintf('Tempo medio de servico (analítico)                : %.4f\n', tempo_de_servico_medio);
   fprintf('Numero medio de elementos servidos (analitico)    : %.4f\n', lambda*tempo_de_servico_medio);
   fprintf('Tempo medio de resposta (analítico)               : %.4f\n', tempo_medio_de_resposta_analitico);    
   fprintf('Numero medio de elemento no sistema (analitico)   : %.4f\n', lambda*tempo_medio_de_resposta_analitico);

  
   fprintf('Taxa de utilização do sistema (analítico)         : %.4f\n', 1 - p0);
   
   fprintf('--------------------------------------------------------------\n\n');

