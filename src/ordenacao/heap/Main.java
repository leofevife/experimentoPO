package ordenacao.heap;

public class Main {

    public static void main(String[] args) {

        // Arquivos de dados
        String[] arquivosDados = {
                "Reserva1000alea.txt", "Reserva1000inv.txt", "Reserva1000ord.txt",
                "Reserva5000alea.txt", "Reserva5000inv.txt", "Reserva5000ord.txt",
                "Reserva10000alea.txt", "Reserva10000inv.txt", "Reserva10000ord.txt",
                "Reserva50000alea.txt", "Reserva50000inv.txt", "Reserva50000ord.txt"
        };

        // Instancia o processador de Heap Sort
        ProcessadorExperimentoHeap processador = new ProcessadorExperimentoHeap(arquivosDados);
        processador.executar();
    }
}