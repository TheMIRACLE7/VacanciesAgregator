package com.model;

import com.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy{
//    private static final String URL_FORMAT = "http://javarush.ru/testdata/big28data2.html";
//    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?page=%d&q=java&city_id=%s";
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> list = new ArrayList<>();
        try {
            int page = 1;
            while (true) {
                Document doc = getDocument(searchString, page++);
//                Elements vacancyElements = doc.select("div.job");
                Elements vacancyElements = doc.getElementsByClass("job");
                if (vacancyElements.size() > 0){
                    for (Element element : vacancyElements) {
                        Vacancy v = new Vacancy();
//                        String vTitle = element.select("div.title").select("a").text();
                        String vTitle = element.getElementsByClass("title").select("a").text();
                        v.setTitle(vTitle);
//                        String vSalary = element.select("div.count").text();
                        String vSalary = element.getElementsByClass("count").text();
                        v.setSalary(vSalary);
//                        String vCity = element.select("span.location").select("a").text();
                        String vCity = element.getElementsByClass("location").text();
                        v.setCity(vCity);
//                        String vCompanyName = element.select("div.company_name").select("a").text();
                        String vCompanyName = element.getElementsByClass("company_name").select("a[href]").text();
                        v.setCompanyName(vCompanyName);
                        v.setSiteName("https://moikrug.ru");
//                        String vURL = element.select("div.title").select("a").attr("abs:href");
                        String vURL = element.getElementsByClass("title").select("a").attr("abs:href");
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
        String referrer = "http://www.google.com";
        Document doc = Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent(userAgent)
                .referrer(referrer)
                .get();
        return doc;
    }
}
