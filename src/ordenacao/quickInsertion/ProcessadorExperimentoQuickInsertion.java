package ordenacao.quickInsertion;

import pesquisa.avl.Reserva;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessadorExperimentoQuickInsertion {

    private final String[] arquivosDados;

    public ProcessadorExperimentoQuickInsertion(String[] arquivosDados) {
        this.arquivosDados = arquivosDados;
    }

    public void executar() {
        for (String arquivoDado : arquivosDados) {
            processarArquivoUnico(arquivoDado);
        }
        System.out.println("Processamento QuickSort com Inserção concluído.");
    }

    private void processarArquivoUnico(String arquivoDado) {
        // Define nome de saída: ex "QuickInsert1000alea.txt"
        String arquivoSaida = "QuickInsert" + arquivoDado.substring(7);
        System.out.println("Processando Quick + Insertion: " + arquivoDado + "...");

        try {
            long inicioTotal = System.nanoTime();

            // Executa 5 vezes
            for (int i = 0; i < 5; i++) {
                // 1. Carrega os dados
                ArrayList<Reserva> lista = carregarDados(arquivoDado);

                // 2. Ordena usando o algoritmo híbrido
                QuickInsertionSort.ordenar(lista);

                // 3. Grava o resultado
                gravarDadosOrdenados(lista, arquivoSaida);
            }

            long fimTotal = System.nanoTime();
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
            for (Reserva r : lista) {
                bw.write("NOME " + r.getNomePassageiro() + ":\n");
                bw.write("Reserva: " + r.getCodigoReserva() + " Voo: " + r.getCodigoVoo() + "\n");
                bw.write("Data: " + r.getData() + " Assento: " + r.getAssento() + "\n");
                bw.write("--------------------------------------------------\n");
            }
            bw.write("TOTAL REGISTROS: " + lista.size() + "\n");
        }
    }
}