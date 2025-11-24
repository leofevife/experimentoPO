package pesquisa.hashing;

import pesquisa.avl.Reserva;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


//classeresponsável por executar toda a lógica do experimento de PO
//Ela gerencia a leitura dos arquivos, a medição de tempo e a escrita dos resultados.

public class ProcessadorExperimento {

    private final String[] arquivosDados;
    private final String arquivoNomes;
    private ArrayList<String> nomesBusca; // Armazena os 400 nomes

    public ProcessadorExperimento(String[] arquivosDados, String arquivoNomes) {
        this.arquivosDados = arquivosDados;
        this.arquivoNomes = arquivoNomes;
        this.nomesBusca = new ArrayList<>();
    }


    //Metodo principal que inicia todo o processamento

    public void executar() {
        try {
            // Carrega os 400 nomes de busca UMA ÚNICA VEZ antes de tudo
            carregarNomes();
        } catch (IOException e) {
            System.err.println("Erro fatal ao carregar o arquivo de nomes: " + arquivoNomes);
            e.printStackTrace();
            return; // Encerra se não puder ler os nomes
        }

        // Itera sobre cada um dos 12 arquivos de dados
        for (String arquivoDado : arquivosDados) {
            processarArquivoUnico(arquivoDado);
        }

        System.out.println("Processamento concluído. 12 arquivos de saída gerados.");
    }

    
    // Processa um único arquivo de dados (ex: "Reserva1000alea.txt")
     //Esse método contém o loop de 5 execuções e a medição do tempo
     
    private void processarArquivoUnico(String arquivoDado) {
        int numRegistros = getNumRegistros(arquivoDado);
        // Define o nome do arquivo de saída, ex: "Hash1000alea.txt"
        String arquivoSaida = "Hash" + arquivoDado.substring(7);

        System.out.println("Processando arquivo: " + arquivoDado + " (" + numRegistros + " registros)...");

        try {
            // Inicia a contagem de tempo ANTES das 5 execuções
            long inicioTotal = System.nanoTime();

            for (int i = 0; i < 5; i++) {
                // Carrega o arquivo em um Hashing Encadeado
                HashingEncadeado tabelaHash = carregarDadosHash(arquivoDado, numRegistros);

                // Faz a pesquisa e gera o arquivo de saída (sobrescrevendo)
                pesquisarEGravar(tabelaHash, arquivoSaida);
            }

            //Termina de contar o tempo
            long fimTotal = System.nanoTime();

            //Calcula a diferença e divide por 5
            long tempoTotal = fimTotal - inicioTotal;
            long tempoMedio = tempoTotal / 5;

            System.out.println("Arquivo de saída gerado: " + arquivoSaida);
            System.out.println("Tempo médio de processamento: " + tempoMedio + " ns\n");

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo " + arquivoDado + ": " + e.getMessage());
        }
    }


    //Cria a tabela hash e a popula
    private HashingEncadeado carregarDadosHash(String arquivoDado, int numRegistros) throws IOException {
        // Usa o número de registros como tamanho da tabela para um bom fator de carga
        HashingEncadeado tabelaHash = new HashingEncadeado(numRegistros);

        try (BufferedReader brDados = new BufferedReader(new FileReader(arquivoDado))) {
            String linhaDado;
            while ((linhaDado = brDados.readLine()) != null) {
                if (!linhaDado.trim().isEmpty()) {
                    Reserva reserva = new Reserva(linhaDado);
                    tabelaHash.inserir(reserva);
                }
            }
        }
        return tabelaHash;
    }


    //Pesquisa os nomes (this.nomesBusca) na tabela hash e grava os resultados

    private void pesquisarEGravar(HashingEncadeado tabelaHash, String arquivoSaida) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida))) {

            // Itera sobre a lista de nomes já carregada na memória
            for (String nome : this.nomesBusca) {
                ArrayList<Reserva> resultados = tabelaHash.buscar(nome);

                bw.write("NOME " + nome + ":\n");

                // Formato de impressão conforme as observações
                if (resultados == null || resultados.isEmpty()) {
                    bw.write("NÃO TEM RESERVA\n");
                    bw.write(" \n");   //alteração que a prof pediu(deixar bonitinho)
                } else {
                    for (Reserva r : resultados) {
                        bw.write("Reserva: " + r.getCodigoReserva() + " Voo: " + r.getCodigoVoo() + "\n");
                        bw.write("Data: " + r.getData() + " Assento: " + r.getAssento() + "\n");
                    }
                    bw.write("TOTAL: " + resultados.size() + " reservas\n");
                    bw.write(" \n"); //alteração que a prof pediu dnovo
                }
            }
        }
    }


    //Carrega os 400 nomes do arquivo nome.txt para a lista 'nomesBusca'

    private void carregarNomes() throws IOException {
        try (BufferedReader brNomes = new BufferedReader(new FileReader(this.arquivoNomes))) {
            String nome;
            while ((nome = brNomes.readLine()) != null) {
                if (!nome.trim().isEmpty()) {
                    this.nomesBusca.add(nome.trim());
                }
            }
        }
    }


    //Extrai o número de registros (1000, 5000, 10000, 50000) do nome do arquivo

    private int getNumRegistros(String nomeArquivo) {
        // Verifica os maiores primeiro para evitar falso positivo (ex: "10000" contém "1000")
        if (nomeArquivo.contains("10000")) {
            return 10000;
        }
        if (nomeArquivo.contains("50000")) {
            return 50000;
        }
        if (nomeArquivo.contains("1000")) {
            return 1000;
        }
        if (nomeArquivo.contains("5000")) {
            return 5000;
        }
        return 0; // Default (não deve acontecer)
    }
}
