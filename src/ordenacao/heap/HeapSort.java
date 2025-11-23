package ordenacao.heap;

import pesquisa.avl.Reserva;

import java.util.ArrayList;

public class HeapSort {

    public static void ordenar(ArrayList<Reserva> lista) {
        int n = lista.size();

        // Construir o heap (rearranjar o array)
        // Começa do último nó não-folha e vai até a raiz
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i);
        }

        //Extrair elementos do heap um por um
        for (int i = n - 1; i > 0; i--) {
            // Move a raiz atual (maior elemento) para o final
            swap(lista, 0, i);

            // Chama heapify na heap reduzida (tamanho i)
            heapify(lista, i, 0);
        }
    }

    // Para manter a subárvore enraizada no nó i como heap, n é o tamanho do heap
    private static void heapify(ArrayList<Reserva> lista, int n, int i) {
        int maior = i; // Inicializa o maior como raiz
        int esquerda = 2 * i + 1;
        int direita = 2 * i + 2;

        // Se o filho da esquerda for maior que a raiz
        if (esquerda < n && comparar(lista.get(esquerda), lista.get(maior)) > 0) {
            maior = esquerda;
        }

        // Se o filho da direita for maior que o maior até agora
        if (direita < n && comparar(lista.get(direita), lista.get(maior)) > 0) {
            maior = direita;
        }

        // Se o maior não é a raiz
        if (maior != i) {
            swap(lista, i, maior);

            // Recursivamente faz o heapify na subárvore afetada
            heapify(lista, n, maior);
        }
    }

    // Método auxiliar para comparar duas reservas pelo mome
    private static int comparar(Reserva r1, Reserva r2) {
        return r1.getNomePassageiro().compareTo(r2.getNomePassageiro());
    }

    // Método auxiliar para trocar elementos na lista
    private static void swap(ArrayList<Reserva> lista, int i, int j) {
        Reserva temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}