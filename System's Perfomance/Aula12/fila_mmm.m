function [ro, E_Nq, E_W, E_R] = fila_mmm (lambda, mu, m, tfila, nsim)
% Simulação do modelo MM1 - Função principal

% Argumentos da simulação:
% tfila             = tamanho máximo da fila
% lambda			= taxa de chegada
% mu				= taxa de serviço
% nsim		 		= quantidade de eventos simulados


% Funções chamadas:
% atualiza.m (atualiza os acumuladores)
% chegada.m (rotina de chegada)
% saida.m (rotina de saida)

% Variaveis globais
global tamanho_da_fila
global limite_da_fila
global perda
global tamanho_fila_x_tempo
global tempo_do_ultimo_evento
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



% **********************************************************************
%                            Inicia variaveis globais
% **********************************************************************
nServidores = m;
Agenda = zeros(1, m+1);

limite_da_fila = tfila;                   % tamanho limite da fila
perda = 0;
tempo_medio_entre_chegadas = 1/lambda;	  % tempo médio entre chegadas  
tempo_de_servico_medio=1/mu;              % tempo médio de serviço          

estado_servidor = zeros(1, nServidores);  % estado do servidor (livre)
tamanho_da_fila = 0;                      % tamanho da fila
tempo_do_ultimo_evento = 0.0;             % data de ocorrência do último evento 
tempo_total_de_espera = 0.0;              % tempo total de espera na fila
total_de_tempo_de_servico = 0.0;          % total de tempo de servico
tamanho_fila_x_tempo = 0.0;               % tamanho da fila multiplicado pela duracao do ultimo evento

estado_do_sistema = 0;
estado_sistema_x_tempo = 0.0;             % contador area_serve_status

% Inicializa o tempo de ocorrência da primeira partida com valor muito
% grande
% Agenda(1, 1:2) = 1.0*exp(30);   

% Agenda a primeira chegada
tempo = exprnd(tempo_medio_entre_chegadas, 1, 1);
Agenda(1, nServidores+1) = tempo;

% Inicializa quantidade de eventos que passam pelo sistema
numero_de_eventos = 1; 

   
% ***********************************************************************
% ********      Executa laço de simulação                   *************
% ***********************************************************************

while(numero_de_eventos < nsim)

  proximo_evento = find(Agenda == min(Agenda),1);
  if (proximo_evento > m)
      tipo_proximo_evento = 1; % chegada
  else
      tipo_proximo_evento = 0; % saída
      servidor = proximo_evento;
  end
  
  tempo = Agenda(proximo_evento);

  % Atualizar os acumuladores estatisticos
  atualiza; 			 	

% Processar o próximo evento  
  if (tipo_proximo_evento == 1)
      chegada;          % Chama rotina de chegada
  else
      saida;			% Chama rotina de saida
  end

end

mostra_estatisticas(lambda, mu, m, nsim);

% Estatisticas da simulação 
ro = lambda/(m*mu);
E_Nq = tamanho_fila_x_tempo / tempo;
E_W = tempo_total_de_espera/numero_de_eventos;
E_S = total_de_tempo_de_servico/numero_de_eventos;
E_R = E_W + E_S;

