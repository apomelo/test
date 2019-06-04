package com.jnitest.jni;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by C on 2018/10/25.
 */
public class OnCalledOne {

    public static Logger logger = LoggerFactory.getLogger(OnCalledOne.class);

    public void onCallback() {
        logger.info("This is OnCalledOne callback function.");
    }

}
