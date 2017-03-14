package com.reunion.converter;

import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.reunion.converter.myDateTimeConverter")
public class MyDateTimeConverter extends DateTimeConverter {

    public MyDateTimeConverter() {
        setPattern("dd.MM.YYYY");
        setTimeZone(TimeZone.getDefault());
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.length() != getPattern().length()) {
            throw new ConverterException("Invalid format");
        }
        return super.getAsObject(context, component, value);
    }

}
