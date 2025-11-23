package pesquisa.abb;

import java.util.ArrayList;
import pesquisa.avl.Reserva;

public class ArvoreABB {

    private class No {
        String chave; // Nome do passageiro
        ArrayList<Reserva> reservas; // Lista para tratar nomes duplicados (mesma chave)
        No esquerda, direita;

        No(Reserva reserva) {
            this.chave = reserva.getNomePassageiro();
            this.reservas = new ArrayList<>();
            this.reservas.add(reserva);
            this.esquerda = null;
            this.direita = null;
        }
    }

    private No raiz;

    public ArvoreABB() {
        this.raiz = null;
    }



    //inserir uma reserva na árvore
    public void inserir(Reserva reserva) {
        raiz = inserir(raiz, reserva);
    }


     //Método recursivo de inserção
     //apenas descemos a árvore procurando o local correto (esq ou dir)

    private No inserir(No no, Reserva reserva) {
        // Se chegou numa folha nula, cria o novo nó aqui
        if (no == null) {
            return new No(reserva);
        }

        String chaveInserir = reserva.getNomePassageiro();
        int cmp = chaveInserir.compareTo(no.chave);

        if (cmp < 0) {
            // Se for menor, vai para a esquerda
            no.esquerda = inserir(no.esquerda, reserva);
        } else if (cmp > 0) {
            // Se for maior, vai para a direita
            no.direita = inserir(no.direita, reserva);
        } else {
            // Se for igual (mesmo nome), adiciona à lista do nó existente
            no.reservas.add(reserva);
        }

        // Retorna o próprio nó (sem balanceamento)
        return no;
    }



    //Busca por um nome na árvore
    public ArrayList<Reserva> buscar(String nome) {
        No resultado = buscar(raiz, nome);
        return (resultado != null) ? resultado.reservas : null;
    }

    private No buscar(No no, String nome) {
        if (no == null) {
            return null; // Não encontrou
        }

        int cmp = nome.compareTo(no.chave);

        if (cmp < 0) {
            return buscar(no.esquerda, nome);
        } else if (cmp > 0) {
            return buscar(no.direita, nome);
        } else {
            return no; // Encontrou a chave
        }
    }
}