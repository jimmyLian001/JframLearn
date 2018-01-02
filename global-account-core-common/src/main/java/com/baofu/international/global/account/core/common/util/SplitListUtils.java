package com.baofu.international.global.account.core.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 分割List
 * <p>
 * User: feng_jiang Date:2017/11/7 ProjectName: globalaccount-core Version: 1.0
 */
@Slf4j
public class SplitListUtils {
    private SplitListUtils() {
    }

    /**
     * 分割list
     *
     * @param tList      需要分割的list
     * @param batchCount 每个list大小
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> tList, int batchCount) {
        List<List<T>> list = Lists.newArrayList();
        int sourListSize = tList.size();
        log.info("list 总条数——> {}", sourListSize);
        log.info("sourListSize%batchCount——> {}，{}", sourListSize % batchCount, sourListSize / batchCount);
        int subCount = sourListSize % batchCount == 0 ? sourListSize / batchCount : sourListSize / batchCount + 1;
        log.info("截取的次数——> {}", subCount);
        int start = 0;
        int last = 0;
        for (int i = 0; i < subCount; i++) {
            //如果是最后一次截取，list取模看是否有余数，有余数 截取数量 = 上次截取位置 + 余数；没有余数 截取数量 = 上次截取位置 + 每次截取的大小；
            last = (i == subCount - 1 && sourListSize % batchCount != 0) ? last + sourListSize % batchCount : last + batchCount;
            log.info("截取最后的条数——> {}", last);
            List<T> subList = tList.subList(start, last);
            log.info("截取结果——>{}", subList);
            list.add(subList);
            start = last;
        }
        return list;
    }
}
