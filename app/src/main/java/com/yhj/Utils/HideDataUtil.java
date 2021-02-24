package com.yhj.Utils;


/**
 * Created by YF on 2020/3/9.
 */
public class HideDataUtil {

    /**
     * 对重要数据进行隐藏处理
     * @return
     */
    public static String bankCardReplaceWithStar(String bankCard) {

        if (bankCard.isEmpty() || bankCard ==null) {

            return null;

        }else {

            return replaceAction(bankCard,"(?<=\\d{4})\\d(?=\\d{3})");

        }

    }


    public static String zfbCardReplaceWithStar(String bankCard) {

        if (bankCard.isEmpty() || bankCard ==null) {

            return null;

        }else {

            return replaceAction(bankCard,"(?<=\\d{3})\\d(?=\\d{2})");

        }

    }
     private static String replaceAction(String username, String regular) {

        return username.replaceAll(regular,"*");//这里的*号可以替换成自己想要的符号

    }


}


