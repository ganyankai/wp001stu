package cn.dante.book.util;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class PageData<T> implements Serializable {
    private long total;
    private List<T> list;
    int pageNo;
    int pageSize;

    public PageData(PageInfo pageInfo) {
        this.total = pageInfo.getTotal();
        this.list = pageInfo.getList();
    }

    public PageData(List<T> list, long total) {
        this.list = list;
        this.total = total;
    }

    public static PageData pageData(PageInfo pageInfo) {
        return new PageData(pageInfo);
    }

    public PageData() {
    }

    public PageData(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public PageData(int pageSize, int pageNo, long total, List<T> list) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.total = total;
        this.list = list;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return this.total;
    }

    public List<T> getList() {
        return this.list;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }
}
