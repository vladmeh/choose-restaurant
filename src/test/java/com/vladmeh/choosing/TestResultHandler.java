package com.vladmeh.choosing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 06.06.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 */


public class TestResultHandler implements ResultHandler {
    private Logger log = LoggerFactory.getLogger(TestResultHandler.class);

    static public ResultHandler testResultHandler(){
        return new TestResultHandler();
    }

    @Override
    public void handle(MvcResult result) throws Exception {
        MockHttpServletRequest request = result.getRequest();
        MockHttpServletResponse response = result.getResponse();
        log.error("HTTP method: {}, status code: {}", request.getMethod(), response.getStatus());
    }
}
