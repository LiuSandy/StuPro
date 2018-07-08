package com.lius.spring.boot.blog.liusBlog.vo;

/**
 * Created with IntelliJ IDEA
 * User: 刘  爽
 * Date: 2018/3/5 9:47
 * Description: 返回对象
 */
public class Response {

    private boolean success;
    private String message;
    private Object body;

    /** 响应处理是否成功 */
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /** 响应处理的消息 */
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    /** 响应处理的返回内容 */
    public Object getBody() {
        return body;
    }
    public void setBody(Object body) {
        this.body = body;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }
}
