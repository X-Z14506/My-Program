package org.example.model;

import org.example.util.OutputUtil;
import org.example.util.PinYinUtil;

import java.io.File;
import java.util.Objects;


/**
 * 传递的数据类
 */

public class FileMeta {
    //所有成员属性私有化
    private final Integer id;
    private final String name;
    private final String pinyin;
    private final String pinyinFirst;
    private final String path;
    private final boolean directory;
    private final Long length;
    private final Long lastModifyTimeStamp;

    //提供给数据库查询后使用，数据库查询时id存在
    public FileMeta(Integer id, String name, String pinyin, String pinyinFirst, String path, boolean directory, Long length, Long lastModifyTimeStamp) {
        this.id = id;
        this.name = name;
        this.pinyin = pinyin;//数据库中存有拼音和拼音首字母，所以直接从数据库中取即可，不用通过文件名获取
        this.pinyinFirst = pinyinFirst;
        this.path = path;
        this.directory = directory;
        this.length = length;
        this.lastModifyTimeStamp = lastModifyTimeStamp;
    }


    //该构造方法提供给扫描使用，扫描时文件没有id，但是id是数据库的自增主键，存入数据库后都会生成一个且唯一的id
    public FileMeta(File file) {
        this.id = null;
        this.name = file.getName();
        this.pinyin = PinYinUtil.getPinYin(name);//扫描到的文件没有拼音和拼音首字母属性，所以要通过文件名获取到
        this.pinyinFirst = PinYinUtil.getPinYinFirst(name);
        this.path = file.getAbsolutePath();
        this.directory = file.isDirectory();
        this.length = file.length();
        this.lastModifyTimeStamp = file.lastModified();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getPinyinFirst() {
        return pinyinFirst;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return directory;
    }

    public Long getLength() {
        return length;
    }

    public Long getLastModifyTimeStamp() {
        return lastModifyTimeStamp;
    }
    public String getLengthUI() {
        return OutputUtil.formatLength(length);
    }
    public String getLastModifyUI() {
        return OutputUtil.formatTimeStamp(lastModifyTimeStamp);
    }

    @Override
    public String toString() {
        return "FileMeta{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", pinyinFirst='" + pinyinFirst + '\'' +
                ", path='" + path + '\'' +
                ", directory=" + directory +
                ", length=" + length +
                ", lastModifyTimeStamp=" + lastModifyTimeStamp +
                '}';
    }

    //只要两个文件的路径一样，他们就是同一个文件，此处路径指的绝对路径
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMeta fileMeta = (FileMeta) o;
        return Objects.equals(path, fileMeta.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
