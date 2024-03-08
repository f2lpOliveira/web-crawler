package br.com.webcrawler

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class WebCrawler {

    private Document getPage(String url) throws IOException {
        return Jsoup.connect(url).get()
    }

    String urlPaginaTISS() {

        Document pagina1 = getPage("https://www.gov.br/ans/pt-br")
        Element conteudo1 = pagina1.getElementById("ce89116a-3e62-47ac-9325-ebec8ea95473")
        String url1 = conteudo1.getElementsByTag("a").attr("href")

        Document pagina2 = getPage(url1)
        Element conteudo2 = pagina2.getElementsByClass("govbr-card-content").first()
        String url2 = conteudo2.getElementsByTag("a").attr("href")

        Document pagina3 = getPage(url2)
        Element conteudo3 = pagina3.getElementsByClass("callout").first()
        return conteudo3.getElementsByTag("a").attr("href")
    }
}
