package exemple.estoque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Estoque {
    private List<Produto> produtos = new ArrayList<>();

    // Método para procurar um produto pelo nome
    public Produto procurarProduto(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        return null;
    }

    // Método para remover um produto pelo nome
    public boolean removerProduto(String nome) {
        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getNome().equalsIgnoreCase(nome)) {
                iterator.remove();
                return true; // Produto removido com sucesso
            }
        }
        return false; // Produto não encontrado
    }

    // Método para remover um produto pelo ID
    public boolean removerProdutoPorId(int id) {
        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getId() == id) {
                iterator.remove();
                return true; // Produto removido com sucesso
            }
        }
        return false; // Produto não encontrado
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    // Outros métodos do estoque (ex.: adicionar produtos)
    public void entradaProduto(String nome, int quantidade, double preco) {
        Produto produtoExistente = procurarProduto(nome);
        if (produtoExistente != null) {
            produtoExistente.adicionarQuantidade(quantidade);
        } else {
            produtos.add(new Produto(nome, quantidade, preco));
        }
    }

    public void saidaProduto(String nome, int quantidade) {
        Produto produto = procurarProduto(nome);
        if (produto != null && produto.getQuantidade() >= quantidade) {
            produto.removerQuantidade(quantidade);
        }
    }

    // Adiciona um novo produto
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }
}
