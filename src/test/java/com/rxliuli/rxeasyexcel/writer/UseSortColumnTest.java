package com.rxliuli.rxeasyexcel.writer;

import com.rxliuli.rxeasyexcel.EasyExcel;
import com.rxliuli.rxeasyexcel.annotation.ExcelField;
import com.rxliuli.rxeasyexcel.domain.ExcelReadContext;
import com.rxliuli.rxeasyexcel.domain.ExcelWriteContext;
import com.rxliuli.rxeasyexcel.read.ExcelReader;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试使用列排序
 *
 * @author rxliuli
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UseSortColumnTest {
    private final String currentPath = DateFieldTest.class.getClassLoader().getResource(".").getPath();
    private final int count = 5;
    private String fileName = currentPath + "/UseSortColumnTest.xlsx";

    static String join(Collection<?> collection) {
        return collection.stream()
                .map(ToStringBuilder::reflectionToString)
                .collect(Collectors.joining("\n"));
    }

    @Test
    @Order(1)
    void exportDateList() {
        final List<Person> users = mockUser(count);
        EasyExcel.export(fileName)
                .export(ExcelWriteContext.builder()
                        .datasource(users)
                        .sheetName("user")
                        .build())
                .write();
    }

    @Test
    @Order(2)
    void importDateList() throws FileNotFoundException {
        try (ExcelReader reader = EasyExcel.read(new FileInputStream(fileName))) {
            List<Person> result = reader.resolve(ExcelReadContext.<Person>builder()
                    .clazz(Person.class)
                    .build())
                    .getData();
            System.out.println(join(result));
            assertThat(result)
                    .hasSize(count);
        }
    }

    private List<Person> mockUser(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new Person("姓名 " + i, new Date(), LocalDate.now(), LocalTime.now()))
                .collect(Collectors.toList());
    }

    public static class Person {
        @ExcelField(columnName = "姓名", order = 1)
        private String username;
        @ExcelField(columnName = "日期", order = 2)
        private Date date;
        @ExcelField(columnName = "本地日期", order = 3)
        private LocalDate localDate;
        @ExcelField(columnName = "本地时间", order = 4)
        private LocalTime localTime;

        public Person() {
        }

        public Person(String username, Date date, LocalDate localDate, LocalTime localTime) {
            this.username = username;
            this.date = date;
            this.localDate = localDate;
            this.localTime = localTime;
        }

        public Date getDate() {
            return date;
        }

        public Person setDate(Date date) {
            this.date = date;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public Person setUsername(String username) {
            this.username = username;
            return this;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("username", username)
                    .append("date", date)
                    .append("localDate", localDate)
                    .append("localTime", localTime)
                    .toString();
        }
    }
}
