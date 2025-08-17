package com.alura.literatura.model;

import jakarta.persistence.*;

import java.util.OptionalDouble;

@Entity
@Table(name="livros")
public class Livros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private String descricao;
    private Integer dataDeNascimentoAutor;
    private Integer dataDeFalecimentoAutor;
    private String idioma;

    public Livros() {}

    public Livros(DadosLivros dadosLivros){
        this.titulo = dadosLivros.titulo();

        if(dadosLivros.authors() != null && !dadosLivros.authors().isEmpty()){
            DadosLivros.Autor autor = dadosLivros.authors().get(0);
            this.autor = autor.name();
            this.dataDeNascimentoAutor = autor.dataDeNascimento();
            this.dataDeFalecimentoAutor = autor.dataDeFalecimento();
        }

        this.idioma = dadosLivros.idiomas() != null ? String.join(", ", dadosLivros.idiomas()) : null;
        this.descricao = ""; // Como summaries não existe, pode deixar vazio ou adaptar
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDataDeNascimentoAutor() {
        return dataDeNascimentoAutor;
    }

    public void setDataDeNascimentoAutor(Integer dataDeNascimentoAutor) {
        this.dataDeNascimentoAutor = dataDeNascimentoAutor;
    }

    public Integer getDataDeFalecimentoAutor() {
        return dataDeFalecimentoAutor;
    }

    public void setDataDeFalecimentoAutor(Integer dataDeFalescimentoAutor) {
        this.dataDeFalecimentoAutor = dataDeFalescimentoAutor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataDeNascimentoAutor=" + dataDeNascimentoAutor +
                ", dataDeFalescimentoAutor=" + dataDeFalecimentoAutor +
                ", idioma='" + idioma;
    }
}
