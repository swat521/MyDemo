package vip.pk.pklib.utils;

import java.math.BigDecimal;
import java.util.Random;


public class RandomUtils {

    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS             = "0123456789";
    public static final String LETTERS             = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS     = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS  = "abcdefghijklmnopqrstuvwxyz";

    private RandomUtils() {
        throw new AssertionError();
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase, lowercase letters and numbers
     * 
     * @param length  length
     * @return  RandomUtils
     */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of numbers
     * 
     * @param length  length
     * @return  RandomUtils
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase and lowercase letters
     * 
     * @param length  length
     * @return  RandomUtils
     */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase letters
     * 
     * @param length  length
     * @return   CapitalLetters
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of lowercase letters
     * 
     * @param length  length
     * @return  get a fixed-length random string, its a mixture of lowercase letters
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of chars in source
     * 
     * @param source  source
     * @param length  length
     * @return  get a fixed-length random string, its a mixture of chars in source
     */
    public static String getRandom(String source, int length) {
        return StringUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    /**
     * get a fixed-length random string, its a mixture of chars in sourceChar
     * 
     * @param sourceChar    sourceChar
     * @param length  length
     * @return   get a fixed-length random string, its a mixture of chars in sourceChar
     */
    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }


    /**
     *
     * @param max  接受的数值
     * @return  返回一个随机的数值
     */
    public static int getRandom(int max) {

        return getRandom(0, max);
    }


    /**
     *
     * @param min  最小
     * @param max  最大
     * @return  返回一个范围的数值
     */
    public static int getRandom(int min, int max) {

        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    /**
     * Shuffling algorithm, Randomly permutes the specified array using a default source of randomness
     * 
     * @param objArray  数组
     * @return 从新的数组
     */
    public static boolean shuffle(Object[] objArray) {
        if (objArray == null) {
            return false;
        }

        return shuffle(objArray, getRandom(objArray.length));
    }

    /**
     * Shuffling algorithm, Randomly permutes the specified array
     * 
     * @param objArray  数组
     * @param shuffleCount  洗的个数
     * @return  是否成功
     */
    public static boolean shuffle(Object[] objArray, int shuffleCount) {
        int length;
        if (objArray == null || shuffleCount < 0 || (length = objArray.length) < shuffleCount) {
            return false;
        }

        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            Object temp = objArray[length - i];
            objArray[length - i] = objArray[random];
            objArray[random] = temp;
        }
        return true;
    }

    /**
     * Shuffling algorithm, Randomly permutes the specified int array using a default source of randomness
     * 
     * @param intArray  数组
     * @return  洗牌之后
     */
    public static int[] shuffle(int[] intArray) {
        if (intArray == null) {
            return null;
        }

        return shuffle(intArray, getRandom(intArray.length));
    }

    /**
     * Shuffling algorithm, Randomly permutes the specified int array
     * 
     * @param intArray   数组
     * @param shuffleCount  范围
     * @return  新的数组
     */
    public static int[] shuffle(int[] intArray, int shuffleCount) {
        int length;
        if (intArray == null || shuffleCount < 0 || (length = intArray.length) < shuffleCount) {
            return null;
        }

        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }


    /**
     * @Title: randomLonLat
     * @Description: 在矩形内随机生成经纬度
     * @param MinLon：最新经度  MaxLon： 最大经度   MinLat：最新纬度   MaxLat：最大纬度    type：设置返回经度还是纬度
     * @return
     * @throws
     */
    public static Double randomLonLat(double MinLon, double MaxLon, double MinLat, double MaxLat, String type) {
        Random random = new Random();
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        //String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        Double lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();// 小数后6位
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        //String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        Double lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (type.equals("Lon")) {
            return lon;
        } else {
            return lat;
        }
    }
}
