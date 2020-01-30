package com.example.configserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String rootLevel;

    public LogController(@Value("${logging.level.root:UNSET}") String rootLevel) {
        this.rootLevel = rootLevel;
    }

    @GetMapping
    public String level() {
        String currentLevel = "UNKNOWN";
        if (logger.isTraceEnabled()) {
            currentLevel = "TRACE";
        } else if (logger.isDebugEnabled()) {
            currentLevel = "DEBUG";
        } else if (logger.isInfoEnabled()) {
            currentLevel = "INFO";
        } else if (logger.isWarnEnabled()) {
            currentLevel = "WARN";
        } else if (logger.isErrorEnabled()) {
            currentLevel = "ERROR";
        }

        logger.info("Root logging level from config: " + rootLevel);
        logger.info("Current logging level in effect: " + currentLevel);
        return currentLevel;
    }
}
