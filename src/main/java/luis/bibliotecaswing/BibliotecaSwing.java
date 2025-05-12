/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package luis.bibliotecaswing;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author catyp
 */

class Livro {
    private int id;
    private String isbn, titulo, autor;
    private boolean disponivel;
    
    public Livro (int id, String isbn, String titulo, String autor, boolean disponivel){
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true; //por defeito esta disponivel.
    }
    
    public int getId(){
        return id;
    }
    public String getIsbn(){
        return isbn;
    }
    
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }
    
    public String getTitulo(){
        return titulo;
    }
    
    public void setTitulo(String Titulo){
        this.titulo = titulo;
    }
    
    public String getAutor(){
        return autor;
    }
    
    public void setAutor(String autor){
        this.autor = autor;
    }
    
    public boolean isDisponivel(){
        return disponivel;
    }
    
    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }
    
    @Override
    public String toString(){
        return titulo + ", (" + autor + ") --> " + (disponivel? "Disponivel" : "Emprestado");
    }    
}

class Membro {
    private int id;
    private String numeroSocio, primeiroNome, apelido, email;
    
    public Membro(int id, String numeroSocio, String primeiroNome, String apelido, String email){
        this.id = id;
        this.numeroSocio = numeroSocio;
        this.primeiroNome = primeiroNome;
        this.apelido = apelido;
        this.email = email;
    }
    
    public int getId(){
        return id;
    }
    public String getNumeroSocio(){
        return numeroSocio;
    }
    
    public void setNumeroSocio(String numeroSocio){
        this.numeroSocio = numeroSocio;
    }
    
    public String getPrimeiroNome(){
        return primeiroNome;
    }
    
    private void setPrimeiroNome(String primeiroNome){
        this.primeiroNome = primeiroNome;
    }
    
    public String getApelido(){
        return apelido;
    }
    
    public void setApelido(String apelido){
        this.apelido = apelido;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getNomeCompleto(){
        return primeiroNome + " "  + apelido;
    }
    
    @Override
    public String toString(){
        return getNomeCompleto() + ", (" + numeroSocio + ")";
    }    
}

class Emprestimo{
    private int id, idLivro, idMembro;
    private Date dataEmprestimo, dataDevolucaoPrevista, dataDevolucaoEfetiva;
    
    public Emprestimo(int id, int idLivro, int idMembro, Date dataEmprestimo, Date dataDevolucaoPrevista){
        this.id = id;
        this.idLivro = idLivro;
        this.idMembro = idMembro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoEfetiva = null;
    }
    
    public int getId(){
        return id;
    }
    public int getIdLivro(){
        return idLivro;
    }
    public int getIdMembro(){
        return idMembro;
    }
    public Date getDataEmprestimo (){
        return dataEmprestimo;
    }
    public Date getDataDevolucaoPrevista(){
        return dataDevolucaoPrevista;
    }
    public Date getDataDevolucaoEfetiva(){
        return dataDevolucaoEfetiva;
    }
    
    public void setDataDevolucaoEfetiva(Date dataDevolucaoEfetiva) {
        this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
    }
    
    public String getEstado(){
        if(dataDevolucaoEfetiva != null){
            return "Devolvido";
        } else if(new Date().after(dataDevolucaoPrevista)){
            return "Atrasado";
        } else {
            return "Ativo";
        }
    }
    
    @Override
    public String toString(){
        return "Id: " + id + " --> Estado: " + getEstado();
    }
    
}


class Biblioteca {
    private List<Livro> livros;
    private List<Membro> membros;
    private List<Emprestimo> emprestimos;
    
    private int proximoIdLivro = 1;
    private int proximoIdMembro = 1;
    private int proximoIdEmprestimo = 1;
    private int nLivros = 0;
    private int nMembros = 0;
    private int nEmprestimos = 0;
    
    public Biblioteca (){
        livros = new ArrayList<>();
        membros = new ArrayList<>();
        emprestimos = new ArrayList<>();
    }
    
    //metodos para adicionae
    public Livro adicionarLivro(String isbn, String titulo, String autor){
        Livro livro = new Livro(proximoIdLivro++, isbn, titulo, autor, true);
        livros.add(livro);
        nLivros++;
        return livro;
    }
    
    public Membro adicionarMembro(String numeroSocio, String primeiroNome, String apelido, String email) {
        Membro membro = new Membro(proximoIdMembro++, numeroSocio, primeiroNome, apelido, email);
        membros.add(membro);
        nMembros++;
        return membro;
    }
    
    public Emprestimo registarEmprestimo(int idLivro, int idMembro, Date dataEmprestimo, Date dataDevolucaoPrevista) {
        Livro livro = encontrarLivroPorId(idLivro);
        if (livro == null || !livro.isDisponivel()) {
            System.out.println("Livro não encontrado ou indisponível.");
            return null;
        }
        
        Emprestimo emprestimo = new Emprestimo(proximoIdEmprestimo++, idLivro, idMembro, dataEmprestimo, dataDevolucaoPrevista);
        emprestimos.add(emprestimo);
        nEmprestimos++;
        livro.setDisponivel(false);
        return emprestimo;
    }
    
    public void registarDevolucao(int idEmprestimo, Date dataDevolucao) {
        Emprestimo emprestimo = encontrarEmprestimoPorId(idEmprestimo);
        if (emprestimo != null && emprestimo.getDataDevolucaoEfetiva() == null) {
            emprestimo.setDataDevolucaoEfetiva(dataDevolucao);
            Livro livro = encontrarLivroPorId(emprestimo.getIdLivro());
            if (livro != null) livro.setDisponivel(true);
        }
    }
    
    // Métodos para encontrar objetos
    public Livro encontrarLivroPorId(int id) {
        return livros.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
    }
    
    public Membro encontrarMembroPorId(int id) {
        return membros.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    public Emprestimo encontrarEmprestimoPorId(int id) {
        return emprestimos.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
    
    // Métodos para listar
    public List<Livro> listarLivros() { 
        return livros; 
    }

    public List<Membro> listarMembros() {
        return membros;
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimos;
    }
    
    public int getNumLivros() {
        return nLivros;
    }
    
    public int getNumMembros() {
        return nMembros;
    }
    
    public int getNumEmprestmos() {
        return nEmprestimos;
    }
    
}



public class BibliotecaSwing {

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        
        Biblioteca biblioteca = new Biblioteca();
        
        Livro livro = biblioteca.adicionarLivro("978-1234567890", "1984", "George Orwell");
        Livro livro1 = biblioteca.adicionarLivro("978-3213213123", "1985", "George Orwell");
        Membro membro = biblioteca.adicionarMembro("M001", "Maria", "Ferreira", "maria@example.com");
        
        //criar emprestimo
        Date hoje = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoje);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date devolucaoPrevista = cal.getTime();
        
        Emprestimo emprestimo = biblioteca.registarEmprestimo(livro.getId(), membro.getId(), hoje, devolucaoPrevista);
        Emprestimo emprestimo1 = biblioteca.registarEmprestimo(livro1.getId(), membro.getId(), hoje, devolucaoPrevista);
        
        //listar tudo
        
        System.out.println("\n Livros");
        biblioteca.listarLivros().forEach(System.out::println);
        
        System.out.println("\n Membros:");
        biblioteca.listarMembros().forEach(System.out::println);
        
        System.out.println("\n Emprestimos:");
        biblioteca.listarEmprestimos().forEach(System.out::println);
        
        //simular devolucao
        biblioteca.registarDevolucao(emprestimo.getId(), new Date());
        System.out.println("\n Emprestimo apos devolucao: ");
        System.out.println(biblioteca.encontrarEmprestimoPorId(emprestimo.getId()));
        
        java.awt.EventQueue.invokeLater(() -> {
            new mainWindow(biblioteca).setVisible(true);
        });
        
        /*
        Livro livro = new Livro(1, "978-1234567890", "O Senhor dos Aneis", "J.R.R. Tolkien", true);
        System.out.println("Livro criado: " + livro);
        
        Membro membro = new Membro(1, "M001", "Joao", "Silva", "joao@example.com");
        System.out.println("Membro criado: " + membro);
        
        // Definir datas do empréstimo
        Date dataHoje = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataHoje);
        cal.add(Calendar.DAY_OF_MONTH, 14); // Data de devolução prevista: 14 dias depois
        
        Date dataPrevista = cal.getTime();
        
        // Criar empréstimo
        Emprestimo emprestimo = new Emprestimo(1, livro.getId(), membro.getId(), dataHoje, dataPrevista);
        System.out.println("Emprestimo criado: " + emprestimo);
        
        // Marcar o livro como emprestado
        livro.setDisponivel(false);
        System.out.println("Estado do livro apos emprestimo: " + livro);
        
        // Simular devolução
        emprestimo.setDataDevolucaoEfetiva(new Date()); // devolvido hoje
        livro.setDisponivel(true);
        System.out.println("Emprestimo apos devolucao: " + emprestimo);
        System.out.println("Estado do livro apos devolucao: " + livro);*/
    }
}
