package pesquisa.avl;

import java.util.ArrayList;

public class ArvoreAVL {

    private class No {
        String chave; // Nome do passageiro
        ArrayList<Reserva> reservas; // Lista de reservas presse nome
        No esquerda, direita;
        int altura;

        No(Reserva reserva) {
            this.chave = reserva.getNomePassageiro();
            this.reservas = new ArrayList<>(); // a lista de reserva pros casos de mais de uma reserva por nome
            this.reservas.add(reserva);
            this.altura = 1;
        }
    }

    private No raiz;

    public void inserir(Reserva reserva) {
        raiz = inserir(raiz, reserva);
    }

    private No inserir(No no, Reserva reserva) {
        if (no == null) {
            return new No(reserva);
        }

        String chaveInserir = reserva.getNomePassageiro();
        // Comparação alfabética (String compareTo)
        int cmp = chaveInserir.compareTo(no.chave);

        if (cmp < 0) {
            no.esquerda = inserir(no.esquerda, reserva);
        } else if (cmp > 0) {
            no.direita = inserir(no.direita, reserva);
        } else {
            // Chaves iguais: adiciona à lista do nó existente
            no.reservas.add(reserva);
            return no; // Não precisa balancear, a estrutura não mudou
        }

        // Atualiza altura
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        // Obtém o fator de balanceamento
        int balanceamento = getBalanceamento(no);

        // Casos de Rotação

        // Esquerda-Esquerda
        if (balanceamento > 1 && chaveInserir.compareTo(no.esquerda.chave) < 0) {
            return rotacaoDireita(no);
        }

        // Direita-Direita
        if (balanceamento < -1 && chaveInserir.compareTo(no.direita.chave) > 0) {
            return rotacaoEsquerda(no);
        }

        // Esquerda-Direita
        if (balanceamento > 1 && chaveInserir.compareTo(no.esquerda.chave) > 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Direita-Esquerda
        if (balanceamento < -1 && chaveInserir.compareTo(no.direita.chave) < 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    //simplificar a chamada por quem usa a classe
    public ArrayList<Reserva> buscar(String nome) {
        No resultado = buscar(raiz, nome);
        return (resultado != null) ? resultado.reservas : null;
    }

    private No buscar(No no, String nome) {
        if (no == null) {
            return null;
        }

        int cmp = nome.compareTo(no.chave);

        if (cmp < 0) {
            return buscar(no.esquerda, nome);
        } else if (cmp > 0) {
            return buscar(no.direita, nome);
        } else {
            return no; // Encontrou
        }
    }

    // --- Métodos Auxiliares AVL ---

    private int altura(No n) {
        return (n == null) ? 0 : n.altura;
    }

    private int getBalanceamento(No n) {
        return (n == null) ? 0 : altura(n.esquerda) - altura(n.direita);
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        // Rotação
        x.direita = y;
        y.esquerda = T2;

        // Atualiza alturas
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x; // Nova raiz
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        // Rotação
        y.esquerda = x;
        x.direita = T2;

        // Atualiza alturas
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y; // Nova raiz
    }
}
