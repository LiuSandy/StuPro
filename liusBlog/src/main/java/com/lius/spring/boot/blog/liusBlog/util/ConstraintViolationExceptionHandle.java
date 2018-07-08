package com.lius.spring.boot.blog.liusBlog.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/3/5 9:51
 * Description: ConstraintViolationException 处理器
 */
public class ConstraintViolationExceptionHandle {
    public static String getMessage(ConstraintViolationException e) {
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            msgList.add(constraintViolation.getMessage());
        }
        String messages = StringUtils.join(msgList.toArray(), ";");
        return messages;
    }
}
