package exemple.estoque;

public class Produto {
    private static int contador = 0; // Contador estático para gerar IDs
    private final int id;  // ID único e imutável
    private String nome;
    private int quantidade;
    private double preco;  // Adicionando preço

    // Construtor atualizado
    public Produto(String nome, int quantidade, double preco) {
        this.id = ++contador; // Incrementa o contador e define o ID
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;  // Inicializando o preço
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

    public double getPreco() {  // Getter para o preço
        return preco;
    }

    public void adicionarQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        }
        this.quantidade += quantidade;
    }

    public void removerQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        }
        if (this.quantidade < quantidade) {
            throw new IllegalArgumentException("Quantidade a ser removida é maior que a quantidade disponível.");
        }
        this.quantidade -= quantidade;
    }

    public boolean precisaReposicao() {
        return quantidade < 5; // Exemplo de critério de reposição
    }

    @Override
    public String toString() {
        return String.format("Produto [ID=%d, Nome=%s, Quantidade=%d, Preço=%.2f]", id, nome, quantidade, preco);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return id == produto.id; // Compara pelo ID
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
