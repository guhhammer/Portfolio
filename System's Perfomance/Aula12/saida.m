function saida
% Simulacao do modelo MM1 - Funcao saida
% Proposito: Tratamento dos eventos de partida

% Variaveis globais
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



  if(tamanho_da_fila == 0) 	% Fila vazia
        % Marca o servidor como disponivel
      	estado_servidor(servidor) = 0; 
        % Momento de tratamento do proximo evento de fim de servico
        Agenda(servidor) = 1.0*exp(30);
        
        quantidade_servindo = sum(estado_servidor);  
        if (quantidade_servindo == 0)
            estado_do_sistema = 0;  % ocioso
        end

  else    % Fila nao vazia 
      
       % Acumula informcoes da entidade que saiu da fila e foi para o servidor
       % Serão usadas para calcular o tempo médio na fila
       espera = tempo - tempo_de_chegada_na_fila(1);                % Calcula tempo de espera na fila da entidade que vai ser atendida
       tempo_total_de_espera = tempo_total_de_espera + espera;      % Acumula tempo de espera na fila
       
       % Retira da fila
       tempo_de_chegada_na_fila(1)=[];              % Retira a primeira entidade da fila
       tamanho_da_fila = tamanho_da_fila - 1;		% Decrementa quantidade de eventos na fila
       
       % Programa o proximo evento de fim de servico
       tempo_de_servico = exprnd(tempo_de_servico_medio, 1, 1);
       Agenda(servidor) = tempo + tempo_de_servico;
       total_de_tempo_de_servico = total_de_tempo_de_servico + tempo_de_servico;


       
  end         