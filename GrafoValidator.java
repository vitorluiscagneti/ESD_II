import java.util.*;

public class GrafoValidator {

    public static boolean ehGrafoNaoDirecionadoValido(List<List<Integer>> grafo) {
        int V = grafo.size();
        Set<String> arestas = new HashSet<>();

        for (int u = 0; u < V; u++) {
            for (int v : grafo.get(u)) {
                // Verifica se os nós estão no intervalo válido
                if (v < 0 || v >= V) return false;

                // Verifica auto-loops
                if (u == v) return false;

                // Verifica se a aresta já foi adicionada (sem duplicatas)
                String aresta = Math.min(u, v) + "-" + Math.max(u, v);
                if (arestas.contains(aresta)) continue; // já registrada de outro lado

                arestas.add(aresta);

                // Verifica se a simetria está presente
                if (!grafo.get(v).contains(u)) return false;
            }
        }

        return true;
    }

    // Exemplo de uso
    public static void main(String[] args) {
        List<List<Integer>> grafo = new ArrayList<>();
        grafo.add(Arrays.asList(1, 2));     // 0
        grafo.add(Arrays.asList(0, 2));     // 1
        grafo.add(Arrays.asList(0, 1));     // 2

        System.out.println(ehGrafoNaoDirecionadoValido(grafo));  // true
    }
}
