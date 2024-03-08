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
        String urlEsperada = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss"

        // When:
        String urlObtida = webCrawler.urlPaginaTISS()

        // Then:
        assertEquals(urlEsperada, urlObtida)
    }

    @Test
    void baixarAndSalvarNaPastaDownloadsTest(){
        // Given:
        String url = "https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/PadroTISSComunicao202301.zip"
        String nomeArquivo = "componente_de_comunicao_TISS.zip"

        // When:
        webCrawler.baixarAndSalvarNaPastaDownloads(url, nomeArquivo)

        // Then:
        File diretorio = new File("./Downloads")
        File arquivo = new File(diretorio, nomeArquivo)
        assert arquivo.exists()
    }
}