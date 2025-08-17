package com.alura.literatura.principal;

import com.alura.literatura.model.DadosLivros;
import com.alura.literatura.model.Livros;
import com.alura.literatura.repository.LivrosRepository;
import com.alura.literatura.services.ConsumoApi;
import com.alura.literatura.services.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
    public class Principal {

        private Scanner leitura = new Scanner(System.in);
        private ConsumoApi consumo = new ConsumoApi();

        private ConverteDados conversor = new ConverteDados();

        private final String ENDERECO = "https://gutendex.com/books";

        @Autowired
        private LivrosRepository repositorio;

        public void exibeMenu() throws JsonProcessingException {
            var opcao = -1;
            while(opcao != 0) {
                var menu = """
                    1 - Buscar livros pelo nome
                    2 - Buscar autores pelo nome
                    3 - Listar livros buscados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em determinado idioma
                                    
                    0 - Sair                                 
                    """;

                System.out.println(menu);
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {
                    case 1:
                        buscarLivroPorNome();
                        break;
                    case 2:
                        buscarAutorPorNome();
                        break;
                    case 3:
                        listarLivrosBuscados();
                        break;
                    case 4:
                        buscarAutorVivoAno();
                        break;
                    case 5:
                        buscarLivroPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            }
        }

        private void buscarLivroPorIdioma() {
            System.out.println("Idiomas: ");
            System.out.println(
                    "pt - Português\n" +
                            "en - Inglês\n" +
                            "fr - Francês\n" +
                            "es - Espanhol"
            );
            var idiomaEscolhido = leitura.nextLine();
            List<Livros> idiomaBuscado = repositorio.findByIdioma(idiomaEscolhido);
            idiomaBuscado.forEach(l ->
                    System.out.println("Livro: " + l.getTitulo())
            );
        }

        private void buscarAutorVivoAno() {
            System.out.println("Digite o ano a ser buscado");
            var anoBuscadoVivo = leitura.nextInt();
            List<Livros> anoBuscado = repositorio
                    .findByDataDeNascimentoAutorLessThanEqualAndDataDeFalecimentoAutorGreaterThanEqualOrDataDeFalecimentoAutorIsNull(
                            anoBuscadoVivo, anoBuscadoVivo);
            System.out.println("Autores encontrados: ");
            anoBuscado.forEach(l ->
                    System.out.println("" + l.getAutor())
            );
        }

        private void listarLivrosBuscados() {
            var livros = repositorio.findAll();
            System.out.println("=== Livros cadastrados ===");
            for (Livros livro : livros) {
                System.out.println(livro);
            }
        }

    private void buscarAutorPorNome() {
        System.out.println("Qual o nome do autor?");
        var nomeAutor = leitura.nextLine();
        List<Livros> livrosEncontrados = repositorio.findByAutorContainingIgnoreCase(nomeAutor);
        System.out.println("Livros Encontrados");
        livrosEncontrados.forEach(l ->
                System.out.println("Livro: " + l.getTitulo())
        );
    }

        private void buscarLivroPorNome() throws JsonProcessingException {
            DadosLivros dados = getDadosLivros();
            Livros livros = new Livros(dados);
            //dadosSeries.add(dados);
            repositorio.save(livros);
            System.out.println(dados);
        }

    private DadosLivros getDadosLivros() throws JsonProcessingException {
        System.out.println("Digite o nome do livro para busca:");
        var nomeLivro = leitura.nextLine();
        var urlBusca = "https://gutendex.com/books/?search=" + nomeLivro.replace(" ", "%20");
        var json = consumo.obterDados(urlBusca);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode firstResult = root.path("results").get(0);

        DadosLivros dados = mapper.treeToValue(firstResult, DadosLivros.class);
        return dados;

    }


}
