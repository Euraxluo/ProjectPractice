package com.lailelaodi.pojo;

import java.util.List;

/**
 * @ClassName PageBean
 * @Description Student分页
 * @Author Euraxluo
 * @Date 18-12-21 上午11:09
 */
public class PageBean<T> {
    private int currentPage;
    private int totalPage;
    private int pageSize;
    private int totalSize;
    private List<T> list;

    public PageBean(List<T> list) {
        this.currentPage = 1;
        this.totalPage = 1;
        this.pageSize = 1;
        this.totalSize = 1;
        this.list = list;
    }

    public PageBean() {
    }

    public PageBean(int currentPage, int totalPage, int pageSize, int totalSize, List<T> list) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.list = list;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public List<T> getList() {
        return list;
    }
}
