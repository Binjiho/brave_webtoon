package com.example.brave_webtoon.base.service;

import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

@Service
public class WebCrawlService {

    public void crawl(String targetUrl) throws IOException {

        Document doc = Jsoup.connect(targetUrl).get();

        Elements webtoonNm = doc.select("div.view_head_font");
        Elements state = doc.select("span.db_i_end");
        String webtoonNmText = webtoonNm.text().trim();
        String stateText = state.text().trim();

        String webtoonTitle = webtoonNmText.replaceAll(stateText,"");

        Elements elements = doc.select("div.people_base");

        elements.forEach(el -> {
            Elements role = el.select("div.people_role");
            String roleText = role.text().trim();
            System.out.println(roleText);

            Elements name = el.select("div.people_name > a");
            String nameText = name.text().trim();
            System.out.println(nameText);

            Elements img = el.select("img[src]");
            String imgSrc = img.attr("src").trim();
            System.out.println(imgSrc);

            try {
                saveFile(webtoonTitle,nameText,imgSrc);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            System.out.println("==============================================================================");
        });
    }

    static void saveFile(String webtoonTitle, String roleNm, String imgSrc) throws Exception {

        String addUrl = "https://m.chuing.net/";
        String folderDir = "C:\\Users\\Administrator\\Desktop\\dev\\brave";
        URL url = new URL(addUrl+imgSrc);

        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        File folder = new File(folderDir, webtoonTitle);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, roleNm+".jpg");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

        int b = 0;
        while((b = bis.read()) != -1) {
            bos.write(b);
        }
        bos.close();
    }
}
