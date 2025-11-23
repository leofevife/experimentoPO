package ordenacao.quick;

import pesquisa.avl.Reserva;

import java.util.ArrayList;

public class QuickSort {


    public static void ordenar(ArrayList<Reserva> lista) {
        if (lista == null || lista.size() <= 1) {
            return;
        }
        quicksort(lista, 0, lista.size() - 1);
    }


    //Método recursivo principal do QuickSort
    private static void quicksort(ArrayList<Reserva> lista, int inicio, int fim) {
        if (inicio < fim) {
            // Particiona e obtém o índice do pivô
            int indicePivo = particionar(lista, inicio, fim);

            // Chamadas recursivas para as sub-listas
            quicksort(lista, inicio, indicePivo - 1); // Esquerda
            quicksort(lista, indicePivo + 1, fim);    // Direita
        }
    }

    //Método de partição, Organiza os elementos em relação ao pivô

    private static int particionar(ArrayList<Reserva> lista, int inicio, int fim) {
        // Estratégia: Pivô Central (evita pior caso em listas já ordenadas). é pra não gerar stackoverflow!
        int meio = (inicio + fim) / 2;
        Reserva pivo = lista.get(meio);

        // Move o pivô para o fim temporariamente para facilitar a lógica
        swap(lista, meio, fim);

        int i = inicio - 1; // Índice do menor elemento

        for (int j = inicio; j < fim; j++) {
            // Se o elemento atual for menor ou igual ao pivô (comparação de String)
            if (comparar(lista.get(j), pivo) <= 0) {
                i++;
                swap(lista, i, j);
            }
        }

        // Coloca o pivô (que estava no fim) na sua posição correta
        swap(lista, i + 1, fim);

        return i + 1; // Retorna a posição final do pivô
    }


    //Auxiliar para comparar dois objetos Reserva pelo nome
    private static int comparar(Reserva r1, Reserva r2) {
        return r1.getNomePassageiro().compareTo(r2.getNomePassageiro());
    }


    //Auxiliar para trocar dois elementos de posição na lista.
    private static void swap(ArrayList<Reserva> lista, int i, int j) {
        Reserva temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}