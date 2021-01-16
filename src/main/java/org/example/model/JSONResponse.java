package org.example.model;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2020-12-06
 * Time:12:39
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 前后端约定的统一数据格式：返回的状态码都是200
 */

@Getter
@Setter
@ToString
public class JSONResponse {

    //业务操作是否成功
    private boolean success;

    //错误码：开发人员使用来定位问题
    private String code;

    //错误信息：给用户看的信息
    private String message;

    //业务数据
    private Object data;
}
