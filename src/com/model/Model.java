package com.model;

import com.view.View;
import com.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private View view;
    private Provider[] providers;

    public Model(View view, Provider[] providers) throws IllegalArgumentException{
        if (view == null || providers == null || providers.length == 0)
            throw new IllegalArgumentException();
        else{
            this.view = view;
            this.providers = providers;
        }
    }

    public void selectCity(String city){
        List<Vacancy> list = new ArrayList<>();
        for (Provider provider : providers){
            list.addAll(provider.getJavaVacancies(city));
        }
        view.update(list);
    }
}
