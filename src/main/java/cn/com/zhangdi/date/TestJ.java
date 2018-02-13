package cn.com.zhangdi.date;

/**
 * Created by zhangdi on 2017/07/25.
 */
public class TestJ {

    public static void main(String[] args) {
        DateTools dateTools = new DateTools();
        String[] allDay = dateTools.getDayBetweenDates("20170515", "20170717", "yyyyMMdd");
        for(String day:allDay){
            System.out.println(day);
        }
    }
}
