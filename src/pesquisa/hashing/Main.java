package pesquisa.hashing;

public class Main {

    public static void main(String[] args) {

        //arquivos de entrada
        String[] arquivosDados = {
                "Reserva1000alea.txt", "Reserva1000inv.txt", "Reserva1000ord.txt",
                "Reserva5000alea.txt", "Reserva5000inv.txt", "Reserva5000ord.txt",
                "Reserva10000alea.txt", "Reserva10000inv.txt", "Reserva10000ord.txt",
                "Reserva50000alea.txt", "Reserva50000inv.txt", "Reserva50000ord.txt"
        };

        //Define o arquivo com os nomes para busca
        String arquivoNomes = "nome.txt";

        //instancia a classe responsável pelo processamento
        ProcessadorExperimento processador = new ProcessadorExperimento(arquivosDados, arquivoNomes);


        //Inicia o trem
        processador.executar();
    }
}