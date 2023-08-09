package com.example.brave_webtoon.base.service;

import com.example.brave_webtoon.base.entity.WebtoonEntity;
import com.example.brave_webtoon.base.entity.WebtoonRoleEntity;
import com.example.brave_webtoon.base.repository.WebtoonRepository;
import com.example.brave_webtoon.base.repository.WebtoonRoleRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.transaction.Transactional;
import java.io.*;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebCrawlService {

    private final WebtoonRepository webtoonRepository;
    private final WebtoonRoleRepository webtoonRoleRepository;

    static String folderDir = "C:\\dev\\brave";
    static String addUrl = "https://m.chuing.net/";
    static Long webtoonId = 0L;

    @Transactional
    public void crawl(String targetUrl) throws IOException {

        Document doc = Jsoup.connect(targetUrl).get();

        Elements webtoonNm = doc.select("div.view_head_font");
        Elements state = doc.select("div.view_head_font > span");
        System.out.println(state);
        String webtoonNmText = webtoonNm.text().trim();
        String stateText = state.text().trim();
        System.out.println(stateText);

        String title = webtoonNmText.replaceAll(stateText,"");
        System.out.println(title);

        Element titleImg = doc.select("img[src]").first();
        String titleImgSrc = titleImg.attr("src").trim();

        try {
                saveWebtoonToLocal(title,titleImgSrc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Optional<WebtoonEntity> webtoonEntityOptional = webtoonRepository.findByTitle(title);


        if(webtoonEntityOptional.isPresent()){
            webtoonId = webtoonEntityOptional.get().getId();
        }else{
            webtoonId = webtoonRepository.save(WebtoonEntity
                    .builder()
                    .title(title)
                    .saveName(title)
                    .uploadPath(folderDir+"\\"+title+"\\")
                    .deleteYn(0)
                    .build()).getId();
        }

        Elements elements = doc.select("div.people_base");

        elements.forEach(el -> {
            System.out.println("==============================================================================");

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
                saveRoleToLocal(title,nameText,imgSrc);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            webtoonRoleRepository.save(WebtoonRoleEntity
                    .builder()
                    .webtoonId(webtoonId)
                    .title(nameText)
                    .role(roleText)
                    .saveName(nameText)
                    .uploadPath(folderDir+"\\"+title+"\\")
                    .deleteYn(0)
                    .build());

        });
        System.out.println("================================종료==============================================");
    }


    static void saveWebtoonToLocal(String title, String imgSrc) throws Exception {

        URL url = new URL(addUrl+imgSrc);

        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        File folder = new File(folderDir, title);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, title+".jpg");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

        int b = 0;
        while((b = bis.read()) != -1) {
            bos.write(b);
        }
        bos.close();
    }

    static void saveRoleToLocal(String title, String roleNm, String imgSrc) throws Exception {

        URL url = new URL(addUrl+imgSrc);

        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        File folder = new File(folderDir, title);
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
