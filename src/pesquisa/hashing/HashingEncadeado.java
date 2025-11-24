package pesquisa.hashing;

import pesquisa.avl.Reserva;

import java.util.ArrayList;


public class HashingEncadeado {


    //Classe interna que representa um nó na lista encadeada de cada bucket(indice) da tabela

    private class No {
        String chave; // Nome do passageiro
        // Lista de reservas para este passageiro (um nome pode ter várias reservas)
        ArrayList<Reserva> reservas;
        No proximo;

        No(String chave, Reserva reserva) {
            this.chave = chave;
            this.reservas = new ArrayList<>();
            this.reservas.add(reserva);
            this.proximo = null;
        }
    }

    private No[] tabela;
    private int tamanhoTabela;


    //Inicializa a tabela hash com um tamanho específico
    //tamanho O número de buckets da tabela

    public HashingEncadeado(int tamanho) {
        this.tamanhoTabela = tamanho;
        this.tabela = new No[tamanhoTabela];
        // Inicializa todos os buckets como nulos
        for (int i = 0; i < tamanhoTabela; i++) {
            tabela[i] = null;
        }
    }


    //Função de Hash. Converte a chave (String) num índice da tabela

    private int hash(String chave) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            //hash polinomial simples. 31 é um número primo
            hash = (31 * hash + chave.charAt(i)) % tamanhoTabela;
        }
        // Garante que o índice seja positivo
        return Math.abs(hash);
    }


    //Insere uma nova reserva na tabela de hash.

    public void inserir(Reserva reserva) {
        String chave = reserva.getNomePassageiro();
        int index = hash(chave);

        No atual = tabela[index];

        // 1. Percorre a lista encadeada (cadeia) nesse índice
        while (atual != null) {
            // 2. Se encontrar um nó com a MESMA chave (nome), apenas adiciona a reserva
            if (atual.chave.equals(chave)) {
                atual.reservas.add(reserva);
                return; // Inserção concluída
            }
            atual = atual.proximo;
        }

        // 3. Se o loop terminar, a chave (nome) não existe na cadeia

        No novoNo = new No(chave, reserva);
        // Adiciona o novo nó no início da lista
        novoNo.proximo = tabela[index];
        tabela[index] = novoNo;
    }

    /**
     * Busca por um nome (chave) na tabela de hash
     * retorna Um ArrayList com todas as reservas encontradas para aquele nome
     * ou null se o nome não for encontrado.
     */
    public ArrayList<Reserva> buscar(String nome) {
        int index = hash(nome);
        No atual = tabela[index];

        //Percorre a lista encadeada no índice calculado
        while (atual != null) {
            //Se encontrar a chave (nome)
            if (atual.chave.equals(nome)) {
                return atual.reservas; // Retorna a lista de reservas
            }
            atual = atual.proximo;
        }

        //Se o loop terminar sem encontrar, retorna null
        return null;
    }
}
