package com;

import com.model.*;
import com.view.HtmlView;

public class Aggregator {

    public static void main(String[] args) {
        Provider provider = new Provider(new HHStrategy());
        Provider prov2 = new Provider(new MoikrugStrategy());
        Provider[] providers = new Provider[2];
        providers[0] = provider;
        providers[1] = prov2;
        HtmlView view = new HtmlView();
        Model model = new Model(view, providers);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
