package com.baofu.cbpayservice.common.util.other;

import java.util.*;

public class CollectionUtil {


    public static Object[] List2Array(List<Object> oList) {
        Object[] oArray = oList.toArray(new Object[]{});
        return oArray;
    }

    public static String[] Set2StringArray(Set<String> oSet) {
        String[] oArray = oSet.toArray(new String[]{});
        return oArray;
    }

    public static Integer[] Set2IntegerArray(Set<Integer> oSet) {
        Integer[] oArray = oSet.toArray(new Integer[]{});
        return oArray;
    }

    public static <T extends Object> List<T> Set2List(Set<T> oSet) {
        List<T> tList = new ArrayList<T>(oSet);
        return tList;
    }

    public static <T extends Object> List<T> Array2List(T[] tArray) {
        List<T> tList = new ArrayList<T>(Arrays.asList(tArray));
        return tList;
    }

    public static <T extends Object> Set<T> List2Set(List<T> tList) {
        Set<T> tSet = new HashSet<T>(tList);
        return tSet;
    }

    public static <T extends Object> Set<T> Array2Set(T[] tArray) {
        Set<T> tSet = new HashSet<T>(Arrays.asList(tArray));
        return tSet;
    }


}
