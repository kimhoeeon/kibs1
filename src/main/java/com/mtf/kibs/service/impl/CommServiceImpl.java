package com.mtf.kibs.service.impl;

import com.google.gson.Gson;
import com.mtf.kibs.constants.CommConstants;
import com.mtf.kibs.dto.*;
import com.mtf.kibs.mapper.CommMapper;
import com.mtf.kibs.service.CommService;
import com.mtf.kibs.util.StringUtil;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class CommServiceImpl implements CommService {

    @Setter(onMethod_ = {@Autowired})
    private CommMapper commMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public List<CommCodeDTO> getCommCodeList(CommCodeDTO commCodeDTO) {
        System.out.println("CommServiceImpl > getCommCodeList : ======");
        return commMapper.getCommCodeList(commCodeDTO);
    }

    @Override
    public String getSystemicSiteMap() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<urlset\n" +
                "      xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"\n" +
                "      xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "      xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9\n" +
                "            http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">\n" +
                "<!-- created with Free Online Sitemap Generator www.xml-sitemaps.com -->\n" +
                "\n" +
                "\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/main.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>1.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/upload/center/board/brochure/7af7354d-d53b-4473-8a61-b07d04febc72.pdf</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/newsletter.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/login.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/summary.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history26.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/award.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/bi.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/cerify.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/exhibitor/guide.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/apply/step01.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/exhibitor/login.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/exhibitor/faq.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/visitor/guide_off.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/visitor/apply.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/conference.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/program.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/visitor/login.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/visitor/faq.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/notice.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/gallery.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/online/kibstv.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/press.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/contest.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/online/company.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/online/product.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/summary_kiss.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/summary_kmts.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/maritime_expo.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/column.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000159</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000157</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000156</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000026</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000025</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000024</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/privacy.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.80</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history24.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history23.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history22.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history21.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history19.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history18.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history17.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history16.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history15.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history14.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history13.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history12.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history11.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history10.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history09.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/history08.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/file/%E2%98%852025%20%EB%B3%B4%ED%8A%B8%EC%87%BC%20%EC%87%BC%EA%B0%80%EC%9D%B4%EB%93%9C%EB%B6%81(%EB%A6%AC%ED%94%8C%EB%A0%9B).pdf</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/exhibitor/privacy.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/exhibitor/findpw.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000153</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000152</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000155</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/online/kibstv_cate.do</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/upload/center/board/brochure/2abbc515-d1d2-4a9b-ba76-6e72c1a7ec1c.pdf</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/upload/center/board/brochure/049973fe-af85-493a-9c6f-993be8c9fc37.pdf</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000023</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.64</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/file/2024_boatshow_show_guide_book.pdf</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.51</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/file/2023%20%EB%B3%B4%ED%8A%B8%EC%87%BC%20%EC%87%BC%EA%B0%80%EC%9D%B4%EB%93%9C%EB%B6%81.pdf</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.51</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/file/2022%20%EA%B2%BD%EA%B8%B0%EA%B5%AD%EC%A0%9C%EB%B3%B4%ED%8A%B8%EC%87%BC%20%EC%87%BC%EA%B0%80%EC%9D%B4%EB%93%9C%EB%B6%81.pdf</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.51</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/file/2019%20%EB%B3%B4%ED%8A%B8%EC%87%BC%20%ED%98%84%EC%9E%A5%EB%A6%AC%ED%94%8C%EB%A0%9B.pdf</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.51</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/file/2018%20KIBS%20Show%20Guide.pdf</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.51</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000151</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.51</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000022</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.51</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000150</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.41</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000021</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.41</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000146</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.33</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000020</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.33</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000142</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.26</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000019</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.26</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000141</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.21</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000018</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.21</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000140</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.17</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000017</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.17</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000136</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.13</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000016</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.13</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000131</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.11</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000012</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.11</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000130</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.09</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000011</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.09</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000128</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.07</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000010</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.07</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000129</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.05</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000009</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.05</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000120</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.04</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000008</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.04</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000119</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.04</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000007</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.04</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000118</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.03</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000006</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.03</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000117</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.02</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000005</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.02</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000116</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.02</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000004</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.02</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000114</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.01</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/board/columnView.do?id=C0000003</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.01</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000113</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.01</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000112</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.01</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000111</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.01</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000110</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.01</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000109</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000108</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000107</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000106</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000105</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000104</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000103</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000101</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000098</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000097</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000096</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000095</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000094</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000092</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000090</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000087</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000086</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000085</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000083</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000080</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000079</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000078</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000077</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000076</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000075</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000074</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000073</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000072</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000071</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000069</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000065</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000062</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000056</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000054</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000035</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000049</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000047</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000046</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000045</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000044</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000043</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000042</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000041</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000040</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000039</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000038</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000037</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000036</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000034</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000032</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000031</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000030</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000029</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000028</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000027</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000026</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000025</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000024</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000023</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000021</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000020</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000019</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000018</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000016</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000015</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000014</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000013</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000012</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000011</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000010</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000009</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000008</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000007</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000006</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000022</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000139</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000138</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "<url>\n" +
                "  <loc>https://kibs.com/guide/noticeView.do?id=N0000137</loc>\n" +
                "  <lastmod>2026-02-25T01:19:11+00:00</lastmod>\n" +
                "  <priority>0.00</priority>\n" +
                "</url>\n" +
                "\n" +
                "\n" +
                "</urlset>";
    }

    /**
     * 단건 전송용
     * */
    @Override
    public SmsResponseDTO smsSend(SmsDTO smsDTO) {
        String senderParam = smsDTO.getSender();
        if(senderParam.contains("-")){
            senderParam = senderParam.replaceAll("-","");
        }
        String receiverParam = smsDTO.getReceiver();
        if(receiverParam.contains("-")){
            receiverParam = receiverParam.replaceAll("-","");
        }

        String result = "";
        try {
            final String encodingType = "UTF8";
            final String boundary = "____boundary____";

            /**************** 문자전송하기 예제 ******************/
            /* "result_code":결과코드,"message":결과문구, */
            /* "msg_id":메세지ID,"error_cnt":에러갯수,"success_cnt":성공갯수 */
            /* 동일내용 > 전송용 입니다.
            /******************** 인증정보 ********************/
            String sms_url = "https://apis.aligo.in/send/"; // 전송요청 URL

            Map<String, String> sms = new HashMap<String, String>();

            sms.put("user_id", "meetingfan"); // SMS 아이디
            sms.put("key", "ddefu9nx1etgljr1p1z1n9h7ri5u8mf0"); //인증키

            /******************** 인증정보 ********************/

            /******************** 전송정보 ********************/
            sms.put("msg", smsDTO.getMessage()); // 메세지 내용
            sms.put("receiver", receiverParam); // 수신번호
            sms.put("destination", ""/*smsDTO.getReceiver()+"|"+smsDTO.getCustomerName()*/); // 수신인 %고객명% 치환
            sms.put("sender", senderParam); // 발신번호
            sms.put("rdate", ""); // 예약일자 - 20161004 : 2016-10-04일기준
            sms.put("rtime", ""); // 예약시간 - 1930 : 오후 7시30분
            sms.put("testmode_yn", ""); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
            sms.put("title", "KIBS 경기국제보트쇼 온라인전시관 문의"); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)

            String image = "";
            //image = "/tmp/pic_57f358af08cf7_sms_.jpg"; // MMS 이미지 파일 위치

            /******************** 전송정보 ********************/

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.setBoundary(boundary);
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName(encodingType));

            for (Iterator<String> i = sms.keySet().iterator(); i.hasNext(); ) {
                String key = i.next();
                builder.addTextBody(key, sms.get(key)
                        , ContentType.create("Multipart/related", encodingType));
            }

            File imageFile = new File(image);
            if (image != null && image.length() > 0 && imageFile.exists()) {

                builder.addPart("image",
                        new FileBody(imageFile, ContentType.create("application/octet-stream"),
                                URLEncoder.encode(imageFile.getName(), encodingType)));
            }

            HttpEntity entity = builder.build();

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(sms_url);
            post.setEntity(entity);

            HttpResponse res = client.execute(post);

            if(res != null){
                BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
                String buffer = null;
                while((buffer = in.readLine())!=null){
                    result += buffer;
                }
                in.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Gson gson = new Gson();
        SmsResponseDTO responseDTO = gson.fromJson(uniToKor(result), SmsResponseDTO.class);
        System.out.println("Msg Send Response : " + responseDTO.toString());
        //{"result_code":"1","message":"success","msg_id":"583009869","success_cnt":1,"error_cnt":0,"msg_type":"SMS"}

        return responseDTO;
    }

    public String uniToKor(String uni){
        StringBuilder result = new StringBuilder();

        for(int i=0; i<uni.length(); i++){
            if(uni.charAt(i) == '\\' &&  uni.charAt(i+1) == 'u'){
                Character c = (char)Integer.parseInt(uni.substring(i+2, i+6), 16);
                result.append(c);
                i+=5;
            }else{
                result.append(uni.charAt(i));
            }
        }
        return result.toString();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void processUpdateFileDeleteUseN(FileDTO fileDTO) {
        System.out.println("CommServiceImpl > processUpdateFileDeleteUseN");
        try {
            if(!StringUtil.isEmpty(fileDTO.getUserId())){
                commMapper.updateFileDeleteUseN(fileDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public ResponseDTO processUpdateFileParentSeq(FileDTO fileDTO) {
        System.out.println("CommServiceImpl > processUpdateFileParentSeq");
        ResponseDTO responseDTO = new ResponseDTO();
        String resultCode = CommConstants.RESULT_CODE_SUCCESS;
        String resultMessage = CommConstants.RESULT_MSG_SUCCESS;
        Integer result = 0;
        try {
            if(!StringUtil.isEmpty(fileDTO.getId())){

                result = commMapper.updateFileParentSeq(fileDTO);
                if(result == 0){
                    resultCode = CommConstants.RESULT_CODE_FAIL;
                    resultMessage = "[Data Update Fail] Id : " + fileDTO.getId();
                }
                //System.out.println(result);
            }else{
                resultCode = CommConstants.RESULT_CODE_FAIL;
                resultMessage = "[Id Not Found Error]";
            }
        }catch (Exception e){
            resultCode = CommConstants.RESULT_CODE_FAIL;
            resultMessage = "[processUpdateFileParentSeq ERROR] " + CommConstants.RESULT_MSG_FAIL + " , " + e.getMessage();
            e.printStackTrace();
        }

        responseDTO.setResultCode(resultCode);
        responseDTO.setResultMessage(resultMessage);
        return responseDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public List<FileDTO> processSelectFileParentSeqList(FileDTO fileDTO) {
        System.out.println("CommServiceImpl > processSelectFileParentSeqList");
        return commMapper.selectFileParentSeqList(fileDTO);
    }

}