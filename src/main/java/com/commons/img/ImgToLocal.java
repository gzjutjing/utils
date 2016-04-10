/**
 *
 */
package com.commons.img;

import com.commons.BaseLogger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 保存网页图片到本地
 *
 * @author jingjiong
 * @version 1.0
 * @created 2013-12-9 下午07:09:17
 */
public class ImgToLocal extends BaseLogger {
    private String directory;
    private String urlStr;
    private Integer start;
    private Integer end;

    enum Type {
        LIST_PAGE, SINGLE_PAGE;
    }

    /**
     * 构造函数
     *
     * @param directory 存放目录
     * @param urlStr    目标图片url
     * @param start     开始页数
     * @param end       结束页数
     */
    public ImgToLocal(String directory, String urlStr, Integer start, Integer end) {
        this.directory = directory;
        this.urlStr = urlStr;
        this.start = start;
        this.end = end;
    }

    /**
     * 获取图片
     *
     * @param type
     * @throws Exception
     */
    public void switchInternetImg(Type type) throws Exception {
        logger.debug("开始获取internet图片");
        File f = new File(directory);
        if (!f.exists()) {
            f.mkdirs();
        }
        switch (type) {
            case SINGLE_PAGE:
                getInternetImg(directory, urlStr);
                break;
            case LIST_PAGE:
                if (start <= end && start > 0 && end > 0) {
                    for (int i = start; i <= end; i++) {
                        getInternetImg(directory, urlStr.replaceAll("\\{\\w\\}",
                                String.valueOf(i)));
                    }
                }
                break;
            default:
                break;
        }

    }

    /**
     * 网络连接获取图片
     *
     * @param directory
     * @param urlStr
     * @throws Exception
     */
    private void getInternetImg(String directory, String urlStr) throws Exception {
        logger.debug("取当前url：{}", urlStr);
        if (urlStr.indexOf("http:") == -1) {
            return;
        }
        URL url = new URL(urlStr);
        BufferedReader br = new BufferedReader(new InputStreamReader(url
                .openStream()));
        String s = "";
        StringBuffer sb = new StringBuffer("");
        while ((s = br.readLine()) != null) {
            sb.append(s.toLowerCase() + "/r/n");
        }
        br.close();
        s = sb.toString();
        // match
        Pattern p = Pattern.compile("(?<=\\<img)(.*?(data-original|src)=(.*?))(?=\\.jpg)");
        Matcher m = p.matcher(s);
        while (m.find()) {
            // Thread.sleep(1000*1);
            // System.out.println(m.group(3));
            String imgsrc = m.group(3).replaceAll("\"", "").replaceAll("\\\\", "/");
            int origianlIndex = imgsrc.indexOf("data-original");
            if (origianlIndex != -1) {
                imgsrc = imgsrc.substring(origianlIndex + 14, imgsrc.length());
            }
            if (imgsrc.indexOf("http:") == -1) {
                continue;
            }
            if (imgsrc.indexOf("////") != -1) {
                imgsrc = imgsrc.replaceAll("//", "/");
            }
            String imgUrlStr = imgsrc.substring(imgsrc.indexOf("http:"), imgsrc.length()) + ".jpg";
            logger.debug("取图片数据：{}", imgUrlStr);

            String filename = imgUrlStr.substring(imgUrlStr.lastIndexOf("/"),
                    imgUrlStr.length());
            File imageFile = new File(directory + filename);
            if (imageFile.exists()) {
                logger.debug("文件已经存在！");
                continue;
            }
            URL imgurl = new URL(imgUrlStr);
            // 打开链接
            HttpURLConnection conn = (HttpURLConnection) imgurl.openConnection();
            // 设置请求方式为"GET"
            conn.setRequestMethod("GET");
            // 超时响应时间为10秒
            conn.setConnectTimeout(10 * 1000);
            // 通过输入流获取图片数据
            String[] agent = {"Mozilla/5.0 (Windows NT 5.1; rv:5.0) Gecko/20110101 Firefox/5.0",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)"};
            conn.setRequestProperty("User-Agent", agent[Double.valueOf(Math.random() * agent.length).intValue()]);
            conn.connect();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.debug("错误链接，代码：{}", conn.getResponseCode());
                continue;
            }
            InputStream inStream = conn.getInputStream();
            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            // new一个文件对象用来保存图片，默认保存当前工程根目录
            // 创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            // 写入数据
            outStream.write(data);
            // 关闭输出流
            outStream.close();
        }
    }

    /**
     * 读流数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static void main(String[] args) {

        String directory = "d:/img/";
        // String url =
        // "http://accessory.haibao.com/accessory/%E8%A1%97%E6%8B%8D%E9%85%8D%E9%A5%B0/{m}.htm";
        String url = "http://pic.haibao.com/piclist/2272/{m}.htm";
        int start = 15;
        int end = 15;
        try {
            ImgToLocal imgToLocal = new ImgToLocal(directory, url, start, end);
            imgToLocal.switchInternetImg(Type.LIST_PAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束");
    }

}
