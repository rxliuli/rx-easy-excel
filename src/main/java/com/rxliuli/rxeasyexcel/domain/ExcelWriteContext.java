package com.rxliuli.rxeasyexcel.domain;

import com.rxliuli.rxeasyexcel.internal.util.ExcelBeanHelper;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * excel上下文
 * 针对每一个sheet
 *
 * @author Quding Ding
 * @since 2018/6/27
 */
public class ExcelWriteContext {
    /**
     * 数据源
     */
    private List<Map<String, Object>> datasource;
    /**
     * excel头
     */
    private LinkedHashMap<String, ExcelWriterHeader> headers;
    /**
     * 创建工作目录后的hook
     */
    private BiConsumer<Sheet, ExcelWriteContext> createSheetHook = (w, v) -> {
    };
    /**
     * 起始行
     */
    private int startRow = 0;

    private boolean isTemplateExport;

    private String sheetName;
    /**
     * 错误列表
     */
    private List<ExcelImportError> errors = Collections.emptyList();

    public ExcelWriteContext(boolean isTemplateExport) {
        this.isTemplateExport = isTemplateExport;
    }

    public boolean isTemplateExport() {
        return isTemplateExport;
    }

    public static ExcelWriteContextBuilder builder() {
        return new ExcelWriteContextBuilder(false);
    }

    public static ExcelWriteContextBuilder builder(boolean isTemplateExport) {
        return new ExcelWriteContextBuilder(isTemplateExport);
    }

    // package set

    public List<Map<String, Object>> getDatasource() {
        return datasource;
    }

    <T> ExcelWriteContext setDatasource(List<T> datasource) {
        // 处理空情况
        if (null == datasource || datasource.isEmpty()) {
            this.datasource = Collections.emptyList();
            return this;
        }
        this.datasource = ExcelBeanHelper.beanToMap(datasource);
        return this;
    }

    public LinkedHashMap<String, ExcelWriterHeader> getHeaders() {
        return headers;
    }

    /**
     * 根据bean确定header
     *
     * @param bean bean
     */
    <T> ExcelWriteContext setHeaders(T bean) {
        this.headers = ExcelBeanHelper.beanToWriterHeaders(bean, this);
        return this;
    }

    ExcelWriteContext setHeaders(LinkedHashMap<String, ExcelWriterHeader> headers) {
        this.headers = headers;
        return this;
    }

    public BiConsumer<Sheet, ExcelWriteContext> getCreateSheetHook() {
        return createSheetHook;
    }

    // public get

    ExcelWriteContext setCreateSheetHook(BiConsumer<Sheet, ExcelWriteContext> createSheetHook) {
        this.createSheetHook = createSheetHook;
        return this;
    }

    public int getStartRow() {
        return startRow;
    }

    ExcelWriteContext setStartRow(int startRow) {
        this.startRow = startRow;
        return this;
    }

    public String getSheetName() {
        return sheetName;
    }

    ExcelWriteContext setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public List<ExcelImportError> getErrors() {
        return errors;
    }

    public ExcelWriteContext setErrors(List<ExcelImportError> errors) {
        this.errors = errors;
        return this;
    }
}
