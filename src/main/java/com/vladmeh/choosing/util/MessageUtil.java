package com.vladmeh.choosing.util;

import com.vladmeh.choosing.exceptions.error.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil {
    public static final Locale RU_LOCALE = new Locale("ru");

    private final MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Locale locale, String... args) {
        return messageSource.getMessage(code, args, locale);
    }

    public String getMessage(String code, String... args) {
        return getMessage(code, LocaleContextHolder.getLocale(), args);
    }

    public String getMessage(ApplicationException appEx) {
        return getMessage(appEx.getMsgCode(), appEx.getArgs());
    }

    public String getMessage(MessageSourceResolvable resolvable) {
        return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
    }
}
