package ordenacao.heap;

import pesquisa.avl.Reserva;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessadorExperimentoHeap {

    private final String[] arquivosDados;

    public ProcessadorExperimentoHeap(String[] arquivosDados) {
        this.arquivosDados = arquivosDados;
    }

    public void executar() {
        for (String arquivoDado : arquivosDados) {
            processarArquivoUnico(arquivoDado);
        }
        System.out.println("Processamento Heap Sort concluído.");
    }

    private void processarArquivoUnico(String arquivoDado) {
        // Define nome de saída: ex "Heap1000alea.txt"
        String arquivoSaida = "Heap" + arquivoDado.substring(7);
        System.out.println("Processando Heap Sort: " + arquivoDado + "...");

        try {
            //Começa a contar o tempo total para as 5 execuções
            long inicioTotal = System.nanoTime();

            for (int i = 0; i < 5; i++) {
                //Carrega o arquivo em um ArrayList
                ArrayList<Reserva> lista = carregarDados(arquivoDado);

                //ORDENAÇÃO
                HeapSort.ordenar(lista);

                // Gera o arquivo com o resultado da ordenação
                gravarDadosOrdenados(lista, arquivoSaida);
            }

            //Termina de contar o tempo
            long fimTotal = System.nanoTime();

            //Calcula a média
            long tempoMedio = (fimTotal - inicioTotal) / 5;

            System.out.println("Gerado: " + arquivoSaida + " | Tempo médio: " + tempoMedio + " ns\n");

        } catch (IOException e) {
            System.err.println("Erro ao processar " + arquivoDado + ": " + e.getMessage());
        }
    }

    private ArrayList<Reserva> carregarDados(String arquivoDado) throws IOException {
        ArrayList<Reserva> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoDado))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(new Reserva(linha));
                }
            }
        }
        return lista;
    }

    private void gravarDadosOrdenados(ArrayList<Reserva> lista, String arquivoSaida) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida))) {
            // Imprime a lista toda ordenada
            for (Reserva r : lista) {

                bw.write("NOME " + r.getNomePassageiro() + ":\n");
                bw.write("Reserva: " + r.getCodigoReserva() + " Voo: " + r.getCodigoVoo() + "\n");
                bw.write("Data: " + r.getData() + " Assento: " + r.getAssento() + "\n");
                bw.write("--------------------------------------------------\n"); //alteração que a professora pediu, para ter mais legibilidade nos arquivos gerados
            }
            bw.write("TOTAL REGISTROS: " + lista.size() + "\n");
        }
    }
}
