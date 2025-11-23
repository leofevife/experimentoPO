package pesquisa.abb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import pesquisa.avl.Reserva;

public class ProcessadorExperimentoABB {

    private final String[] arquivosDados;
    private final String arquivoNomes;
    private ArrayList<String> nomesBusca;

    public ProcessadorExperimentoABB(String[] arquivosDados, String arquivoNomes) {
        this.arquivosDados = arquivosDados;
        this.arquivoNomes = arquivoNomes;
        this.nomesBusca = new ArrayList<>();
    }

    public void executar() {
        try {
            carregarNomes();
        } catch (IOException e) {
            System.err.println("Erro ao carregar nomes: " + e.getMessage());
            return;
        }

        for (String arquivoDado : arquivosDados) {
            processarArquivoUnico(arquivoDado);
        }
        System.out.println("Processamento ABB concluído.");
    }

    private void processarArquivoUnico(String arquivoDado) {
        // Define o nome de saída: ex "ABB1000alea.txt"
        String arquivoSaida = "ABB" + arquivoDado.substring(7);
        System.out.println("Processando ABB: " + arquivoDado + "...");

        try {
            long inicioTotal = System.nanoTime();

            // Repete 5 vezes para calcular a média
            for (int i = 0; i < 5; i++) {
                ArvoreABB arvore = new ArvoreABB();

                // 1. Carrega dados na ABB
                try (BufferedReader br = new BufferedReader(new FileReader(arquivoDado))) {
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        if (!linha.trim().isEmpty()) {
                            arvore.inserir(new Reserva(linha));
                        }
                    }
                }

                // 2. Pesquisa e Grava
                pesquisarEGravar(arvore, arquivoSaida);
            }

            long fimTotal = System.nanoTime();
            long tempoMedio = (fimTotal - inicioTotal) / 5;

            System.out.println("Gerado: " + arquivoSaida + " | Tempo médio: " + tempoMedio + " ns\n");

        } catch (IOException e) {
            System.err.println("Erro em " + arquivoDado + ": " + e.getMessage());
        }
    }

    private void pesquisarEGravar(ArvoreABB arvore, String arquivoSaida) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida))) {
            for (String nome : nomesBusca) {
                ArrayList<Reserva> resultados = arvore.buscar(nome);

                bw.write("NOME " + nome + ":\n");


                if (resultados == null || resultados.isEmpty()) {
                    bw.write("NÃO TEM RESERVA\n");
                } else {
                    for (Reserva r : resultados) {
                        bw.write("Reserva: " + r.getCodigoReserva() + " Voo: " + r.getCodigoVoo() + "\n");
                        bw.write("Data: " + r.getData() + " Assento: " + r.getAssento() + "\n");
                    }
                    bw.write("TOTAL: " + resultados.size() + " reservas\n");
                }
            }
        }
    }

    private void carregarNomes() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoNomes))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    nomesBusca.add(linha.trim());
                }
            }
        }
    }
}