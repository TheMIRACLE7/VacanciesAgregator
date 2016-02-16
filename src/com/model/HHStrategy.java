package com.model;

import com.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=java&area=1&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> list = new ArrayList<>();
        try {
            int page = 0;
            while (true) {
                Document doc = getDocument(searchString, page++);
                Elements vacancyElements = doc.select("div[data-qa=vacancy-serp__vacancy]");
                if (vacancyElements.size() > 0){
                    for (Element vacancyElement : vacancyElements) {
                        Vacancy v = new Vacancy();
                        String vTitle = vacancyElement.select("a[data-qa=vacancy-serp__vacancy-title]").text();
                        v.setTitle(vTitle);
                        String vSalary = vacancyElement.select("div[data-qa=vacancy-serp__vacancy-compensation]").text();
                        v.setSalary(vSalary);
                        String vCity = vacancyElement.select("span[data-qa=vacancy-serp__vacancy-address]").text();
                        v.setCity(vCity);
                        String vCompanyName = vacancyElement.select("a[data-qa=vacancy-serp__vacancy-employer]").text();
                        v.setCompanyName(vCompanyName);
                        v.setSiteName("http://hh.ru/");
                        String vURL = vacancyElement.select("[data-qa=vacancy-serp__vacancy-title]").attr("href");
                        v.setUrl(vURL);
                        list.add(v);
                    }
                }
                else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    protected Document getDocument(String searchString, int page) throws IOException{
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36";
        Document doc = Jsoup.connect(String.format(URL_FORMAT, page)).
                    userAgent(userAgent).
                    referrer("http://hh.ru/search/vacancy?text=Java&area=1")
                    .get();
        return doc;
    }
}
