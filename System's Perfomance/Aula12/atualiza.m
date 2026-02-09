function atualiza
% Simulacao do modelo MM1 - Funcao atualiza

% Proposito: atualizar area acumulada para estatisticas temporais
%   Atualiza tempo do ultimo evento
%   Atualiza variaveis relacionadas com o tempo para estatisticas

% Variaveis globais
% global estado_servidor 
% global estado_servidor_x_tempo
global tamanho_da_fila
global limite_da_fila
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




  % Calcula intervalo decorrido desde ultimo evento  
  % Na primeira chamada tempo_do_ultimo_evento é igual a 0 (iniciado no procedimento principal)
  % Variável tempo é atualizada na função timing
  % Variável  tempo_do_ultimo_evento é atualizada aqui
  intervalo_do_ultimo_evento = tempo - tempo_do_ultimo_evento;
  tempo_do_ultimo_evento = tempo;
 
  % Acumula quantidade de elementos na fila multiplicado pelo tempo decorrido desde o ultimo evento
  % A divisão da variável tamanho_fila_x_tempo pela variável tempo vai fornecer o tamanho médio da fila
  % A variável tamanho_da_fila é atualizada em arrive e depert
  tamanho_fila_x_tempo = tamanho_fila_x_tempo + tamanho_da_fila * intervalo_do_ultimo_evento ;			

% Acumula estado do servidor multiplicado pelo tempo decorrido desde o ultimo evento
% A divisão da variável estado_servidor_x_tempo pela variável tempo vai fornecer a utlização do servidor
% A vairiável estado_do_servidor é atualizada em arrive e depert
  estado_sistema_x_tempo = estado_sistema_x_tempo + estado_do_sistema * intervalo_do_ultimo_evento ;