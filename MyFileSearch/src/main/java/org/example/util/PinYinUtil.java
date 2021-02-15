package org.example.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;


//拼音类——将输入的汉字转换为拼音，从而支持拼音搜索功能
public class PinYinUtil {
    public static String getPinYinFirst(String name) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder sb = new StringBuilder();
        for (char ch : name.toLowerCase().toCharArray()) {
            try {
                String[] pinYinArray = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                if (pinYinArray == null || pinYinArray.length == 0) {
                    sb.append(ch);
                    continue;
                }
                //获取拼音的首字母
                sb.append(pinYinArray[0].charAt(0));
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    //根据输入的查询内容，如果是汉字，将汉字转换为拼音
    public static String getPinYin(String name) {
        HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder sb = new StringBuilder();
        for (char ch: name.toLowerCase().toCharArray()) {
            try {
                String[] pinYinArray = PinyinHelper.toHanyuPinyinStringArray(ch,hanyuPinyinOutputFormat);
                if (pinYinArray == null || pinYinArray.length == 0) {
                    sb.append(ch);
                    continue;
                }
                //可能是多音字，若是多音字，只取拼音数组的第一个元素
                sb.append(pinYinArray[0]);
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                //说明输入的本来就是拼音，不需要转换为拼音
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = getPinYin("  张丁玉 和乐 说 zdy");
        System.out.println(s);
        String s1 = getPinYinFirst("张丁玉 和乐 说 zdy zhangdingyu");
        System.out.println(s1);
        String[] pinYins = PinyinHelper.toHanyuPinyinStringArray('乐');
        System.out.println(Arrays.toString(pinYins));
    }
}
