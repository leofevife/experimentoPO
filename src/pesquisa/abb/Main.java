package pesquisa.abb;

public class TrabalhoPO {

    public static void main(String[] args) {

        // Arquivos de dados (os mesmos 12 arquivos)
        String[] arquivosDados = {
                "Reserva1000alea.txt", "Reserva1000inv.txt", "Reserva1000ord.txt",
                "Reserva5000alea.txt", "Reserva5000inv.txt", "Reserva5000ord.txt",
                "Reserva10000alea.txt", "Reserva10000inv.txt", "Reserva10000ord.txt",
                "Reserva50000alea.txt", "Reserva50000inv.txt", "Reserva50000ord.txt"
        };

        String arquivoNomes = "nome.txt";

        // Instancia o processador específico para Árvore Binária de Busca
        ProcessadorExperimentoABB processador = new ProcessadorExperimentoABB(arquivosDados, arquivoNomes);
        processador.executar();
    }
}