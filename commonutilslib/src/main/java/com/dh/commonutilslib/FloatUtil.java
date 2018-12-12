package com.dh.commonutilslib;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 处理浮点数小数点工具
 */
public class FloatUtil {

    /**
     * 转化浮点型为小数点后n位
     *
     * @param num
     * @return
     */
    public static float formatFloat2float2Bits(float num, int n) {
        BigDecimal bd = new BigDecimal(num);
        BigDecimal bd1 = bd.setScale(n, bd.ROUND_HALF_UP);
        return bd1.floatValue();
    }

    public static String formatInt2Bits(int num) {
        return String.format("%02d", num);
    }

    /**
     * 转化浮点型为小数点后两位
     *
     * @param str
     * @return
     */
    public static String formatFloat2String2bits(float str) {
        return String.format("%.2f", str);
    }

    /**
     * 删除小数点
     *
     * @param str
     * @return
     */
    public static String deleteBits(float str) {
        return String.format("%.0f", str);
    }

    /**
     * 转化为小数点后两位
     *
     * @param d
     * @return
     */
    public static String formatDouble2String2bits(double d) {
        DecimalFormat format = new DecimalFormat("0.00");
        return String.valueOf(format.format(d));
    }

    /**
     * 转化为小数点后两位,不四舍五入
     *
     * @param d
     * @return
     */
    public static String formatDouble2String2bitsRoundingDown(double d) {
        DecimalFormat format = new DecimalFormat("0.00");
        format.setRoundingMode(RoundingMode.DOWN);
        return String.valueOf(format.format(d));
    }

    /**
     * 截取两位小数  不会四舍五入
     *
     * @param newScale
     * @param b
     * @return
     */
    public static BigDecimal setScaleRoundDown(int newScale, BigDecimal b) {
        return b.setScale(newScale, BigDecimal.ROUND_DOWN);
    }

    /**
     * string : 数字转化  如 以千、万为单位，保留 2位小数点
     */
    public static String getUnit(Long aGold, int aUnit) {
        String aGoldStr = null;
        if (aGold > aUnit) {
            float aGoldfloat = formatFloat2float2Bits((float) aGold / aUnit, 2);
            if (aUnit == 1000) {
                aGoldStr = aGoldfloat + "K";
            } else if (aUnit == 10000) {
                aGoldStr = aGoldfloat + "W";
            }
        } else {
            aGoldStr = String.valueOf(aGold);
        }
        return aGoldStr;
    }


    /**
     * 最多保留小数点后五位
     *
     * @param d
     * @return
     */
    public static String setMaximumFractionDigits(double d) {
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(5);
        return ddf1.format(d);
    }
}
