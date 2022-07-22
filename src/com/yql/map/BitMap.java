package com.yql.map;


import java.util.Arrays;

/**
 * @author yangqianlei
 * @date 2022/07/22
 */
public class BitMap<V> {
    /*十亿手机号 去重
     *方案一：数组 ，占用内存大，效率低，不考虑
     *15001036720
     *long   8字节*1000000000=(8000000000/1024/1024/1024)GB=7GB
     *string 11字节*1000000000=(11000000000/1024/1024/1024)GB=10GB
     *方案二：Hashmap: hashmap包括hash/value/key/next节点，内存占用更高，不考虑
     *方案三：bitmap:该方案  布隆过滤器   参考BitSet
     */

    private static final int ADDRESS_BITS_PER_WORD = 6;  //long=8byte=8*8bit=1<<6bit
    private static final int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;  //wordBits=1<<6=64bit
    private static final int BIT_INDEX_MASK = BITS_PER_WORD - 1;
    private long[] words;
    private long wordCount = 0;

    public BitMap() {
        initWord(BITS_PER_WORD);
    }

    public void initWord(int bitIndex) {
        words = new long[wordIndex(bitIndex - 1) + 1];
    }

    public void set(V v) {
        if (contains(v)) {
            return;
        }
        int bitIndex = hash(v);
        int wordIndex = wordIndex(bitIndex);
        expandTo(wordIndex);
        words[wordIndex] |= 1L << bitIndex;
        wordCount++;
    }

    public boolean contains(V v) {
        int bitIndex = hash(v);
        int wordIndex = wordIndex(bitIndex);
        expandTo(wordIndex);
        return (words[wordIndex] & (1L << bitIndex)) == 1L << bitIndex;
    }

    public long getWordCount() {
        return wordCount;
    }

    public int wordIndex(int bitIndex) {
        return bitIndex >> ADDRESS_BITS_PER_WORD;
    }

    public int hash(Object value) {
        int h;
        return (value == null) ? 0 : ((h = Math.abs(value.hashCode())) ^ (h >>> 16));
    }

    public void expandTo(int wordIndex) {
        int wordRequiredIndex = wordIndex + 1;
        if (words.length < wordRequiredIndex) {
            words = Arrays.copyOf(words, wordRequiredIndex);
        }
    }
}
