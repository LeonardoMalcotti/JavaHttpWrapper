package com.lm.wrapper.http;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class UriBuilder {

    private String baseUrl;
    private String pathTemplate;
    private Map<String, String> queryParams;
    private Map<String, String> urlParams;

    public UriBuilder() {
        baseUrl = "";
        pathTemplate = "";
        queryParams = Map.of();
        urlParams = Map.of();
    }

    public UriBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
        pathTemplate = "";
        queryParams = Map.of();
        urlParams = Map.of();
    }

    public UriBuilder path(String p){
        this.pathTemplate = p;
        return this;
    }

    public UriBuilder queryValue(String k, String v){
        this.queryParams.put(k,v);
        return this;
    }

    public UriBuilder queryValues(Map<String,String> vals){
        this.queryParams.putAll(vals);
        return this;
    }

    public UriBuilder urlValue(String k, String v){
        this.urlParams.put(k,v);
        return this;
    }

    public UriBuilder urlValues(Map<String,String> vals){
        this.urlParams.putAll(vals);
        return this;
    }

    public URI build(){
        var urlString = insertUrlParamsInPathTemplate();
        var query = buildQueryString();
        return URI.create(this.baseUrl + urlString + "?" + query);
    }

    private String insertUrlParamsInPathTemplate(){
        return Arrays.stream(this.pathTemplate.split("/")).map((val) -> {
            if(val.startsWith("{") && val.endsWith("}")) {
                var stripped_val = val.substring(1,val.length() - 1);
                if (this.urlParams.containsKey(stripped_val)){
                    return this.urlParams.get(stripped_val);
                }
            }
            return val;
        }).collect(Collectors.joining("/"));
    }

    private String buildQueryString(){
        return this.queryParams
                .entrySet()
                .stream()
                .map((entry) -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

}
