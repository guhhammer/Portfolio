/** Builds the graph of the 27 Brazilian states (adjacency + capital coordinates)
 *  and runs breadth-first, depth-first, depth-limited, iterative-deepening,
 *  greedy and A* searches between two states, then times each over 500 runs.
 *
 *  Artificial Intelligence course, PUCPR (2021).
 */
public class Main {

    private static final String[] stateNames = {
            "Amazonas", "Para", "Roraima", "Amapa", "Acre", "Rondonia",
            "Tocantins", "Mato Grosso", "Goias", "Distrito Federal", "Mato Grosso do Sul",
            "Maranhao", "Ceara", "Piaui", "Rio Grande do Norte", "Paraiba", "Pernambuco",
            "Alagoas", "Sergipe", "Bahia", "Minas Gerais", "Espirito Santo", "Rio de Janeiro",
            "Sao Paulo", "Parana", "Santa Catarina", "Rio Grande do Sul"
    };

    private static Graph buildBrazil() {
        State amazonas = new State("Amazonas", -3.1346, -60.0233);
        State para = new State("Para", -1.4598, -48.4878);
        State roraima = new State("Roraima", 2.8166, -60.6705);
        State amapa = new State("Amapa", 0.0389, -51.0574);
        State acre = new State("Acre", -9.9782, -67.8105);
        State rondonia = new State("Rondonia", -8.7688, -63.8381);
        State tocantins = new State("Tocantins", -10.1632, -48.3510);
        State matoGrosso = new State("Mato Grosso", -15.5989, -56.0949);
        State goias = new State("Goias", -16.6799, -49.255);
        State distritoFederal = new State("Distrito Federal", -15.7801, -47.9292);
        State matoGrossoDoSul = new State("Mato Grosso do Sul", -20.4435, -54.6478);
        State maranhao = new State("Maranhao", -2.5307, -44.3068);
        State ceara = new State("Ceara", -3.7183, -38.5434);
        State piaui = new State("Piaui", -5.0892, -42.8016);
        State rioGrandeDoNorte = new State("Rio Grande do Norte", -5.79448, -35.2110);
        State paraiba = new State("Paraiba", -7.1153, -34.861);
        State pernambuco = new State("Pernambuco", -8.0542, -34.8813);
        State alagoas = new State("Alagoas", -9.6608, -35.7016);
        State sergipe = new State("Sergipe", -10.9072, -37.0482);
        State bahia = new State("Bahia", -13.0147, -38.488);
        State minasGerais = new State("Minas Gerais", -19.9375, -45.9264);
        State espiritoSanto = new State("Espirito Santo", -20.3383, -40.2939);
        State rioDeJaneiro = new State("Rio de Janeiro", -22.8766, -43.2278);
        State saoPaulo = new State("Sao Paulo", -23.5673, -46.5703);
        State parana = new State("Parana", -25.4329, -49.2718);
        State santaCatarina = new State("Santa Catarina", -27.5877, -48.5476);
        State rioGrandeDoSul = new State("Rio Grande do Sul", -30.03, -51.2286);

        amazonas.addNeighbour(roraima); amazonas.addNeighbour(para); amazonas.addNeighbour(acre);
        amazonas.addNeighbour(rondonia); amazonas.addNeighbour(matoGrosso);
        para.addNeighbour(roraima); para.addNeighbour(amazonas); para.addNeighbour(amapa);
        para.addNeighbour(maranhao); para.addNeighbour(tocantins); para.addNeighbour(matoGrosso); para.addNeighbour(rondonia);
        roraima.addNeighbour(amazonas); roraima.addNeighbour(para); roraima.addNeighbour(matoGrosso);
        amapa.addNeighbour(para); acre.addNeighbour(amazonas); rondonia.addNeighbour(amazonas);
        tocantins.addNeighbour(para); tocantins.addNeighbour(maranhao); tocantins.addNeighbour(piaui);
        tocantins.addNeighbour(bahia); tocantins.addNeighbour(goias); tocantins.addNeighbour(matoGrosso);
        matoGrosso.addNeighbour(amazonas); matoGrosso.addNeighbour(rondonia); matoGrosso.addNeighbour(matoGrossoDoSul);
        matoGrosso.addNeighbour(goias); matoGrosso.addNeighbour(tocantins); matoGrosso.addNeighbour(para);
        goias.addNeighbour(matoGrosso); goias.addNeighbour(matoGrossoDoSul); goias.addNeighbour(saoPaulo);
        goias.addNeighbour(minasGerais); goias.addNeighbour(bahia); goias.addNeighbour(tocantins); goias.addNeighbour(distritoFederal);
        distritoFederal.addNeighbour(goias);
        matoGrossoDoSul.addNeighbour(matoGrosso); matoGrossoDoSul.addNeighbour(goias); matoGrossoDoSul.addNeighbour(minasGerais);
        matoGrossoDoSul.addNeighbour(saoPaulo); matoGrossoDoSul.addNeighbour(parana);
        maranhao.addNeighbour(para); maranhao.addNeighbour(tocantins); maranhao.addNeighbour(bahia); maranhao.addNeighbour(piaui);
        ceara.addNeighbour(rioGrandeDoNorte); ceara.addNeighbour(paraiba); ceara.addNeighbour(pernambuco); ceara.addNeighbour(piaui);
        piaui.addNeighbour(maranhao); piaui.addNeighbour(tocantins); piaui.addNeighbour(bahia);
        piaui.addNeighbour(pernambuco); piaui.addNeighbour(ceara); piaui.addNeighbour(distritoFederal);
        rioGrandeDoNorte.addNeighbour(ceara); rioGrandeDoNorte.addNeighbour(paraiba);
        paraiba.addNeighbour(rioGrandeDoNorte); paraiba.addNeighbour(ceara); paraiba.addNeighbour(pernambuco);
        pernambuco.addNeighbour(paraiba); pernambuco.addNeighbour(ceara); pernambuco.addNeighbour(piaui);
        pernambuco.addNeighbour(bahia); pernambuco.addNeighbour(alagoas);
        alagoas.addNeighbour(pernambuco); alagoas.addNeighbour(bahia); alagoas.addNeighbour(sergipe);
        bahia.addNeighbour(pernambuco); bahia.addNeighbour(alagoas); bahia.addNeighbour(sergipe); bahia.addNeighbour(piaui);
        bahia.addNeighbour(maranhao); bahia.addNeighbour(tocantins); bahia.addNeighbour(goias);
        bahia.addNeighbour(minasGerais); bahia.addNeighbour(espiritoSanto);
        sergipe.addNeighbour(bahia); sergipe.addNeighbour(alagoas);
        minasGerais.addNeighbour(espiritoSanto); minasGerais.addNeighbour(bahia); minasGerais.addNeighbour(goias);
        minasGerais.addNeighbour(matoGrossoDoSul); minasGerais.addNeighbour(saoPaulo); minasGerais.addNeighbour(rioDeJaneiro);
        espiritoSanto.addNeighbour(bahia); espiritoSanto.addNeighbour(minasGerais); espiritoSanto.addNeighbour(rioDeJaneiro);
        rioDeJaneiro.addNeighbour(espiritoSanto); rioDeJaneiro.addNeighbour(minasGerais); rioDeJaneiro.addNeighbour(saoPaulo);
        saoPaulo.addNeighbour(rioDeJaneiro); saoPaulo.addNeighbour(minasGerais); saoPaulo.addNeighbour(matoGrossoDoSul); saoPaulo.addNeighbour(parana);
        parana.addNeighbour(saoPaulo); parana.addNeighbour(matoGrossoDoSul); parana.addNeighbour(santaCatarina);
        santaCatarina.addNeighbour(parana); santaCatarina.addNeighbour(rioGrandeDoSul);
        rioGrandeDoSul.addNeighbour(santaCatarina);

        Graph g = new Graph();
        for (State s : new State[]{ amazonas, acre, roraima, amapa, para, rondonia, tocantins, matoGrosso, goias,
                distritoFederal, matoGrossoDoSul, maranhao, ceara, piaui, rioGrandeDoNorte, paraiba, pernambuco,
                alagoas, sergipe, bahia, minasGerais, espiritoSanto, rioDeJaneiro, saoPaulo, parana, santaCatarina,
                rioGrandeDoSul }) {
            g.add(s);
        }
        return g;
    }

    public static void main(String[] args) {
        Graph g = buildBrazil();
        String start = "Parana", end = "Bahia";

        System.out.println("\nUninformed search from " + start + " to " + end + ":\n");
        System.out.println("Breadth-first:        " + BreadthFirstSearch.breadthFirst(g, start, end));
        System.out.println("Depth-first:          " + DepthFirstSearch.depthFirst(g, start, end));
        System.out.println("Depth-limited (4):    " + DepthFirstSearch.depthLimited(g, start, end, 4));
        System.out.println("Iterative deepening:  " + DepthFirstSearch.iterativeDeepening(g, start, end));

        System.out.println("\nInformed search from " + start + " to " + end + ":\n");
        System.out.println("Greedy:               " + HeuristicSearch.greedy(g, start, end));
        System.out.println("A*:                   " + HeuristicSearch.aStar(g, start, end));
        System.out.println("A* iterative:         " + HeuristicSearch.aStarIterative(g, start, end));

        System.out.println("\n\tTiming phase (500 random start/end pairs)...\n");
        long[] times = new long[5];
        long track;
        int runs = 500;
        for (int e = 0; e < runs; e++) {
            String s = stateNames[(int) Math.floor(Math.random() * 27)];
            String t = stateNames[(int) Math.floor(Math.random() * 27)];

            track = System.currentTimeMillis(); DepthFirstSearch.depthFirst(g, s, t); times[0] += System.currentTimeMillis() - track;
            track = System.currentTimeMillis(); BreadthFirstSearch.breadthFirst(g, s, t); times[1] += System.currentTimeMillis() - track;
            track = System.currentTimeMillis(); HeuristicSearch.greedy(g, s, t); times[2] += System.currentTimeMillis() - track;
            track = System.currentTimeMillis(); HeuristicSearch.aStar(g, s, t); times[3] += System.currentTimeMillis() - track;
            track = System.currentTimeMillis(); HeuristicSearch.aStarIterative(g, s, t); times[4] += System.currentTimeMillis() - track;
        }

        float[] avg = new float[times.length];
        for (int i = 0; i < times.length; i++) { avg[i] = ((float) times[i]) / runs; }

        System.out.print("\tAverage execution time:\n\t" + "-".repeat(72));
        System.out.format("\n\t| %-13s | %-9s | %-8s | %-8s | %-13s |", "DepthFirst", "Breadth", "Greedy", "A*", "A* iterative");
        System.out.format("\n\t| %10.3f ms | %6.3f ms | %5.3f ms | %5.3f ms | %10.3f ms |", avg[0], avg[1], avg[2], avg[3], avg[4]);
        System.out.print("\n\t" + "-".repeat(72) + "\n");
    }
}
