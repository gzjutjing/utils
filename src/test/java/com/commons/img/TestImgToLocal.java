package com.commons.img;

import com.commons.BaseLogger;
import org.junit.Test;

/**
 * Created by Administrator on 2016/4/10.
 */
public class TestImgToLocal extends BaseLogger {

    @Test
    public void test() {
        String directory = "d:/img/";
        String url = "http://pic.haibao.com/piclist/2272/{m}.htm";
        int start = 15;
        int end = 15;
        try {
            ImgToLocal imgToLocal = new ImgToLocal(directory, url, start, end);
            imgToLocal.switchInternetImg(ImgToLocal.Type.LIST_PAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("获取结束");
    }
}
