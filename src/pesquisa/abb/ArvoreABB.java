package pesquisa.abb;

import pesquisa.avl.Reserva;

import java.util.ArrayList;

public class ArvoreABB {

    private class No {
        String chave; // Nome do passageiro
        ArrayList<Reserva> reservas;
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




    //Método iterativo em vez de recursivo pra resolver o problema de stackoverflow nas arvores desbalanceadas
    public void inserir(Reserva reserva) {
        if (raiz == null) {
            raiz = new No(reserva);
            return;
        }

        No atual = raiz;
        String chaveInserir = reserva.getNomePassageiro();

        while (true) {
            int cmp = chaveInserir.compareTo(atual.chave); //comparação da chave

            if (cmp < 0) {
                // Ir para a esquerda
                if (atual.esquerda == null) {
                    atual.esquerda = new No(reserva);
                    return;
                }
                atual = atual.esquerda;
            } else if (cmp > 0) {
                // Ir para a direita
                if (atual.direita == null) {
                    atual.direita = new No(reserva);
                    return;
                }
                atual = atual.direita;
            } else {
                // Chaves iguais adiciona na lista do nó
                atual.reservas.add(reserva);
                return;
            }
        }
    }



    //Método iterativo de busca
    public ArrayList<Reserva> buscar(String nome) {
        No atual = raiz;

        while (atual != null) {
            int cmp = nome.compareTo(atual.chave);

            if (cmp < 0) {
                atual = atual.esquerda;
            } else if (cmp > 0) {
                atual = atual.direita;
            } else {
                return atual.reservas; // Encontrou
            }
        }
        return null; // Não encontrou
    }
}