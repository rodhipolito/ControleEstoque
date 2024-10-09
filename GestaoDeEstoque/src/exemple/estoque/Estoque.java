package exemple.estoque;

import java.util.ArrayList;
import java.util.List;

public class Estoque {
    private List<Produto> produtos;

    public Estoque() {
        this.produtos = new ArrayList<>();
    }

    public void entradaProduto(String nome, int quantidade) {
        Produto produto = procurarProduto(nome);
        if (produto != null) {
            produto.setQuantidade(produto.getQuantidade() + quantidade);
        } else {
            produtos.add(new Produto(nome, quantidade));
        }
    }

    public void saidaProduto(String nome, int quantidade) {
        Produto produto = procurarProduto(nome);
        if (produto != null && produto.getQuantidade() >= quantidade) {
            produto.setQuantidade(produto.getQuantidade() - quantidade);
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    private Produto procurarProduto(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equals(nome)) {
                return produto;
            }
        }
        return null;
    }
}
