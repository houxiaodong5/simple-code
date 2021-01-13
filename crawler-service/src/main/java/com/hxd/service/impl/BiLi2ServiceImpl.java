package com.hxd.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hxd.service.BaseUrl;
import com.hxd.service.BiLi2Service;
import com.hxd.utils.HttpUtil;
import lombok.*;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020/11/18/018.
 */
@Service
public class BiLi2ServiceImpl implements BiLi2Service, BaseUrl {

    @Autowired
    private RestTemplate restTemplate;

    static Map<String, String> headler;

    static {
        headler = new HashMap<>();
        headler.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headler.put("accept-language", "zh-CN,zh;q=0.9");
        headler.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
        headler.put("accept-encoding", "UTF-8");
        //headler.put("Keep-Alive", "300");
        //headler.put("Connection", "Keep-Alive");
        // headler.put("cookie", "CURRENT_FNVAL=80; _uuid=9F85BE12-2ED2-B8D4-870A-C0D7FD1A063546406infoc; buvid3=2773BC98-5907-42EB-9B7F-B3D13120BB57143084infoc; blackside_state=1; bsource=sea");
    }

    @Override
    public void chineseClassics() {
        //JSONObject result = restTemplate.getForObject(BILIBILI, JSONObject.class,headler);
        try {
            List<MVDTO> mvdtoList = new ArrayList<>();
            // TODO 可先爬取此网页的相关mv基础信息，然后循环爬取相关推荐的基础信息，<设置出口或者一定数据量，不然可能会无限循环>
            String url = "https://www.bilibili.com/video/BV1nx411Y7gc/?spm_id_from=333.788.videocard.3";
            String result = HttpUtil.get(url);
            FileUtils.writeStringToFile(new File("E:\\IdeaProjects\\simple-code\\crawler-service\\src\\main\\resources\\test.html"), result, "UTF-8", false);
            Document parse = Jsoup.parse(result);
            Element body = parse.body();
            Elements recElements = body.getElementsByClass("rec-list");// Elements对象可遍历
            List<Node> childNodes = recElements.get(0).childNodes();

            childNodes.forEach(childNode -> {
                childNode.childNodes().forEach(node -> {
                    if (node.childNodeSize() >= 2) {
                        List<Node> nodes = node.childNodes();
                        MVDTO mvdto = new MVDTO();
                        for (int i = 0; i < nodes.size(); i++) {
                            Node currNode = nodes.get(i); // currNode为pic-box、info
                            if (i == 0) {
                                // <a href="/video/BV1nW411b7hA/?spm_id_from=333.788.videocard.0"><img onload="reportRecoFs()" src="" alt="【古风】十首超好听且虐心的古风剧情歌（二）" width="168" height="95"></a>
                                Node node1 = currNode.childNodes().get(0).childNode(0);
                                // <img onload="reportRecoFs()" src="" alt="【古风】十首超好听且虐心的古风剧情歌（二）" width="168" height="95">

                                mvdto.setMvLink(BILIBILI + node1.attr("href"));
                                mvdto.setTitle(node1.childNode(0).attr("alt"));
                            } else if (i == 1) {
                                Node author = currNode.childNode(1);
                                Node count = currNode.childNode(2);
                                mvdto.setAuthorHomePageUrl("https:" + author.childNode(0).attr("href"));
                                mvdto.setAuthor(author.childNode(0).childNode(0).outerHtml());
                                mvdto.setPlayCount(count.childNode(0).outerHtml());
                                mvdtoList.add(mvdto);
                                break;
                            }

                        }

                    }
                });

            });

            //视频播放页 class=video-page-card
            // 视频
            /**
             * class="video-page-card"
             *  子标签<div class="card-box">
             *      子标签<div class="pic-box">获取图片相关信息 无用，真实图片链接在href中id隐藏(BV12E411b7Mg)
             *      子标签<div class="info"> 获取介绍
             *          info可获取mv链接、图片链接真实id、title、
             *          子标签<div class="count up"> 获取up主昵称及主页链接
             *          子标签<div class="count"> 获取播放量及弹幕数量
             *      两个子标签的<a href></a> 均可以获取mv链接
             *
             *
             *   <script>window.__INITIAL_STATE__ = {} 对象的related属性(数组)为相关推荐的图片链接信息，通过遍历该数组，
             *   通过数组的子对象的bvid与href中隐藏的id关联，
             *
             *   该对象的基础属性为当前播放页的mv的基本信息
             *   eg:<a href="/video/BV12E411b7Mg/?spm_id_from=333.788.videocard.1">
             *       <script>window.__INITIAL_STATE__ = {
             *           ...相关属性
             *           ...相关属性
             *           "related": [
             *              {
             "aid": 73973109,
             "cid": 126539411,
             "bvid": "BV12E411b7Mg",
             "duration": 3303,
             "pic": "http:\u002F\u002Fi0.hdslb.com\u002Fbfs\u002Farchive\u002Fe89cf2a48503701d44973c11bbea4c8c20e2f1e2.jpg",
             "title": "耳朵怀孕！许嵩2006-2019年最好听歌曲合集（附《雨幕》完整版）",
             "owner": {"name": "音乐私藏馆", "mid": 229733301},
             "stat": {"danmaku": 62512, "view": 1476185}
             }
             *           ]
             *       }
             */
            mvdtoList.forEach(mvdto -> System.out.println(mvdto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    *  <div class="video-page-card">
            <div class="card-box">
                <div class="pic-box">
                    <div class="pic"><a href="/video/BV12E411b7Mg/?spm_id_from=333.788.videocard.1"><img
                            src="" alt="耳朵怀孕！许嵩2006-2019年最好听歌曲合集（附《雨幕》完整版）" width="168"
                            height="95"></a><span class="mask-video"></span><!---->
                    </div><!---->
                </div>
                <div class="info">
                    <a href="/video/BV12E411b7Mg/?spm_id_from=333.788.videocard.1"
                                     title="耳朵怀孕！许嵩2006-2019年最好听歌曲合集（附《雨幕》完整版）" class="title">耳朵怀孕！许嵩2006-2019年最好听歌曲合集（附《雨幕》完整版）</a>
                    <div class="count up"><a href="//space.bilibili.com/229733301/" target="_blank"
                                             style="display:;">音乐私藏馆</a></div>
                    <div class="count">
                        147.6万 播放 · 6.3万 弹幕
                    </div>
                </div>https://i0.hdslb.com/bfs/archive/e89cf2a48503701d44973c11bbea4c8c20e2f1e2.jpg@336w_190h.webp
            </div>
        </div>
    *
    *
    * */

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MVDTO {
        private String title;
        private String mvLink;
        private String author;
        private String authorHomePageUrl;
        private String playCount;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMvLink() {
            return mvLink;
        }

        public void setMvLink(String mvLink) {
            this.mvLink = mvLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthorHomePageUrl() {
            return authorHomePageUrl;
        }

        public void setAuthorHomePageUrl(String authorHomePageUrl) {
            this.authorHomePageUrl = authorHomePageUrl;
        }

        public String getPlayCount() {
            return playCount;
        }

        public void setPlayCount(String playCount) {
            this.playCount = playCount;
        }

        @Override
        public String toString() {
            return "MVDTO{" +
                    "title='" + title + '\'' +
                    ", mvLink='" + mvLink + '\'' +
                    ", author='" + author + '\'' +
                    ", authorHomePageUrl='" + authorHomePageUrl + '\'' +
                    ", playCount='" + playCount + '\'' +
                    '}';
        }
    }
}
