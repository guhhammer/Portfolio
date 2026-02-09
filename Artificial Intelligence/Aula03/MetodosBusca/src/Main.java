public class Main {

    public static void main(String[] args) {

        Estado amazonas = new Estado("Amazonas");
        Estado para = new Estado("Para");
        Estado roraima = new Estado("Roraima");
        Estado amapa = new Estado("Amapa");
        Estado acre = new Estado("Acre");
        Estado rondonia = new Estado("Rondonia");
        Estado tocantins = new Estado("Tocantins");
        Estado matoGrosso = new Estado("Mato Grosso");
        Estado goias = new Estado("Goias");
        Estado distritoFederal = new Estado("Distrito Federal");
        Estado matoGrossoDoSul = new Estado("Mato Grosso do Sul");
        Estado maranhao = new Estado("Maranhao");
        Estado ceara = new Estado("Ceara");
        Estado piaui = new Estado("Piaui");
        Estado rioGrandeDoNorte = new Estado("Rio Grande do Norte");
        Estado paraiba = new Estado("Paraiba"), pernambuco = new Estado("Pernambuco");
        Estado alagoas = new Estado("Alagoas");
        Estado sergipe = new Estado("Sergipe");
        Estado bahia = new Estado("Bahia");
        Estado minasGerais = new Estado("Minas Gerais");
        Estado espiritoSanto = new Estado("Espirito Santo");
        Estado rioDeJaneiro = new Estado("Rio de Janeiro");
        Estado saoPaulo = new Estado("Sao Paulo");
        Estado parana = new Estado("Parana");
        Estado santaCatarina = new Estado("Santa Catarina");
        Estado rioGrandeDoSul = new Estado("Rio Grande do Sul");

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
        System.out.println("Ex.8: "+caminho_ex8);
        // End.

    }

}
