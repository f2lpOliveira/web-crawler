package br.com.webcrawler

import groovyx.net.http.HttpBuilder
import groovyx.net.http.optional.Download
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class WebCrawler {

    private Document getPage(String url) throws IOException {
        return (Document) HttpBuilder.configure { request.uri = url }.get()
    }

    String urlPaginaTISS() {

        Document pagina1 = getPage("https://www.gov.br/ans/pt-br")
        Element conteudo1 = pagina1.getElementById("ce89116a-3e62-47ac-9325-ebec8ea95473")
        String url1 = conteudo1.getElementsByTag("a").attr("href")

        Document pagina2 = getPage(url1)
        Element conteudo2 = pagina2.getElementsByClass("govbr-card-content").first()
        return conteudo2.getElementsByTag("a").attr("href")
    }

    void getTabelaArquivosPadraoTISS() {

        Document pagina = getPage(urlPaginaTISS())
        Element conteudo = pagina.getElementsByClass("internal-link").get(0)
        String url = conteudo.getElementsByTag("a").attr("href")

        Document pagina2 = getPage(url)
        Element tabela = pagina2.getElementsByTag("tbody").first().getElementsByTag("tr").last()
        url = tabela.lastElementChild().firstElementChild().attr("href")

        baixarAndSalvarNaPastaDownloads(url, "componente_de_comunicao_TISS.zip")
    }

    private void baixarAndSalvarNaPastaDownloads(String url, String nomeArquivo) {

        File diretorio = new File("./Downloads")
        diretorio.mkdirs()
        File path = new File(diretorio, nomeArquivo)

        HttpBuilder.configure {
            request.uri = url
        }.get { Download.toFile(delegate, path)}
    }
}
