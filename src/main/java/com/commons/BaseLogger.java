package com.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/4/10.
 */
public abstract class BaseLogger {
      protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
