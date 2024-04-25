package com.example.common.dto.menus;

import lombok.Data;

@Data
public class Meta {

    private String title;
    private String icon;
    private boolean noCache;
    private String link;

    public Meta(String title, String icon, boolean noCache, String link) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        this.link = link;
    }
}