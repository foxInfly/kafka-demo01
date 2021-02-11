package com.pupu.lp01_simple;

/**
 * 判断offset放在哪个分区存储
 *
 * @author lp
 * @since 2021/2/11 15:55
 */
public class HashCalculate {
    public static void main(String[] args) {
        // 要放在第17个分区存储
        System.out.println(Math.abs("gp-assign-group-1".hashCode()) % 50);
    }
}
