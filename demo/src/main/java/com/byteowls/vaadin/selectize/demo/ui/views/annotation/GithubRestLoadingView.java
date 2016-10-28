package com.byteowls.vaadin.selectize.demo.ui.views.annotation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.byteowls.vaadin.selectize.Selectize;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionLabel;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionSearch;
import com.byteowls.vaadin.selectize.config.annotation.SelectizeOptionValue;
import com.byteowls.vaadin.selectize.demo.ui.views.AbstractAddonView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringView
public class GithubRestLoadingView  extends AbstractAddonView {

    private static final long serialVersionUID = -5516501224827050267L;
    private static final Logger LOGGER = LoggerFactory.getLogger(GithubRestLoadingView.class);

    // ### start source

    @Override
    public Component getAddonComponent() {

        VerticalLayout component = new VerticalLayout();
        component.setWidth(100, Unit.PERCENTAGE);
        component.setSpacing(true);

        HorizontalLayout toolbar = new HorizontalLayout();
        component.addComponent(toolbar);

        Selectize<GithubEntry> selectize = new Selectize<>();
        selectize.setSizeFull();
        selectize.setJsLoggingEnabled(true);
        selectize.config(GithubEntry.class).infiniteItems(true).placeholder("Search for a github repo").loadThrottle(1000);
        selectize.addLazyLoadingListener(filter -> {
            RestTemplate restTemplate = new RestTemplate();
            try{
                GithubSearchResult githubSearchResult = restTemplate.getForObject("https://api.github.com/search/repositories?q={q}&sort={sort}&order={order}", GithubSearchResult.class, filter, "stars", "desc");
                return githubSearchResult.getItems();
            } catch (RestClientException e){
                //process exception
                if (e instanceof HttpStatusCodeException){
                    String errorResponse = ((HttpStatusCodeException)e).getResponseBodyAsString();
                    //now you have the response, construct json from it, and extract the errors
                    LOGGER.error("{}", errorResponse);
                }
            }
            return new ArrayList<>();
        });
        component.addComponent(selectize);
        component.setExpandRatio(selectize, 1);
        return component;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GithubSearchResult implements Serializable {
        private static final long serialVersionUID = 5146720295001735342L;
        @JsonProperty("total_count")
        private int total;
        private List<GithubEntry> items;
        public GithubSearchResult() {
            super();
        }
        public int getTotal() {
            return total;
        }
        public void setTotal(int total) {
            this.total = total;
        }
        public List<GithubEntry> getItems() {
            return items;
        }
        public void setItems(List<GithubEntry> items) {
            this.items = items;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GithubEntry implements Serializable {
        private static final long serialVersionUID = -1323886725135840587L;
        @SelectizeOptionValue
        private Long id;
        private String name;
        @JsonProperty("full_name")
        @SelectizeOptionLabel
        @SelectizeOptionSearch
        private String fullName;
        private String description;
        private String url;
        private boolean fork;
        public GithubEntry() {
        }
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getFullName() {
            return fullName;
        }
        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public boolean isFork() {
            return fork;
        }
        public void setFork(boolean fork) {
            this.fork = fork;
        }
    }

    // ### end source

}
