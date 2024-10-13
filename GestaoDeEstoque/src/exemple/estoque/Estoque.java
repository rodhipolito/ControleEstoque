package exemple.estoque;

import java.util.ArrayList;
import java.util.List;

public class Estoque {
    private List<Produto> produtos;

    public Estoque() {
        this.produtos = new ArrayList<>();
        inicializarProdutos(); // Inicializa produtos
    }

    private void inicializarProdutos() {
        produtos.add(new Produto("Tênis", 10));
        produtos.add(new Produto("Camiseta", 20));
        produtos.add(new Produto("Calça", 15));
        produtos.add(new Produto("Sapato", 8));
        produtos.add(new Produto("Luva", 12));
        produtos.add(new Produto("PS5", 5));
        produtos.add(new Produto("Notebook", 3));
    }

    public void entradaProduto(String nome, int quantidade) {
        Produto produto = procurarProduto(nome);
        if (produto != null) {
            produto.setQuantidade(produto.getQuantidade() + quantidade);
        } else {
            throw new IllegalArgumentException("Produto não encontrado com o nome: " + nome);
        }
    }

    public void saidaProduto(String nome, int quantidade) {
        Produto produto = procurarProduto(nome);
        if (produto != null) {
            if (produto.getQuantidade() >= quantidade) {
                produto.setQuantidade(produto.getQuantidade() - quantidade);
            } else {
                throw new IllegalArgumentException("Quantidade insuficiente para saída do produto: " + produto.getNome());
            }
        } else {
            throw new IllegalArgumentException("Produto não encontrado com o nome: " + nome);
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    // Tornando o método 'procurarProduto' público
    public Produto procurarProduto(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) { // Busca pelo nome
                return produto;
            }
        }
        return null; // Retorna null se não encontrar o produto
    }
}
