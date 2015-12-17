/*
 * Copyright (c) 2015,robinjim(robinjim@126.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robin.core.convert.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DateUtil {

   private static Log    log                = LogFactory.getLog(DateUtil.class);
   private static String defaultDatePattern = "yyyy-MM-dd";
   private static String timePattern        = "HH:mm";

   public static synchronized String getDatePattern() {
      return defaultDatePattern;
   }

   /**
    * 锟斤拷锟斤拷菘锟斤拷卸锟饺★拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷转锟斤拷锟斤拷缺省锟侥革拷式锟斤拷
    * 
    * @param date
    *           要转锟斤拷锟斤拷锟斤拷锟斤拷
    * @return 锟街凤拷锟绞斤拷锟斤拷锟斤拷锟?
    */
   public static final String getDate(Date date) {
      SimpleDateFormat df = null;
      String returnValue = "";

      if (date != null) {
         df = new SimpleDateFormat(getDatePattern());
         returnValue = df.format(date);
      }

      return (returnValue);
   }

   /**
    * 锟斤拷锟斤拷一锟斤拷锟斤拷锟节革拷式锟斤拷一锟斤拷锟街凤拷转锟斤拷锟斤拷锟斤拷锟节★拷
    * 
    * @param mask
    *           锟斤拷锟节革拷式
    * @param strdate
    *           要转锟斤拷锟斤拷锟街凤拷
    * @return 转锟斤拷锟缴碉拷锟斤拷锟斤拷
    * @see java.text.SimpleDateFormat
    * @throws ParseException
    */
   public static final Date convertStringToDate(String mask, String strdate) throws ParseException {
      SimpleDateFormat df = null;
      Date date = null;
      df = new SimpleDateFormat(mask);

      if (log.isDebugEnabled()) log.debug("converting '" + strdate + "' to date with mask '" + mask + "'");

      try {
         date = df.parse(strdate);
      }
      catch (ParseException pe) {
         throw new ParseException(pe.getMessage(), pe.getErrorOffset());
      }

      return (date);
   }

   /**
    * This method returns the current date time in the format: MM/dd/yyyy HH:MM a
    * 
    * @param time
    *           the current time
    * @return the current date/time
    */
   public static String getTimeNow(Date time) {
      return getDateTime(timePattern, time);
   }

   /**
    * 转锟斤拷锟斤拷锟斤拷锟斤拷锟节碉拷锟街凤拷锟绞?
    * 
    * @return 锟斤拷锟斤拷锟斤拷锟节ｏ拷锟斤拷式MM/dd/yyyy
    * @throws ParseException
    */
   public static Calendar getToday() throws ParseException {
      Date today = new Date();
      SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

      String todayAsString = df.format(today);
      Calendar cal = new GregorianCalendar();
      cal.setTime(convertStringToDate(todayAsString));

      return cal;
   }

   /**
    * This method generates a string representation of a date's date/time in the format you specify on input
    * 
    * @param mask
    *           the date pattern the string is in
    * @param date
    *           a date object
    * @return a formatted string representation of the date
    * 
    * @see java.text.SimpleDateFormat
    */
   public static final String getDateTime(String mask, Date date) {
      if (date == null) return null;
      SimpleDateFormat df = new SimpleDateFormat(mask);
      return df.format(date);
   }

   /**
    * This method generates a string representation of a date based on the System Property 'dateFormat' in the format you specify on input
    * 
    * @param date
    *           A date to convert
    * @return a string representation of the date
    */
   public static final String convertDateToString(Date date) {
      return getDateTime(getDatePattern(), date);
   }

   /**
    * This method converts a String to a date using the datePattern
    * 
    * @param strdate
    *           the date to convert (in format MM/dd/yyyy)
    * @return a date object
    * 
    * @throws ParseException
    */
   public static Date convertStringToDate(String strdate) throws ParseException {
      Date aDate = null;

      try {
         if (log.isDebugEnabled()) {
            log.debug("converting date with pattern: " + getDatePattern());
         }

         aDate = convertStringToDate(getDatePattern(), strdate);
      }
      catch (ParseException pe) {
         log.error("Could not convert '" + strdate + "' to a date, throwing exception");
         pe.printStackTrace();
         throw new ParseException(pe.getMessage(), pe.getErrorOffset());

      }

      return aDate;
   }
}
