package cn.com.zhangdi.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/***
 * 关于日期时间的各项函数
 * 
 * @author zhangdi
 *
 */
public class DateTools
{
	/***
	 * 获取指定格式的当前日期
	 * 
	 * @param strFormat
	 * @return
	 */
	public String getNowDateFormat(String strFormat)
	{
		Date tempDate = new Date(Calendar.getInstance().getTimeInMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
		return dateFormat.format(tempDate);
	}

	/**
	 * 将整数形式的秒转换为指定格式的日期字符串
	 */
	public String longTime2FormatStr(long lTimeSec, String format)
	{
		if (format == null)
			return "";
		Date tempDate = new Date(lTimeSec * 1000);
		SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd HH:mm");
		try
		{
			dateformat = new SimpleDateFormat(format);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return dateformat.format(tempDate);
	}

	/***
	 * 将指定格式的字符串转化为秒
	 * 
	 * @param time
	 * @param strFormat
	 * @return
	 */
	public long StrTime2Long(String time, String strFormat)
	{
		long seconds = 0;
		try
		{
			SimpleDateFormat sdm = new SimpleDateFormat(strFormat);
			sdm.parse(time);
			Calendar cal = sdm.getCalendar();
			seconds = cal.getTimeInMillis() / 1000;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return seconds;
	}

	/***
	 * 计算出两个日期之间所有日期,eg:strBegin=2010-01-28,strEnd=2010-02-02
	 * 
	 * @param strBegin
	 *            开始日期
	 * @param strEnd
	 *            结束日期
	 * @param strFormat
	 *            日期格式
	 * @return
	 */
	public String[] getDayBetweenDates(String strBegin, String strEnd, String strFormat)
	{
		List<String> listDate = new ArrayList<String>();
		try
		{
			Date dateBegin = new SimpleDateFormat(strFormat).parse(strBegin);
			Date dateEnd = new SimpleDateFormat(strFormat).parse(strEnd);
			Calendar calendarTemp = Calendar.getInstance();
			calendarTemp.setTime(dateBegin);
			while (calendarTemp.getTime().getTime() <= dateEnd.getTime())
			{
				listDate.add(new SimpleDateFormat(strFormat).format(calendarTemp.getTime()));
				calendarTemp.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return (String[]) listDate.toArray(new String[listDate.size()]);
	}

	public String[] getDayBetweenDates(long lBeginSec, long lEndSec, String strFormat)
	{
		String strBegin = longTime2FormatStr(lBeginSec, strFormat);
		String strEnd = longTime2FormatStr(lEndSec, strFormat);
		return getDayBetweenDates(strBegin, strEnd, strFormat);
	}

	/***
	 * 根据输入的日期的到前或后N天的日期
	 * 
	 * @param strDate
	 *            输入日期
	 * 
	 * @param strFormat
	 *            日期格式
	 * 
	 * @param nDay
	 *            负数为前N天，正数为后N天
	 * @return
	 */
	public String getBeforNDay(String strDate, String strFormat, int nDay)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
		Calendar cal = Calendar.getInstance();
		try
		{
			cal.setTime(dateFormat.parse(strDate));
			cal.add(Calendar.DATE, nDay);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return dateFormat.format(cal.getTime());
	}
}
