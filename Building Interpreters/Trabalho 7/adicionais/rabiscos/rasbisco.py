





def vocabulario(docs):
    vocab = []
    for i in docs:
        for j in i.aplit(" "):
            flag = True
            for k in vocab:
                if i == k:
                    flag = False
            if flag:
                vocab.append(j)











D1 = "Shipment of gold damaged in a fire."
D2 = "Delivery of silver arrived in a silver truck."
D3 = "Shipment of gold arrived in a truck."




corpus=[("Brasil poderá ter uma presidente mulher, diz Dilma: Declaração foi dada após encontro com Michelle Bachelet em SP. A sociedade está madura para isso, disse a ministra. "),
        ("Justiça brasileira existe e se fez valer, diz Protógenes: Delegado da PF comentou arquivamento do processo do juiz De Sanctis. Em festa de 1º de Maio, ele disse que está na iminência do desemprego. "),
        ("1,7 milhão já trocaram de operadora sem mudar número de telefone: Dados foram divulgados nesta segunda-feira (27) pela ABR Telecom. Total de solicitações de portabilidade numérica já é de 2,3 milhões. "),
        ("2,1 mil servidores foram expulsos do governo desde 2003: Expulsões ocorreram devido a envolvimento em atos de corrupção. CGU diz que cultura de impunidade\' está mudando. "),
        ("50 quilos de droga são apreendidos no Paraná: Crack, pasta base de cocaína e haxixe estavam em caminhonete. Motorista disse que receberia R$ 6 mil para levar droga a Curitiba. "),
        ("50 soldados sírios morrem em explosão de carro-bomba, diz grupo ativista. Pelo menos 50 pessoas ligadas ao governo do presidente da Síria, Bashar Al Assad, morreram nesta semana. "),
        ("70% do Conselho de Ética é suspeito de irregularidades: Levantamento de jornal mostra 21 ligados a escândalos recentes. Senadores decidirão sobre abertura de processo de cassação de Sarney. "),
        ("9 cidades do AM terão recursos para combater DSTs e hepatites virais. Portaria que autoriza o repasse foi publicada no Diário Oficial da União. Valor anual chega a R$ 2.876.461,0, diz Ministério da Saúde. "),
        ("A 8 dias da Copa, rotatória perto da Arena Pantanal ainda está em obras. Ampliação na frente do Círculo Militar começou a ser feita no mês passado. Primeiro jogo do mundial em Cuiabá será no dia 13, entre Chile e Austrália. "),
        ("Abastecimento d\'água em 14 bairros de Natal será normalizado nesta quarta. Informação é da Companhia de Águas e Esgotos do Rio Grande do Norte. Vazamentos foram causados pelas obras de mobilidade e defeito em adutora. "),
        ("Abbas pede a Israel o fim da \'escalada\' militar contra Gaza. Foguete lançado por Gaza caiu no sul de Israel, sem deixar feridos. Outros projéteis foram lançados, mas não atingiram o país. "),
        ("Ação desmantela quadrilha suspeita de explorar prostituição de travestis. Caso registrado em Cascavel (PR) vem sendo investigado há 13 meses. Segundo a polícia, vítimas eram obrigadas a pagar por ponto e serviços. "),
        ("Ação nas praias da Paraíba quer ajudar na preservação do peixe-boi. Campanha que está abordando banhistas já passou por 28 praias. Abordagem ensina como proceder ao encontrar animal encalhado. "),
        ("Achado arqueológico sugere que lepra surgiu na Índia há cerca de 4.000 anos: Pesquisadores debatiam se origem da doença era asiática ou africana. Apesar de fácil detecção e cura, moléstia ainda persiste no mundo. "),
        ("Acidente com ônibus e trem mata 47 crianças no sul do Egito. Outras 13 crianças ficaram gravemente feridas. Também morreram o motorista do veículo e duas pessoas responsáveis pelos alunos. "),
        ("Acidente de helicóptero deixa 4 mortos na Inglaterra. Um acidente de helicóptero causou a morte de quatro pessoas ontem à noite no condado de Norfolk, no leste da Inglaterra, informou nesta sexta-feira a polícia. "),
        ("Acidente de ônibus mata 16 na Bulgária: Veículo teve falha nos freios, segundo testemunhas. Vinte pessoas ficaram feridas. "),
        ("Acidente de ônibus mata pelo menos 15 pessoas na Colômbia. Veículo teria explodido e a maioria dos mortos são crianças. Presidente do país lamentou a tragédia por meio de uma rede social. "),
        ("Acidente deixa 2 mortos e 51 feridos no Texas. O desastre ocorreu no feriado de Ação de Graças, quando milhões de norte-americanos pegam as estradas e viajam. "),
        ("Acidente deixa 4 mortos e 2 feridos em rodovia mineira: Segundo a PRF, carreta tombou em uma curva na BR-452. Veículo arrastou um carro e outros três caminhões. "),
        ("Acidente deixa três mortos e dois feridos na BR-101. Uma colisão envolvendo uma carreta, uma caçamba e um carro-forte deixou três mortos e dois feridos na manhã desta sexta-feira, no km 207, da BR-101, trecho de Governador Mangabeira (a 119 quilômetros de Salvador). "),
        ("Acidente em mina deixa 22 mortos na China. Acidente ocorreu em uma mina de carvão no sudoeste do país. Causa foi um escapamento de gás. "),
        ("Acidente entre caminhão e dois carros deixa quatro feridos no DF. Colisão aconteceu na BR-080, na entrada de Brazlândia, sentido Ceilândia. Uma das vítimas ficou presa nas ferragens, segundo o Corpo de Bombeiros. "),
        ("Acidente entre carretas deixa dois mortos em MT: Dois veículos transportavam soja e óleo lubrificante. Segundo a polícia, vítimas morreram após colisão em uma curva. "),
        ("Acidente entre van e caminhão deixa 2 mortos em MG: Veículo com 12 passageiros bateu em caminhão e capotou na pista. Outras cinco pessoas ficaram feridas. "),
        ("Acidente mata dois homens e criança de seis anos em rodovia de MT. Dois motoristas e um menino morreram após acidente na MT-235. Mulher foi socorrida em estado grave até hospital da região. "),
        ("Acidentes com motos matam 10 mil em um ano, diz pesquisa: Nos últimos dez anos, o número de mortes aumentou 1.000%. Em 14 estados, óbitos de motociclistas superaram os de pedestres. "),
        ("Aciub contesta lei que altera tamanho de lotes para indústrias em Uberlândia. Representante da associação diz que faltou comunicação com entidades. Lei visa alterar tamanho dos terrenos de 2,5 mil metros quadrados para mil. "),
        ("Acusado de matar menina de 5 anos diz não lembrar onde está o corpo. Britânico Mark Bridger é acusado de assassinar April Jones, de 5 anos. "),
        ("Acusado de queimar e jogar corpo de ex em lixão pega 27 anos de prisão. Crime ocorreu em 2011 na cidade de Brasiléia. Defesa criticou pena e recorreu da decisão. ")]



























q = "gold silver truck"
