package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.lang.ref.PhantomReference;
import java.util.Date;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2020-12-06
 * Time:23:55
 */


@Setter
@Getter
@ToString
public class Article {
    private Integer id;
    private String title;
    private String content;
    private Integer useId;
    private Date createTime;
    private Integer viewCount;
}
