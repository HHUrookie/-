package com.jwking.community.pojo;


import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
/**
 * 封装分页相关的信息
 */
public class Page {
    //默认一页的数量
    public static Integer DEFAULT_NUMBERS = 10;
    //当前页码
    private Integer current = 1;
    //一页的数量
    private Integer numbers = DEFAULT_NUMBERS;
    //总数
    private Integer total;
    //查询路径
    private String path;

    public void setCurrent(Integer current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public void setNumbers(Integer numbers) {
        if (numbers >= 1 && numbers <= 100) {
            this.numbers = numbers;
        }
    }

    public void setTotal(Integer total) {
        if (total >= 0) {
            this.total = total;
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取起始值
     * @return 起始值
     */
    public Integer getBegin() {
        return (this.getCurrent() - 1) * this.getNumbers();
    }

    /**
     *
     * @return 总页数
     */
    public Integer getPageNumber() {
        if (total % numbers > 0) {
            return total / numbers + 1;
        }
        return total / numbers;
    }

    /**
     *
     * @return 起始页
     */
    public Integer getBeginPage() {
        Integer from = current - 2;
        return from < 1? from : 1;
    }

    /**
     *
     * @return 末页
     */
    public Integer getEndPage() {
        Integer end = current + 2;
        return end > total? getPageNumber() : end;
    }
}
