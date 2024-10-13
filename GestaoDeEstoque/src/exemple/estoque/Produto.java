package exemple.estoque;

public class Produto {
    private static int contador = 0; // Contador estático para gerar IDs
    private final int id;  // ID único e imutável
    private String nome;
    private int quantidade;

    public Produto(String nome, int quantidade) { // Construtor com nome e quantidade
        this.id = ++contador; // Incrementa o contador e define o ID
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public int getId() { // Getter para o ID
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean precisaReposicao() {
        return quantidade < 5; // Exemplo de critério de reposição
    }
}
