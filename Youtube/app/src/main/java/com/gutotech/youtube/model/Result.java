package com.gutotech.youtube.model;

import java.util.List;

public class Result {
    public String regionCode;
    public PageInfo pageInfo;
    public List<Item> items;

    public class PageInfo {
        public String totalResults;
        public String resultsPerPage;
    }
}
