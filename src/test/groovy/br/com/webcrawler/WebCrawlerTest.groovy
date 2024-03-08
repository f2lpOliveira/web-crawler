package br.com.webcrawler

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertEquals

class WebCrawlerTest {

    private static WebCrawler webCrawler

    @BeforeAll
    static void instanciaWebCrawler(){
        webCrawler = new WebCrawler()
    }

    @Test
    void urlPaginaTISSTest() {
        // Given:
        String urlEsperada = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/janeiro-2024"

        // When:
        String urlObtida = webCrawler.urlPaginaTISS()

        // Then:
        assertEquals(urlEsperada, urlObtida)
    }
}