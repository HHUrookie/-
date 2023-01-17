package com.jwking.community.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SensitiveFilter {
    //替换符
    private static final String REPLACE = "***";
    //根节点
    private TrieNode rootNode = new TrieNode();
    //会在spring容器执行该类的构造方法后自动执行
    @PostConstruct
    public void init() {
        try(
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String keyWord;
            while ((keyWord = reader.readLine()) != null) {
                //添加到前缀树
                this.addKeyWord(keyWord);
            }

        } catch (IOException e) {
            log.error("加载敏感文件失败" + e.getMessage());
        }

    }

    private void addKeyWord(String keyword) {

        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); ++i) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
                //初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            //指向子节点，进入下一轮
            tempNode = subNode;
            //设置结束标识
            if (i == keyword.length()-1) {
                tempNode.setKeyWordEnd(true);
            }

        }
    }

    /**
     * 过滤敏感词
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String getResult(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        //指针1
        TrieNode tempNode = rootNode;
        //指针2
        int begin = 0;
        //指针3
        int position = 0;

        //结果
        StringBuilder result = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);
            //跳过符号
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    // 若指针1处于根节点，将此符号计入结果
                    result.append(c);
                    begin++;
                }
                //无论符号在开头或者中间，指针3都向下走一步
                position++;
                continue;
            }
            //检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                //position所在的字符不是敏感词
                result.append(text.charAt(begin));
                //进入下一个位置
                position = ++begin;
                //回到根节点
                tempNode = rootNode;
            } else if (tempNode.isKeyWordEnd()) {
                //发现了敏感词，将begin-position之间的词替换掉
                result.append(REPLACE);
                //进入下一个位置
                begin = ++position;
                //回到根节点
                tempNode = rootNode;
            } else {
                //检查下一个字符
                position++;
            }
        }
        //将最后一批字符计入结果
        result.append(text.substring(begin));
        return result.toString();
    }

    //判断是否为符号
    private boolean isSymbol(Character c) {
        //0x2E80 ~ 0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    //定义前缀树内部类
    private class TrieNode {
        //关键词结束的标识
        private boolean isKeyWordEnd = false;
        //子节点
        private Map<Character,TrieNode> subNodes = new HashMap<>();

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }
        //添加子节点
        public void addSubNode (Character c,TrieNode node) {
            subNodes.put(c,node);
        }
        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}
