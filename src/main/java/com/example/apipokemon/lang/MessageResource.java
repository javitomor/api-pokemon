package com.example.apipokemon.lang;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@NoArgsConstructor
public class MessageResource {
    @Autowired
    private ResourceBundleMessageSource messageSource;


    public String toLocale(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.messageSource.getMessage(msgCode, (Object[])null, locale);
    }

}
