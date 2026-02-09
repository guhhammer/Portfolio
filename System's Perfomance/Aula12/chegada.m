function chegada				
% Simulacao modelo MM1 - Funcao chegada
% Proposito: Tratamento dos eventos de chegada

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





  quantidade_servindo = sum(estado_servidor);

  if (quantidade_servindo < nServidores)
       servidor = find(estado_servidor == min(estado_servidor),1);
       estado_servidor(servidor) = 1; % Marca o servidor como ocupado
       estado_do_sistema = 1;  % Sistema não ocioso

     % Agenda a saida da entidade que está no servidor
       tempo_de_servico = exprnd(tempo_de_servico_medio, 1, 1);
       Agenda(servidor) = tempo + tempo_de_servico;
       total_de_tempo_de_servico = total_de_tempo_de_servico + tempo_de_servico;
       
  else % Todos servidores ocupados
     % Coloca entidade na fila
     % Incrementa numero de eventos na fila
     tamanho_da_fila = tamanho_da_fila + 1 ; 
     % Salva tempo de chegada da entidade que foi colocado na fila
     tempo_de_chegada_na_fila(tamanho_da_fila) = tempo; 
     
     % Testa e informa se houve estouro na fila
     if(tamanho_da_fila > limite_da_fila) 
        perda = perda + 1;
        %fprintf('tamanho_da_fila = %d\n', tamanho_da_fila);
        %fprintf('Estouro do tamanho da fila no tempo  %f\n\n',tempo);	
        %pause
     end

  end

% Agenda a proxima chegada
  Agenda(nServidores+1) = tempo + exprnd(tempo_medio_entre_chegadas, 1, 1);

% Incrementa quantidade de eventos que passaram pelo sistema
% É usado para calcular o tempo médio na fila
  numero_de_eventos = numero_de_eventos + 1;

