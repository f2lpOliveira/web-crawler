package br.com.webcrawler

import groovyx.net.http.HttpBuilder
import groovyx.net.http.optional.Download
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class WebCrawler {

    private Document getPage(String url) throws IOException {
        return (Document) HttpBuilder.configure { request.uri = url }.get()
    }

    String urlPaginaTISS() {

        Document pagina = getPage("https://www.gov.br/ans/pt-br")
        Element conteudo1 = pagina.getElementById("ce89116a-3e62-47ac-9325-ebec8ea95473")
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

    void getHistoricoVersoesComponentesTISS() {
        try {
            Document pagina = Jsoup.connect(urlPaginaTISS()).get()
            Element conteudo = pagina.select(".external-link").get(0)
            String url = conteudo.select("a").attr("href")

            Document pagina1 = Jsoup.connect(url).get()
            Elements conteudo1 = pagina1.select("tbody")
            Elements listaTr = conteudo1.select("tr")

            List<List<String>> informacoes = []

            informacoes.add(["Competência", "Publicação", "Início de Vigência"])

            listaTr.each { tr ->
                Elements listaTd = tr.select("td")
                String competencia = listaTd.get(0).text()

                List<String> competenciaSplit = competencia.split("/")
                Integer ano = Integer.parseInt(competenciaSplit[1])

                if (ano >= 2016) {
                    String publicacao = listaTd.get(1).text()
                    String inicioVigencia = listaTd.get(2).text()
                    informacoes.add([competencia, publicacao, inicioVigencia])
                }
            }

            criaArquivoTXTnaPastaDownloads(informacoes, "./Downloads/historico_versoes_de_componentes_TISS.txt")
        } catch (Exception e) {
            println("Erro ao coletar informações: ${e.getMessage()}")
        }
    }

    void getTabelaErrosANS() {
        try {
            Document pagina = Jsoup.connect(urlPaginaTISS()).get()
            Element conteudo = pagina.select("#parent-fieldname-text > .callout").get(2)
            String url = conteudo.select("a").attr("href")

            Document pagina2 = getPage(url)
            Element conteudo2 = pagina2.select("#parent-fieldname-text").get(0)
            url = conteudo2.select("a").attr("href")

            baixarAndSalvarNaPastaDownloads(url, "tabela_de_erros_no_envio_para_a_ANS.xlsx")

        } catch (Exception e) {
            println("Erro ao coletar informações: ${e.getMessage()}")
        }
    }

    void criaArquivoTXTnaPastaDownloads(List<List<String>> informacoes, String caminhoArquivo) {
        try {
            File arquivo = new File(caminhoArquivo)
            if (arquivo.exists()) {
                arquivo.delete()
            }
            arquivo.createNewFile()
            arquivo.withWriter { writer ->
                informacoes.each { info ->
                    writer.writeLine(info.join(", "))
                }
            }
            println("Arquivo salvo em ${caminhoArquivo}")
        } catch (Exception e) {
            println("Erro ao criar arquivo: ${e.getMessage()}")
        }
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
