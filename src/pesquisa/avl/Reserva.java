package pesquisa.avl;

//Classe para modelar os dados da reserva de voo
//Formato: reserva; nome; voo; data (dd/mm/aaaa); assento

public class Reserva {
    private String codigoReserva;
    private String nomePassageiro;
    private String codigoVoo;
    private String data;
    private String assento;


    //construtor que recebe uma linha do arquivo e divide nos ";"
    //fiz assim pra mandar cada parte certinha pros respectivos indices
    //linhaArquivo Ex: "R000126;ADILSON BARRETO;V937;16/11/2023;133E"

    public Reserva(String linhaArquivo) {
        String[] campos = linhaArquivo.split(";");
        if (campos.length == 5) {
            this.codigoReserva = campos[0];
            this.nomePassageiro = campos[1];
            this.codigoVoo = campos[2];
            this.data = campos[3];
            this.assento = campos[4];
        }
    }

    // Getters para acessar os dados
    public String getCodigoReserva() {
        return codigoReserva;
    }

    public String getNomePassageiro() {
        return nomePassageiro;
    }

    public String getCodigoVoo() {
        return codigoVoo;
    }

    public String getData() {
        return data;
    }

    public String getAssento() {
        return assento;
    }
}
