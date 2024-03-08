

import br.com.webcrawler.WebCrawler

class Main {
    static void main(String[] args) {
        WebCrawler webCrawler = new WebCrawler()
        String url = webCrawler.urlPaginaTISS()
        println("URL encontrado: $url")
    }
}