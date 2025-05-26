package test;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ResourceBundle;

/**
 * @author C
 * @date 2022/4/13
 */
@Getter
public class ApplicationConfig {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private ApplicationConfig() {}
    private static class Instance {
        private static final ApplicationConfig instance = new ApplicationConfig();
        static {
            instance.loadConfig();
        }
    }
    public static ApplicationConfig getInstance() {
        return Instance.instance;
    }

    private String filePath;

    private void loadConfig() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
        filePath = resourceBundle.getString("file.path");
        if (!filePath.endsWith(File.separator)) {
            filePath += File.separator;
        }
    }
}
