Para executar a classe WebCrawler corretamente, foi preciso importar as classes do Jsoup, que são usadas para manipulação e análise de documentos HTML.

Além disso, a classe WebCrawler utiliza um método getPage() que não está definido na classe.
O Jsoup fornece métodos para fazer requisições HTTP e analisar documentos HTML, como Jsoup.connect(url).get(), que foi implementado para usar o método getPage().

O método getPage() deve lidar com exceções, como IOException, que podem ocorrer durante a requisição HTTP.

![](./img/IntelliJ%20Snippet.svg)