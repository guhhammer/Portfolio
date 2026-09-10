public class Main {


    private static String[] estados = {
            "Amazonas", "Para", "Roraima", "Amapa", "Acre", "Rondonia",
            "Tocantins", "Mato Grosso", "Goias", "Distrito Federal", "Mato Grosso do Sul",
            "Maranhao", "Ceara", "Piaui", "Rio Grande do Norte", "Paraiba", "Pernambuco",
            "Alagoas", "Sergipe", "Bahia", "Minas Gerais", "Espirito Santo", "Rio de Janeiro",
            "Sao Paulo", "Parana", "Santa Catarina", "Rio Grande do Sul"
    };

    public static void main(String[] args) {

        Estado amazonas = new Estado("Amazonas", -3.1346, -60.0233);
        Estado para = new Estado("Para", -1.4598,-48.4878);
        Estado roraima = new Estado("Roraima", 2.8166,-60.6705);
        Estado amapa = new Estado("Amapa", 0.0389, -51.0574);
        Estado acre = new Estado("Acre", -9.9782,-67.8105);
        Estado rondonia = new Estado("Rondonia", -8.7688, -63.8381);
        Estado tocantins = new Estado("Tocantins", -10.1632,-48.3510);
        Estado matoGrosso = new Estado("Mato Grosso",  -15.5989, -56.0949);
        Estado goias = new Estado("Goias",  -16.6799,  -49.255);
        Estado distritoFederal = new Estado("Distrito Federal",  -15.7801,  -47.9292 );
        Estado matoGrossoDoSul = new Estado("Mato Grosso do Sul",  -20.4435,  -54.6478);
        Estado maranhao = new Estado("Maranhao",  -2.5307,  -44.3068);
        Estado ceara = new Estado("Ceara",  -3.7183,  -38.5434);
        Estado piaui = new Estado("Piaui",  -5.0892,  -42.8016);
        Estado rioGrandeDoNorte = new Estado("Rio Grande do Norte",  -5.79448,  -35.2110);
        Estado paraiba = new Estado("Paraiba", -7.1153, -34.861);
        Estado pernambuco = new Estado("Pernambuco",  -8.0542, -34.8813);
        Estado alagoas = new Estado("Alagoas",-9.6608,-35.7016);
        Estado sergipe = new Estado("Sergipe",-10.9072,-37.0482);
        Estado bahia = new Estado("Bahia",-13.0147,-38.488);
        Estado minasGerais = new Estado("Minas Gerais",-19.9375,-45.9264);
        Estado espiritoSanto = new Estado("Espirito Santo",-20.3383,-40.2939);
        Estado rioDeJaneiro = new Estado("Rio de Janeiro",-22.8766,-43.2278);
        Estado saoPaulo = new Estado("Sao Paulo",-23.5673,-46.5703);
        Estado parana = new Estado("Parana",-25.4329,-49.2718);
        Estado santaCatarina = new Estado("Santa Catarina",-27.5877,-48.5476);
        Estado rioGrandeDoSul = new Estado("Rio Grande do Sul",-30.03,-51.2286);


        amazonas.addEstado(roraima);
        amazonas.addEstado(para);
        amazonas.addEstado(acre);
        amazonas.addEstado(rondonia);
        amazonas.addEstado(matoGrosso);
        para.addEstado(roraima);
        para.addEstado(amazonas);
        para.addEstado(amapa);
        para.addEstado(maranhao);
        para.addEstado(tocantins);
        para.addEstado(matoGrosso);
        para.addEstado(rondonia);
        roraima.addEstado(amazonas);
        roraima.addEstado(para);
        amapa.addEstado(para);
        acre.addEstado(amazonas);
        rondonia.addEstado(amazonas);
        roraima.addEstado(matoGrosso);
        tocantins.addEstado(para);
        tocantins.addEstado(maranhao);
        tocantins.addEstado(piaui);
        tocantins.addEstado(bahia);
        tocantins.addEstado(goias);
        tocantins.addEstado(matoGrosso);
        matoGrosso.addEstado(amazonas);
        matoGrosso.addEstado(rondonia);
        matoGrosso.addEstado(matoGrossoDoSul);
        matoGrosso.addEstado(goias);
        matoGrosso.addEstado(tocantins);
        matoGrosso.addEstado(para);
        goias.addEstado(matoGrosso);
        goias.addEstado(matoGrossoDoSul);
        goias.addEstado(saoPaulo);
        goias.addEstado(minasGerais);
        goias.addEstado(bahia);
        goias.addEstado(tocantins);
        goias.addEstado(distritoFederal);
        distritoFederal.addEstado(goias);
        matoGrossoDoSul.addEstado(matoGrosso);
        matoGrossoDoSul.addEstado(goias);
        matoGrossoDoSul.addEstado(minasGerais);
        matoGrossoDoSul.addEstado(saoPaulo);
        matoGrossoDoSul.addEstado(parana);
        maranhao.addEstado(para);
        maranhao.addEstado(tocantins);
        maranhao.addEstado(bahia);
        maranhao.addEstado(piaui);
        ceara.addEstado(rioGrandeDoNorte);
        ceara.addEstado(paraiba);
        ceara.addEstado(pernambuco);
        ceara.addEstado(piaui);
        piaui.addEstado(maranhao);
        piaui.addEstado(tocantins);
        piaui.addEstado(bahia);
        piaui.addEstado(pernambuco);
        piaui.addEstado(ceara);
        piaui.addEstado(distritoFederal);
        rioGrandeDoNorte.addEstado(ceara);
        rioGrandeDoNorte.addEstado(paraiba);
        paraiba.addEstado(rioGrandeDoNorte);
        paraiba.addEstado(ceara);
        paraiba.addEstado(pernambuco);
        pernambuco.addEstado(paraiba);
        pernambuco.addEstado(ceara);
        pernambuco.addEstado(piaui);
        pernambuco.addEstado(bahia);
        pernambuco.addEstado(alagoas);
        alagoas.addEstado(pernambuco);
        alagoas.addEstado(bahia);
        alagoas.addEstado(sergipe);
        bahia.addEstado(pernambuco);
        bahia.addEstado(alagoas);
        bahia.addEstado(sergipe);
        bahia.addEstado(piaui);
        bahia.addEstado(maranhao);
        bahia.addEstado(tocantins);
        bahia.addEstado(goias);
        bahia.addEstado(minasGerais);
        bahia.addEstado(espiritoSanto);
        sergipe.addEstado(bahia);
        sergipe.addEstado(alagoas);
        minasGerais.addEstado(espiritoSanto);
        minasGerais.addEstado(bahia);
        minasGerais.addEstado(goias);
        minasGerais.addEstado(matoGrossoDoSul);
        minasGerais.addEstado(saoPaulo);
        minasGerais.addEstado(rioDeJaneiro);
        espiritoSanto.addEstado(bahia);
        espiritoSanto.addEstado(minasGerais);
        espiritoSanto.addEstado(rioDeJaneiro);
        rioDeJaneiro.addEstado(espiritoSanto);
        rioDeJaneiro.addEstado(minasGerais);
        rioDeJaneiro.addEstado(saoPaulo);
        saoPaulo.addEstado(rioDeJaneiro);
        saoPaulo.addEstado(minasGerais);
        saoPaulo.addEstado(matoGrossoDoSul);
        saoPaulo.addEstado(parana);
        parana.addEstado(saoPaulo);
        parana.addEstado(matoGrossoDoSul);
        parana.addEstado(santaCatarina);
        santaCatarina.addEstado(parana);
        santaCatarina.addEstado(rioGrandeDoSul);
        rioGrandeDoSul.addEstado(santaCatarina);

        Grafo g = new Grafo();
        g.add(amazonas);        g.add(acre);            g.add(roraima);
        g.add(amapa);           g.add(para);            g.add(rondonia);
        g.add(tocantins);       g.add(matoGrosso);      g.add(goias);
        g.add(distritoFederal); g.add(matoGrossoDoSul); g.add(maranhao);
        g.add(ceara);           g.add(piaui);           g.add(rioGrandeDoNorte);
        g.add(paraiba);         g.add(pernambuco);      g.add(alagoas);
        g.add(sergipe);         g.add(bahia);           g.add(minasGerais);
        g.add(espiritoSanto);   g.add(rioDeJaneiro);    g.add(saoPaulo);
        g.add(parana);          g.add(santaCatarina);   g.add(rioGrandeDoSul);

        System.out.println("\n");


        // =========================================================================================
        // =========================================================================================
        // =========================================================================================

        // Output dos exercícios da tarefa 3. Não são o foco desta atividade.
        boolean ok = false;
        if(ok){

            // Ex.5
            String caminho_ex5 = BLargura.buscaLargura(g, parana.getNome(), bahia.getNome());
            System.out.println("Ex.5: "+caminho_ex5+"\n");
            // End.

            // Ex.6
            String caminho_ex6 = BProfundidade.buscaProfundidade(g, parana.getNome(), bahia.getNome());
            System.out.println("Ex.6: "+caminho_ex6+"\n");
            // End.

            // Ex.7
            // Profundidade começa em 1.
            // (1) Paraná -> (2) São Paulo -> (3a) Rio de Janeiro -> (4a) Espirito Santo
            //                             -> (3b) Minas Gerais -> (4b) Bahia.
            // Return: (1) -> (2) -> (3a) -> (4a) -> (3b) -> (4b).
            //
            String caminho_ex7 = BProfundidade.buscaProfundidadeLimitada(g, parana.getNome(), bahia.getNome(), 4);
            System.out.println("Ex.7: "+caminho_ex7+"\n");
            // End.

            // Ex.8
            String caminho_ex8 = BProfundidade.buscaAprofundamentoIterado(g, parana.getNome(), bahia.getNome());
            System.out.println("Ex.8: "+caminho_ex8+"\n");
            // End.


        }


        // h(n) e g(n) são passados no algoritmo de busca na classe BHeuristica por motivos de
        // simplificação.


        // Utiliza apenas h(n). Avalia, para n, a distância até o estado objetivo.
        String caminho_heuristico = BHeuristica.buscaGulosa(g, parana.getNome(), bahia.getNome());
        System.out.println("Busca Gulosa: \n\t\t"+caminho_heuristico+"\n");


        // Utiliza h(n) e g(n). Avalia, para n, a menor distância da próxima cidade em relação
        // a cidade de início e em relação à final. Ou seja, avalia a média das distâncias do início
        // ao meio e do meio ao fim.
        String caminho_a_estrela = BHeuristica.A_estrela(g, parana.getNome(), bahia.getNome());
        System.out.println("Busca A*: \n\t\t"+caminho_a_estrela+"\n");


        // Utiliza h(n) e g(n). Avalia, para n, a menor distância da próxima cidade em relação
        // a cidade de início e em relação à final. Ou seja, avalia a média das distâncias do início
        // ao meio e do meio ao fim.

        String caminho_a_estrela_iterativo = BHeuristica.A_estrela_iterativo(g, parana.getNome(), bahia.getNome());
        System.out.println("Busca A* iterativa: \n"+caminho_a_estrela_iterativo+"\n");




        System.out.println("\n\n\tExecuting Timing Phase...\n\n");

        long tempos[] = new long[]{ 0, 0, 0, 0, 0 }, track;

        int exec_counter = 0;

        do{

            String start = estados[(int)Math.floor(Math.random() * 27)];
            String end = estados[(int)Math.floor(Math.random() * 27)];

            // Busca Profundidade
            track = System.currentTimeMillis();

                BProfundidade.buscaProfundidade(g, start, end);

            tempos[0] = tempos[0] + (System.currentTimeMillis()-track);

            // Busca Largura
            track = System.currentTimeMillis();

                BLargura.buscaLargura(g, start, end);

            tempos[1] = tempos[1] + (System.currentTimeMillis()-track);

            // Busca Gulosa
            track = System.currentTimeMillis();

                BHeuristica.buscaGulosa(g, start, end);

            tempos[2] = tempos[2] + (System.currentTimeMillis()-track);

            // Busca A*
            track = System.currentTimeMillis();

                BHeuristica.A_estrela(g, start, end);

            tempos[3] = tempos[3] + (System.currentTimeMillis()-track);

            // Busca A* iterativa
            track = System.currentTimeMillis();

                BHeuristica.A_estrela_iterativo(g, start, end);

            tempos[4] = tempos[4] + (System.currentTimeMillis()-track);

            exec_counter++;

            if( exec_counter % 50 == 49 ){
                System.out.println("\t\t\tPhase "+exec_counter+"/500.");
            }

        }while(exec_counter < 500);

        System.out.println("\n\n");


        float[] ans = new float[tempos.length];
        for(int i = 0; i < tempos.length; i++){ ans[i] = ((float)tempos[i])/exec_counter ; }


        System.out.print("\tExecution Average Time:\n\t"+"-".repeat(67));
        System.out.format("\n\t| %s | %s | %8s | %8s | %s |", "BProfundidade", "BLargura", "BGulosa", "B_A*", "B_A*_Iterativa");
        System.out.format("\n\t| %10.3f ms | %5.3f ms | %4.3f ms | %3.3f ms | %11.3f ms |", ans[0], ans[1], ans[2], ans[3], ans[4]);
        System.out.print("\n\t"+"-".repeat(67)+"\n");

    }


}
