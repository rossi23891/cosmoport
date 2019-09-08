package com.space.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ProdDateValidator implements ConstraintValidator<ProdDate, Date> {
    private static final int MIN = 2800;
    private static final int MAX = 3019;

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date==null){
            return false;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year>= MIN && year<= MAX && date.getTime()>=0;
    }
}
