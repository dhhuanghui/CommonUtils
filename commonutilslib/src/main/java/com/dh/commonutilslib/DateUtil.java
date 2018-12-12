package com.dh.commonutilslib;


import com.dh.commonutilslib.TimeUtil;
import com.dh.commonutilslib.time.Lunar;
import com.dh.commonutilslib.time.LunarSolarConverter;
import com.dh.commonutilslib.time.SolarTermUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dh on 2018/2/8.
 * 算年柱，月柱以及日柱是以立春作为一年的开始
 */
public class DateUtil {
    //  星期
//    private static String[] week = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    //  农历月份
    public static String[] lunarMonth = {"正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月"};
    //  农历日
    public static String[] lunarDay = {"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
            "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
            "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"};
    //与TIAN_GAN一一对应
    public final static String[] WUXING = {"木", "木", "火", "火", "土", "土", "金", "金", "水", "水",};
    //天干
    private static String[] tianGan = {"癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬"};
    public final static String[] TIAN_GAN = {
            "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"
    };

    //出行吉利方位 与地支一一对应
    public final static String[] CHUXING = {"西南", "西北", "四方", "南", "北", "西南", "北", "西北", "东南西", "四方", "西北", "四方"};
    //地支
    public static String[] diZhi = {"亥", "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌"};

    public static String[] DI_ZHI = {
            "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"
    };
    //与DI_ZHI一一对应
    //    水	土	木	木	土	火	火	土	金	金	土	水
    public final static String[] WUXING_DZ = {"水", "土", "木", "木", "土", "火", "火", "土", "金", "金", "土", "水"};
    public final static String[] WUXING_DZ_YINGYANG = {"阳", "阴", "阳", "阴", "阳", "阴", "阳", "阴", "阳", "阴", "阳", "阴"};
    public final static String[] WUXING_TG_YINGYANG = {"阳", "阴", "阳", "阴", "阳", "阴", "阳", "阴", "阳", "阴"};
    //生肖 根据年份的地支
    public static String[] sheng_xiao = {"猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗"};
    //月支 第一个月也就是正月为寅月
    public static String[] yue_zhi = {"寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥", "子", "丑"};

    /**
     * 获取农历年份
     *
     * @return
     */
    public static int getLunarYear(int year, int month, int day) {
        int[] lunarDate = LunarSolarConverter.solarToLunar(year, month, day);
        return lunarDate[0];
    }

    /**
     * 获取农历年月日
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int[] getLunar(int year, int month, int day) {
        int[] lunarDate = LunarSolarConverter.solarToLunar(year, month, day);
        return lunarDate;
    }


    /**
     * 获取农历月份
     *
     * @return
     */
    public static String getLunarMonth(int year, int month, int day) {
        int[] lunarDate = LunarSolarConverter.solarToLunar(year, month, day);
        return lunarMonth[lunarDate[1] - 1];
    }

    /**
     * 获取转化为字符串的农历月日
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String[] getLunarMonthDayString(int year, int month, int day) {
        int[] lunarDate = LunarSolarConverter.solarToLunar(year, month, day);
        String[] strings = new String[2];
        strings[0] = lunarMonth[lunarDate[1] - 1];
        strings[1] = lunarDay[lunarDate[2] - 1];
        return strings;
    }

    /**
     * 获取农历的年月日
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int[] getLunarMonthDayIndex(int year, int month, int day) {
        int[] lunarDate = LunarSolarConverter.solarToLunar(year, month, day);
        int[] md = new int[3];
        md[0] = lunarDate[0];
        md[1] = lunarDate[1];
        md[2] = lunarDate[2];
        return md;
    }

    /**
     * 农历转化为公历
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int[] lunarToSolar(int year, int month, int day) {
        int leapMonth = LunarSolarConverter.leapMonth(year);
        boolean isLeapMonth = false;
        if (leapMonth == month) {
            isLeapMonth = true;
        }
        return LunarSolarConverter.lunarToSolar(year, month, day, isLeapMonth);
    }

    /**
     * 计算农历月份month的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int daysInLunarMonth(int year, int month) {
        int leapMonth = LunarSolarConverter.leapMonth(year);
        boolean isLeapMonth = false;
        if (leapMonth == month) {
            isLeapMonth = true;
        }
        return LunarSolarConverter.daysInMonth(year, month, isLeapMonth);
    }

    /**
     * 获取农历日
     *
     * @return
     */
    public static String getLunarDay(int year, int month, int day) {
        int[] lunarDate = LunarSolarConverter.solarToLunar(year, month, day);
        return lunarDay[lunarDate[2] - 1];
    }

    /**
     * 获取年柱(天干地支)
     * 计算公式：（公历年份-3）/10 余数=天干，（公历年份-3）/12 余数=地支
     * （2009-3）÷10……余数=天干 （2009-3）÷12……余数=地支
     *
     * @return
     */
    private static String getYearGanZhi(int year) {
        int yearTianGan = (year - 3) % 10;
        int yearDizhi = (year - 3) % 12;
        String td = tianGan[yearTianGan] + diZhi[yearDizhi];
        return td;
    }

    /**
     * 获取年柱
     *
     * @param year
     * @param month
     * @param day
     * @return 返回第一个参数为通过节气计算的立春的年份
     */
    public static String[] getYearGZ(int year, int month, int day) {
        String[] strings = new String[2];
        String solarTermLiChunTime = SolarTermUtil.getSolarTermLiChunTime(year - 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            Date date = sdf.parse(solarTermLiChunTime);
            Calendar liChunCalendar = Calendar.getInstance();
            liChunCalendar.setTime(date);
            //获取到的立春时间为2018-02-04，那么2月4号这天相当于是正月初一，使用这个时间来算天干地支
            //获取立春这一天是农历几年几月几日
            int lichunYear = liChunCalendar.get(Calendar.YEAR);
            int lichunMonth = liChunCalendar.get(Calendar.MONTH) + 1;
            int lichunDay = liChunCalendar.get(Calendar.DAY_OF_MONTH);
            //这个lunarYear就是新的一年，前面一天就是前一年
            int liChunLunarYear = getLunarYear(lichunYear, lichunMonth, lichunDay);
            //腊月
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.set(Calendar.YEAR, year);
            currentCalendar.set(Calendar.MONTH, month - 1);
            currentCalendar.set(Calendar.DAY_OF_MONTH, day);
            currentCalendar.set(Calendar.SECOND, 0);
            currentCalendar.set(Calendar.MILLISECOND, 0);
            //这里有两种情况，一种：立春时间已经是农历新的一年了，这时候立春前的日期就需要用农历年份减1
            //另一种：立春时间还没到正月，这时立春当天的年份就需要加1
            if (year == liChunLunarYear) {
                //2018-2-4  农历：2017-十二月十九
                //这时2018-2-4 就相当于是2018-1-1
                if (currentCalendar.compareTo(liChunCalendar) < 0) {
                    strings[0] = String.valueOf(liChunLunarYear - 1);
                    strings[1] = getYearGanZhi(liChunLunarYear - 1);
                } else {
                    strings[0] = String.valueOf(liChunLunarYear);
                    strings[1] = getYearGanZhi(liChunLunarYear);
                }
            } else if (year > liChunLunarYear) {
                //year=2018 luchunlunaryear=2017
                if (currentCalendar.compareTo(liChunCalendar) < 0) {
                    strings[0] = String.valueOf(liChunLunarYear);
                    strings[1] = getYearGanZhi(liChunLunarYear);
                } else {
                    strings[0] = String.valueOf(liChunLunarYear + 1);
                    strings[1] = getYearGanZhi(liChunLunarYear + 1);
                }
            } else {
                //这种情况一般不会出现
                //year<liChunLunarYear
                if (currentCalendar.compareTo(liChunCalendar) < 0) {
                    strings[0] = String.valueOf(liChunLunarYear - 1);
                    strings[1] = getYearGanZhi(liChunLunarYear - 1);
                } else {
                    strings[0] = String.valueOf(liChunLunarYear);
                    strings[1] = getYearGanZhi(liChunLunarYear);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strings;
    }

    /**
     * 获取月柱(月份根据农历算，正月为寅月)
     * 计算公式：月干=年干数×2+月数，和除以10，余数即是，整除月干即是癸
     *
     * @param month
     * @return
     */
    public static String getMonthGanZhi(int year, int month, int day) {
        int gzMonth = getGZMonth(year, month, day);
        return getMonthGanZhi(year, month, day, gzMonth);
    }

    public static String getMonthGanZhi(int year, int month, int day, int gzMonth) {
        int y = Integer.parseInt(getYearGZ(year, month, day)[0]);
        int yearTianGan = (y - 3) % 10;
        int yueGan = (yearTianGan * 2 + gzMonth) % 10;
        String ygz = tianGan[yueGan] + yue_zhi[gzMonth - 1];
        return ygz;
    }

    /**
     * 获取天干地支的月份，以二十四节气计算
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getGZMonth(int year, int month, int day) {
        String[] solarTerms = SolarTermUtil.getSolarTerms(year);
        return getGZMonth(year, month, day, solarTerms);
    }

    public static int getGZMonth(int year, int month, int day, String[] solarTerms) {
        Calendar calendar = Calendar.getInstance();
        int m = 1;
        for (int i = 0; i < solarTerms.length; i++) {
            if (i % 2 == 0) {
                //计算新的一个月
                String solarTerm = solarTerms[i];
                String time = solarTerm.substring(0, solarTerm.length() - 2);
                Date date = TimeUtil.formatTimeToDate(time, "yyyyMMddHHmm");
                calendar.setTime(date);
                int month2 = calendar.get(Calendar.MONTH) + 1;
                if (month == month2) {
                    Calendar currentCalendar = Calendar.getInstance();
                    currentCalendar.set(Calendar.YEAR, year);
                    currentCalendar.set(Calendar.MONTH, month - 1);
                    currentCalendar.set(Calendar.DAY_OF_MONTH, day);
                    currentCalendar.set(Calendar.SECOND, 0);
                    currentCalendar.set(Calendar.MILLISECOND, 0);
                    if (currentCalendar.compareTo(calendar) < 0) {
                        m = i / 2;
                    } else {
                        m = i / 2 + 1;
                    }
                    break;
                }
            }
        }
        return m == 0 ? 12 : m;
    }

    /**
     * 获取日干支
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getDayGZ(int year, int month, int day) {
        // 当月一日与 1900/1/1 相差天数
        // 1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
        //一天为86400000ms
        int cyclicalDay = (int) (Lunar.UTC(year, month - 1, day, 0, 0, 0) / 86400000 + 25567 + 10) % 60;
        return TIAN_GAN[getTianan(cyclicalDay)] + DI_ZHI[getDeqi(cyclicalDay)];
    }

    /**
     * 由日干推算时柱干支公式
     * 1、日干 x 2 + 时支数 - 2 = 时干数（时干数超过10要减去10,只取个位数）（公式揭密：因为甲子日的子时是从甲子时开始，
     * 推算下去，乙丑日对应丙子，即乙2对应丙3，再下去是丙3对应戊5，正好每日的子时天干为日干×2-1，即日干×2＋时支数-2）
     * 2、时支是固定的，时辰顺余是：子时、丑时、寅时、卯时、辰时、巳时、午时、未时、申时、酉时 、戌时、亥时。
     * <p>
     * 子时 23：00 - 1：00
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @return
     */
    public static String getHourGanZhi(int year, int month, int day, int hour) {
        int hourZhiIndex = getHourZhi(hour);
        String dayGanZhi = getDayGZ(year, month, day);
        String dayGan = dayGanZhi.substring(0, 1);
        int dayGanIndex = 0;
        for (int i = 0; i < tianGan.length; i++) {
            if (dayGan.equals(tianGan[i])) {
                dayGanIndex = i;
                break;
            }
        }
        int hourGanIndex = (dayGanIndex * 2 + hourZhiIndex - 2) % 10;
        if (hourGanIndex == -1) {
            return tianGan[9] + diZhi[hourZhiIndex];
        } else if (hourGanIndex == -2) {
            return tianGan[8] + diZhi[hourZhiIndex];
        } else {
            return tianGan[hourGanIndex] + diZhi[hourZhiIndex];
        }
    }

    public static String getHour(int hour) {
        int hourZhi = getHourZhi(hour);
        return diZhi[hourZhi];
    }

    /**
     * 获得地支
     *
     * @param cyclicalNumber
     * @return 地支 (数字)
     */
    private static int getDeqi(int cyclicalNumber) {
        return cyclicalNumber % 12;
    }

    /**
     * 获得天干
     *
     * @param cyclicalNumber
     * @return 天干 (数字)
     */
    private static int getTianan(int cyclicalNumber) {
        return cyclicalNumber % 10;
    }
    /**
     * 获取日柱
     * 口诀：乘五除四九加日，双月间隔三十天，一二自加整少一，三五七八十尾前。
     * 1-12月的调节数：1,2，0，1，1，2，2，3，4，4，5，5
     * 或：①年数减基数，所得为差数；差数乘以5，所得为积数。
     * ②差数除以4，加9加日数，双月加30，再加调节数。
     * ③积数加和数，再除以60，所除之余数，便是日柱数。
     * 根据“日干支序数表”查日干支
     * <p>
     * 说明：一是“基数”为1900，；
     * 二是出生年数除以4，能整除者皆为闰年，凡闰年一、二月出生者，须从“余数”中减1，不能整除者其商取整数；
     * 三是“调节数”：一、四、五月出生为1，二、六、七月出生为2，八月出生为3，九、十月出生为4，十一、十二月出生为5，三月出生为0。（1-12月的调节数：1,2，0，1，1，2，2，3，4，4，5，5）。
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
//    public static String getDayGanZhi(int year, int month, int day) {
//        int y = Integer.parseInt(getYearGZ(year, month, day)[0]);
//        int chashu = y - ji_Shu;
//        int jishu = chashu * 5;
//        int t = month % 2 == 0 ? 30 : 0;
//        int heShu = chashu / 4 + 9 + day + t + tiaoJieShu[month - 1];
//        int dayGZ = (jishu + heShu) % 60;
//        int geWei = dayGZ % 10;
//        int shiWei = dayGZ / 10;
//        return DAY_GAN_ZHI[shiWei][geWei];
//    }

    /**
     * 由日干推算时柱干支公式
     * 1、日干 x 2 + 时支数 - 2 = 时干数（时干数超过10要减去10,只取个位数）（公式揭密：因为甲子日的子时是从甲子时开始，
     * 推算下去，乙丑日对应丙子，即乙2对应丙3，再下去是丙3对应戊5，正好每日的子时天干为日干×2-1，即日干×2＋时支数-2）
     * 2、时支是固定的，时辰顺余是：子时、丑时、寅时、卯时、辰时、巳时、午时、未时、申时、酉时 、戌时、亥时。
     * <p>
     * 子时 23：00 - 1：00
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @return
     */
//    public static String getHourGanZhi(int year, int month, int day, int hour) {
//        int hourZhiIndex = getHourZhi(hour);
//        String dayGanZhi = getDayGanZhi(year, month, day);
//        String dayGan = dayGanZhi.substring(0, 1);
//        int dayGanIndex = 0;
//        for (int i = 0; i < tianGan.length; i++) {
//            if (dayGan.equals(tianGan[i])) {
//                dayGanIndex = i;
//            }
//        }
//        int hourGanIndex = (dayGanIndex * 2 + hourZhiIndex - 2) % 10;
//        return tianGan[hourGanIndex] + diZhi[hourZhiIndex];
//
//    }

    /**
     * 返回地支对应的下标
     *
     * @param hour
     * @return
     */
    private static int getHourZhi(int hour) {
        if (hour >= 23 || (hour >= 0 && hour < 1)) {
            //子时
            return 1;
        } else if (hour >= 1 && hour < 3) {
            return 2;
        } else if (hour >= 3 && hour < 5) {
            return 3;
        } else if (hour >= 5 && hour < 7) {
            return 4;
        } else if (hour >= 7 && hour < 9) {
            return 5;
        } else if (hour >= 9 && hour < 11) {
            return 6;
        } else if (hour >= 11 && hour < 13) {
            return 7;
        } else if (hour >= 13 && hour < 15) {
            return 8;
        } else if (hour >= 15 && hour < 17) {
            return 9;
        } else if (hour >= 17 && hour < 19) {
            return 10;
        } else if (hour >= 19 && hour < 21) {
            return 11;
        } else if (hour >= 21 && hour < 23) {
            return 0;
        }
        return 0;
    }

    /**
     * 生肖
     *
     * @return
     */
    public static String getShengXiao(int year, int month, int day) {
        int lunarYear = getLunarYear(year, month, day);
        int yearDizhi = (lunarYear - 3) % 12;
        return sheng_xiao[yearDizhi];
    }

    /**
     * 获取农历
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getLunarIndex(int year, int month, int day) {
        int[] lunarDate = LunarSolarConverter.solarToLunar(year, month, day);
        return lunarDate[1];
    }

    /**
     * 获取星座
     *
     * @param month
     * @param day
     */
    public static String getXingzuo(int month, int day) {
        switch (month) {
            case 1:
                return day <= 19 ? "摩羯座" : "水瓶座";
            case 2:
                return day <= 18 ? "水瓶座" : "双鱼座";
            case 3:
                return day <= 20 ? "双鱼座" : "白羊座";
            case 4:
                return day <= 19 ? "白羊座" : "金牛座";
            case 5:
                return day <= 20 ? "金牛座" : "双子座";
            case 6:
                return day <= 21 ? "双子座" : "巨蟹座";
            case 7:
                return day <= 22 ? "巨蟹座" : "狮子座";
            case 8:
                return day <= 22 ? "狮子座" : "处女座";
            case 9:
                return day <= 22 ? "处女座" : "天秤座";
            case 10:
                return day <= 23 ? "天秤座" : "天蝎座";
            case 11:
                return day <= 22 ? "天蝎座" : "射手座";
            case 12:
                return day <= 21 ? "射手座" : "摩羯座";
        }
        return "";
    }


    public static String getXingZuoEn(String xingzuo) {
        if ("水瓶座".equals(xingzuo)) {
            return "Aquarius";
        } else if ("双鱼座".equals(xingzuo)) {
            return "Pisces";
        } else if ("白羊座".equals(xingzuo)) {
            return "Aries";
        } else if ("金牛座".equals(xingzuo)) {
            return "Taurus";
        } else if ("双子座".equals(xingzuo)) {
            return "Gemini";
        } else if ("巨蟹座".equals(xingzuo)) {
            return "Cancer";
        } else if ("狮子座".equals(xingzuo)) {
            return "Leo";
        } else if ("处女座".equals(xingzuo)) {
            return "Virgo";
        } else if ("天秤座".equals(xingzuo)) {
            return "Libra";
        } else if ("天蝎座".equals(xingzuo)) {
            return "Scorpio";
        } else if ("射手座".equals(xingzuo)) {
            return "Sagittarius";
        } else if ("摩羯座".equals(xingzuo)) {
            return "Capricorn";
        }
        return "";
    }

    public static long getOneDay() {
        return 24 * 60 * 60 * 1000;
    }

    public static boolean isToday(Calendar todayCalendar, Calendar otherCalendar) {
        int year = todayCalendar.get(Calendar.YEAR);
        int month = todayCalendar.get(Calendar.MONTH) + 1;
        int day = todayCalendar.get(Calendar.DAY_OF_MONTH);
        int year1 = otherCalendar.get(Calendar.YEAR);
        int month1 = otherCalendar.get(Calendar.MONTH) + 1;
        int day1 = otherCalendar.get(Calendar.DAY_OF_MONTH);
        if (year == year1 && month == month1 && day == day1) {
            return true;
        }
        return false;
    }

    /**
     * 通过年月日获取节气
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getSolarTerm(int year, int month, int day) {
        return SolarTermUtil.getSolarTerm(year, month, day);
    }


    /**
     * @param degree
     * @return
     */
    public static String[] formatCompassPosition(float degree) {
        String[] position = new String[2];
        if (degree >= 7 && degree < 22) {
            position[0] = "癸";
            position[1] = "丁";
        } else if (degree >= 22 && degree < 37) {
            position[0] = "丑";
            position[1] = "未";
        } else if (degree >= 37 && degree < 52) {
            position[0] = "艮";
            position[1] = "坤";
        } else if (degree >= 52 && degree < 67) {
            position[0] = "寅";
            position[1] = "申";
        } else if (degree >= 67 && degree < 82) {
            position[0] = "甲";
            position[1] = "庚";
        } else if (degree >= 82 && degree < 97) {
            position[0] = "卯";
            position[1] = "酉";
        } else if (degree >= 97 && degree < 112) {
            position[0] = "乙";
            position[1] = "辛";
        } else if (degree >= 112 && degree < 127) {
            position[0] = "辰";
            position[1] = "戌";
        } else if (degree >= 127 && degree < 142) {
            position[0] = "巽";
            position[1] = "乾";
        } else if (degree >= 142 && degree < 157) {
            position[0] = "巳";
            position[1] = "亥";
        } else if (degree >= 157 && degree < 172) {
            position[0] = "丙";
            position[1] = "壬";
        } else if (degree >= 172 && degree < 187) {
            position[0] = "午";
            position[1] = "子";
        } else if (degree >= 187 && degree < 202) {
            position[0] = "丁";
            position[1] = "癸";
        } else if (degree >= 202 && degree < 217) {
            position[0] = "未";
            position[1] = "丑";
        } else if (degree >= 217 && degree < 232) {
            position[0] = "坤";
            position[1] = "艮";
        } else if (degree >= 232 && degree < 247) {
            position[0] = "申";
            position[1] = "寅";
        } else if (degree >= 247 && degree < 262) {
            position[0] = "庚";
            position[1] = "甲";
        } else if (degree >= 262 && degree < 277) {
            position[0] = "酉";
            position[1] = "卯";
        } else if (degree >= 277 && degree < 292) {
            position[0] = "辛";
            position[1] = "乙";
        } else if (degree >= 292 && degree < 307) {
            position[0] = "戌";
            position[1] = "辰";
        } else if (degree >= 307 && degree < 322) {
            position[0] = "乾";
            position[1] = "巽";
        } else if (degree >= 322 && degree < 337) {
            position[0] = "亥";
            position[1] = "巳";
        } else if (degree >= 337 && degree < 352) {
            position[0] = "壬";
            position[1] = "丙";
        } else if ((degree >= 352 && degree < 361) || (degree >= 0 && degree < 7)) {
            position[0] = "子";
            position[1] = "午";
        }
        return position;
    }
}
