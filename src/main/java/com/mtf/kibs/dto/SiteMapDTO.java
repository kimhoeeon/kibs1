package com.mtf.kibs.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SiteMapDTO {

    public static final String END_DOC = "</urlset>";
    public static final String CHANGEFREQ_ALWAYS = "always";
    public static final String CHANGEFREQ_HOURLY = "hourly";
    public static final String CHANGEFREQ_DAILY = "daily";
    public static final String CHANGEFREQ_WEEKLY = "weekly";
    public static final String CHANGEFREQ_MONTHLY = "monthly";
    public static final String CHANGEFREQ_YEARLY = "yearly";
    public static final String CHANGEFREQ_NEVER = "never";

    private static final SimpleDateFormat SITE_MAP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public SiteMapDTO(String loc) {
        this.loc = loc;
        this.lastmod = new Date();
        this.changefreq = CHANGEFREQ_DAILY;
        this.priority = "1.0";
    }
    public SiteMapDTO(String loc, Date lastmod, String changefreq, String priority) {
        this.loc = loc;
        this.lastmod = lastmod;
        this.changefreq = changefreq;
        this.priority = priority;
    }
    /**
     * url
     */
    private String loc;
    /**
     * yyyy-MM-dd
     */
    private Date lastmod;
    /**
     * always hourly daily weekly monthly yearly never
     */
    private String changefreq;
    /**
     * 1.0 0.9 0.8
     */
    private String priority;
    public String getLoc() {
        return loc;
    }
    public void setLoc(String loc) {
        this.loc = loc;
    }
    public Date getLastmod() {
        return lastmod;
    }
    public void setLastmod(Date lastmod) {
        this.lastmod = lastmod;
    }
    public String getChangefreq() {
        return changefreq;
    }
    public void setChangefreq(String changefreq) {
        this.changefreq = changefreq;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    @Override
    public String toString() {
        return "<url>" +
                "<loc>" + loc + "</loc>" +
                "<lastmod>" + SITE_MAP_DATE_FORMAT.format(lastmod) + "</lastmod>" +
                "<changefreq>" + changefreq + "</changefreq>" +
                "<priority>" + priority + "</priority>" +
                "</url>";
    }
}