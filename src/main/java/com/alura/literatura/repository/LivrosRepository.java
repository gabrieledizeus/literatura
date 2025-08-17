package com.alura.literatura.repository;

import com.alura.literatura.model.DadosLivros;
import com.alura.literatura.model.Livros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivrosRepository extends JpaRepository<Livros, Long> {
    List<Livros> findByAutorContainingIgnoreCase(String autor);

    List<Livros> findByIdioma(String idiomaEscolhido);

    List<Livros> findByDataDeNascimentoAutorLessThanEqualAndDataDeFalecimentoAutorGreaterThanEqualOrDataDeFalecimentoAutorIsNull(
            Integer dataDeNascimentoAutor, Integer dataDeFalecimentoAutor);
}