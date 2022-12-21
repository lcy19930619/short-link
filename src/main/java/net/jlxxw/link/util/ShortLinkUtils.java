package net.jlxxw.link.util;

/**
 * @author chunyang.leng
 * @date 2022-12-21 10:54 AM
 */

import org.apache.commons.lang3.StringUtils;

/**
 * 短链接计算工具
 * @author chunyang.leng
 * @date 2021-10-25 9:17 上午
 */
public class ShortLinkUtils {

    /**
     * 进制
     */
    private static final int SCALE = 62;

    /**
     * 最小长度，超过该长度，自动补0
     */
    private static final int MIN_LENGTH = 5;

    /**
     * 常量字符串
     */
    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    /**
     * 数字转指定进制编码
     *
     * @param num 待转换待数据id，可以为雪花id，或者数据库自增数据id
     * @return 62进制编码
     * @see ShortLinkUtils#SCALE 进制
     */
    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            // 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
            remainder = Long.valueOf(num % SCALE).intValue();
            sb.append(chars.charAt(remainder));
            // 除以进制数，获取下一个末尾数
            num = num / SCALE;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, MIN_LENGTH, '0');
    }

    /**
     * 指定进制转为数字
     *
     * @param str 加密字符串
     * @return 原始数据id
     * @see ShortLinkUtils#SCALE 进制
     */
    public static long decode(String str) {
        //将 0 开头的字符串进行替换
        str = str.replace("^0*", "");
        long value = 0;
        char tempChar;
        int tempCharValue;
        for (int i = 0; i < str.length(); i++) {
            //获取字符
            tempChar = str.charAt(i);
            //单字符值
            tempCharValue = chars.indexOf(tempChar);
            //单字符值在进制规则下表示的值
            value += (long) (tempCharValue * Math.pow(SCALE, str.length() - i - 1));
        }
        return value;
    }

}
