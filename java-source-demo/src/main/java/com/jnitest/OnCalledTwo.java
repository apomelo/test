package com.jnitest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2018/10/25.
 */
public class OnCalledTwo {

    public static Logger logger = LoggerFactory.getLogger(OnCalledTwo.class);

    public void onCallback() {
        logger.info("This is OnCalledTwo callback function.");
    }

}
