package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(
        @JsonAlias("title") String titulo,
        List<Autor> authors,
        @JsonAlias("languages") List<String> idiomas
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Autor(
            String name,
            @JsonAlias("birth_year") Integer dataDeNascimento,
            @JsonAlias("death_year") Integer dataDeFalecimento
    ) {}

}

