package com.example.brave_webtoon.webtoon.service;

import com.example.brave_webtoon.base.util.S3Util;
import com.example.brave_webtoon.webtoon.entity.WebtoonEntity;
import com.example.brave_webtoon.webtoon.entity.WebtoonRoleEntity;
import com.example.brave_webtoon.webtoon.repository.impl.WebtoonRepositoryImpl;
import com.example.brave_webtoon.webtoon.repository.impl.WebtoonRoleRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WebCrawlService {

    private final WebtoonRepositoryImpl webtoonRepositoryImpl;
    private final WebtoonRoleRepositoryImpl webtoonRoleRepositoryImpl;
    private final S3Util s3Util;

    static String folderDir = "C:\\dev\\brave";
    static String addUrl = "https://m.chuing.net/";
    static Long webtoonId = 1L;

    /**
     *
     * @param targetUrl
     * @var
     * title = 웹툰제목
     * state = 연재중,완결
     * uploadedTitleImageUrl = s3에 업로드된 타이틀 이미지 URL
     * role = 웹툰 캐릭터 역할 주연,조연,특별출연
     * name = 웹툰 캐릭터 이름
     * uploadedRoleImageUrl = s3에 업로드된 웹툰 캐릭터 이미지 URL
     * @throws IOException
     */
    @Transactional
    public void crawl(String targetUrl) throws IOException {

        Document doc = Jsoup.connect(targetUrl).get();

        Elements webtoonNm = doc.select("div.view_head_font");
        Elements state = doc.select("div.view_head_font > span");
        String webtoonNmText = webtoonNm.text().trim();
        String stateText = state.text().trim();

        String title = webtoonNmText.replaceAll(stateText,"");
        System.out.println(title);
        System.out.println(stateText);

        Elements titleImg = doc.select("div.swiper-wrapper > div > div > div > img[src]");
        String titleImgSrc = titleImg.attr("src").trim();

        Optional<WebtoonEntity> webtoonEntityOptional = webtoonRepositoryImpl.findByTitle(title);
        if(webtoonEntityOptional.isPresent()){
            webtoonId = webtoonEntityOptional.get().getId();
        }else{
            String uploadedTitleImageUrl="";
            try {
                //image generate
                File titleFile = saveWebtoonToLocal(title,titleImgSrc);
                //image save to S3
                uploadedTitleImageUrl = s3Util.upload(titleFile,"webtoon"+"/"+webtoonId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //webtoon DB Insert
            webtoonId=webtoonRepositoryImpl.save(WebtoonEntity
                    .builder()
                    .title(title)
                    .uploadPath(uploadedTitleImageUrl)
                    .deleteYn(0)
                    .build()).getId();
        }

        Elements elements = doc.select("div.people_base");

        elements.forEach(el -> {
            System.out.println("==============================================================================");

            Elements role = el.select("div.people_role");
            String roleText = role.text().trim();
            if(roleText.equals("특별출연")){
                return;
            }
            System.out.println(roleText);

            Elements name = el.select("div.people_name > a");
            String nameText = name.text().trim();
            System.out.println(nameText);

            Elements img = el.select("img[src]");
            String imgSrc = img.attr("src").trim();
            System.out.println(imgSrc);

            String uploadedRoleImageUrl="";
            try {
                //image generate
                File roleFile = saveRoleToLocal(title,nameText,imgSrc);
                //image save to S3
                uploadedRoleImageUrl = s3Util.upload(roleFile,"webtoon"+"/"+webtoonId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //webtoonRole DB Insert
            webtoonRoleRepositoryImpl.save(WebtoonRoleEntity
                    .builder()
                    .webtoonEntity(WebtoonEntity.builder().id(webtoonId).build())
                    .title(title)
                    .name(nameText)
                    .role(roleText)
                    .uploadPath(uploadedRoleImageUrl)
                    .deleteYn(0)
                    .build());

        });
        System.out.println("================================종료==============================================");
    }


    private File saveWebtoonToLocal(String title, String imgSrc) throws Exception {

        URL url = new URL(addUrl+imgSrc);

        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        File folder = new File(folderDir, title);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        String saveTitleName = generateSaveFilename(title);
        File titleFile = new File(folder, saveTitleName);

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(titleFile));

        int b = 0;
        while((b = bis.read()) != -1) {
            bos.write(b);
        }
        bos.close();

        return titleFile;
    }

    private File saveRoleToLocal(String title, String roleNm, String imgSrc) throws Exception {

        URL url = new URL(addUrl+imgSrc);

        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        File folder = new File(folderDir, title);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        String saveRoleName = generateSaveFilename(title);
        File roleFile = new File(folder, saveRoleName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(roleFile));

        int b = 0;
        while((b = bis.read()) != -1) {
            bos.write(b);
        }
        bos.close();

        return roleFile;
    }

    /**
     * 저장 파일명 생성
     * @param filename 원본 파일명
     * @return 디스크에 저장할 파일명
     */
    private String generateSaveFilename(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = "";
        if (filename.contains(".")){
            extension=StringUtils.getFilenameExtension(filename);
        }else{
            extension="jpg";
        }
        return uuid + "." + extension;
    }

}
