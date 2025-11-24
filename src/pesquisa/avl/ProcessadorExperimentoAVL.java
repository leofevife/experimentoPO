package pesquisa.avl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessadorExperimentoAVL {

    private final String[] arquivosDados;
    private final String arquivoNomes;
    private ArrayList<String> nomesBusca;

    public ProcessadorExperimentoAVL(String[] arquivosDados, String arquivoNomes) {
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
        System.out.println("Processamento AVL concluído.");
    }

    private void processarArquivoUnico(String arquivoDado) {
        // Nome de saída: AVL + resto do nome original
        String arquivoSaida = "AVL" + arquivoDado.substring(7);
        System.out.println("Processando AVL: " + arquivoDado + "...");

        try {
            long inicioTotal = System.nanoTime();

            // Roda 5 vezes
            for (int i = 0; i < 5; i++) {
                ArvoreAVL arvore = new ArvoreAVL();

                // Carrega dados na AVL
                try (BufferedReader br = new BufferedReader(new FileReader(arquivoDado))) {
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        if (!linha.trim().isEmpty()) {
                            arvore.inserir(new Reserva(linha));
                        }
                    }
                }

                // Pesquisa e grava
                pesquisarEGravar(arvore, arquivoSaida);
            }

            long fimTotal = System.nanoTime(); // quiser alterar a medida de tempo depois é aqui!
            long tempoMedio = (fimTotal - inicioTotal) / 5;

            System.out.println("Gerado: " + arquivoSaida + " | Tempo médio: " + tempoMedio + " ns\n");

        } catch (IOException e) {
            System.err.println("Erro em " + arquivoDado + ": " + e.getMessage());
        }
    }

    private void pesquisarEGravar(ArvoreAVL arvore, String arquivoSaida) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida))) {
            for (String nome : nomesBusca) {
                // pros casos de gerar mais de uma reserva no msm nome(vai rolar """"colisão"""", igual no hashing, mas é pq um nome pode ter mais de uma reserva)
                // aí o nó tem uma lista de reservas

                ArrayList<Reserva> resultados = arvore.buscar(nome);

                bw.write("NOME " + nome + ":\n");
                if (resultados == null || resultados.isEmpty()) {
                    bw.write("NÃO TEM RESERVA\n");
                    bw.write("\n");
                } else {
                    for (Reserva r : resultados) {
                        bw.write("Reserva: " + r.getCodigoReserva() + " Voo: " + r.getCodigoVoo() + "\n");
                        bw.write("Data: " + r.getData() + " Assento: " + r.getAssento() + "\n");
                    }
                    bw.write("TOTAL: " + resultados.size() + " reservas\n");
                    bw.write("\n");
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
