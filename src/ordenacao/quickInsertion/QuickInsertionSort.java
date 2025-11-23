package ordenacao.quickInsertion;

import pesquisa.avl.Reserva;

import java.util.ArrayList;

public class QuickInsertionSort {

    //Galera, o quicksort divide em pedaços menores rapidinho, aí quando chega a 20 a recursão(metodo chama ele msm) para e entra o insertion sort sem o custo da recursão!!


    //o Insertion Sort começa a ser mais rápido que o QuickSort
    private static final int LIMITE_INSERCAO = 20;

    public static void ordenar(ArrayList<Reserva> lista) {
        if (lista == null || lista.size() <= 1) {
            return;
        }
        quicksortHibrido(lista, 0, lista.size() - 1);
    }

    private static void quicksortHibrido(ArrayList<Reserva> lista, int inicio, int fim) {
        // Se a partição for pequena, usa Insertion Sort
        if (fim - inicio + 1 <= LIMITE_INSERCAO) {
            insertionSort(lista, inicio, fim);
        } else {
            // Senão, usa QuickSort
            int indicePivo = particionar(lista, inicio, fim);
            quicksortHibrido(lista, inicio, indicePivo - 1);
            quicksortHibrido(lista, indicePivo + 1, fim);
        }
    }

    // Algoritmo de Insertion Sort adaptado para ordenar apenas um intervalo (low a high)
    private static void insertionSort(ArrayList<Reserva> lista, int inicio, int fim) {
        for (int i = inicio + 1; i <= fim; i++) {
            Reserva pivo = lista.get(i);
            int j = i - 1;

            // Move elementos maiores que o pivô para a frente
            while (j >= inicio && comparar(lista.get(j), pivo) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, pivo);
        }
    }

    // Particionamento do QuickSort (Usando pivô central para evitar pior caso em listas ordenadas)
    private static int particionar(ArrayList<Reserva> lista, int inicio, int fim) {
        int meio = (inicio + fim) / 2;
        Reserva pivo = lista.get(meio);

        // Coloca o pivô no final temporariamente
        swap(lista, meio, fim);

        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (comparar(lista.get(j), pivo) <= 0) {
                i++;
                swap(lista, i, j);
            }
        }

        // Coloca o pivô de volta na posição correta
        swap(lista, i + 1, fim);
        return i + 1;
    }

    private static int comparar(Reserva r1, Reserva r2) {
        return r1.getNomePassageiro().compareTo(r2.getNomePassageiro());
    }

    private static void swap(ArrayList<Reserva> lista, int i, int j) {
        Reserva temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }
}