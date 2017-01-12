package com.commons;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 所有单独的可通用的工具类都放这里
 * Created by admin on 2017/1/12.
 */
public class CommonUtils {

    /**
     * apache common-io包工具类的是用，还有FileUtils等
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public String io2str(InputStream inputStream) throws IOException {
        return IOUtils.toString(inputStream, Charset.defaultCharset());
    }

}
