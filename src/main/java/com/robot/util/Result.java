package com.robot.util;

import com.google.common.collect.Maps;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by xing on 2016/10/7.
 */
public class Result<T> {
    public static final Integer SUCCESS = 200;
    public static final Integer FAILED = 500;
    public static final Integer WARNING = 600;
    public static final Integer DEADLINE = 700;

    private T data;
    private Integer code;
    private String message;

    public Result() {
    }

    public static Integer getSUCCESS() {
        return SUCCESS;
    }

    public static Integer getFAILED() {
        return FAILED;
    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Builder<T> {
        private T data;
        private Integer code;
        private String message;

        public Builder() {
        }

        public Result build() {
            Assert.notNull(data, "data不能为空");
            Assert.notNull(code, "code不能为空");
            Assert.notNull(message, "message不能为空");
            Result<T> result = new Result<T>();
            result.setData(data);
            result.setCode(code);
            result.setMessage(message);
            return result;
        }

        public Builder data(T data, Integer code, String message) {
            this.data = data;
            this.code = code;
            this.message = message;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public Builder failed() {
            this.code = FAILED;
            this.message = "失败,原因未知";
            return this;
        }

        public Builder failed(String message) {
            this.code = FAILED;
            this.message = message;
            return this;
        }

        public Builder failed(String code, String message) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("code", code);
            map.put("message", message);
            this.data = (T) map;
            this.code = FAILED;
            return this;
        }

        public Builder success() {
            this.code = SUCCESS;
            this.message = "成功";
            return this;
        }

        public Builder success(String message) {
            this.code = SUCCESS;
            this.message = message;
            return this;
        }

        public Builder warning(String message) {
            this.code = WARNING;
            this.message = message;
            return this;
        }

        public Builder warning(Integer code, String message) {
            this.code = code;
            this.message = message;
            return this;
        }

        public Builder deadline(String message) {
            this.code = DEADLINE;
            this.message = message;
            return this;
        }
    }

}

