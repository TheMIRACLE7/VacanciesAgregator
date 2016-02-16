package com.view;

import com.Controller;
import com.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private final String filePath =
        this.getClass().getPackage().toString().replace('.', '/').replaceFirst("package ", "./src/")+"/vacancies.html";
    Controller controller;

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            updateFile(getUpdatedFileContent(vacancies));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod(){
        controller.onCitySelect("Moscow");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        Document doc;
        try {
            doc = getDocument();
            Element original = doc.getElementsByClass("template").first();
            Element templateEl = original.clone();
            templateEl.removeClass("template").removeAttr("style");
            doc.select("tr.vacancy").select("tr:not(.template)").remove();
            for (Vacancy v : vacancies){
                Element el = templateEl.clone();
                el.select("td.city").html(v.getCity());
                el.select("td.companyName").html(v.getCompanyName());
                el.select("td.salary").html(v.getSalary());
                el.select("a").html(v.getTitle()).attr("href", v.getUrl());
                original.before(el.outerHtml());
            }
            return doc.outerHtml();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Some exception occurred";
    }

    private void updateFile(String string) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Document getDocument() throws IOException{
        File input = new File(filePath);
        Document doc = Jsoup.parse(input, "UTF-8");
        return doc;
    }
}

