package com.robin.core.base.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.text.StrBuilder;

public class StringUtils {
	/**
	 * custom String split 
	 * @param str   
	 * @param delimer   
	 * @param excludeArr  char must exclude for example like \","{:}"
	 * @return
	 */
	public static String[] split(String str,char delimer,String[] excludeArr){
		char[] chars=str.toCharArray();
		List<String> list=new ArrayList<String>();
		String[] arrs=(excludeArr!=null && excludeArr.length>0)?excludeArr:new String[]{"\"","'"};
		List<Character> includeList=new ArrayList<Character>();//Arrays.asList(arrs);
		List<Character> includeSuffixList=new ArrayList<Character>();
		for (int i = 0; i < arrs.length; i++) {
			if(!arrs[i].contains(":")){
				includeList.add(Character.valueOf(arrs[i].charAt(0)));
				includeSuffixList.add(Character.valueOf(arrs[i].charAt(0)));
			}else{
				String[] sepArr=arrs[i].split(":");
				includeList.add(Character.valueOf(sepArr[0].charAt(0)));
				includeSuffixList.add(Character.valueOf(sepArr[1].charAt(0)));
			}
		}
		int start=0;
		int length=str.length();
		int i=0;
		Character curstr=null;
		int selpos=0;
		//start pos
		boolean startpos=true;
		while (i<length) {
			curstr=Character.valueOf(chars[i]);
			if(startpos && includeList.contains(curstr)){
				if(start==i){
					start=++i;
					selpos=includeList.indexOf(curstr);
					while(i<length && !Character.valueOf(chars[i]).equals(includeSuffixList.get(selpos))){
						i++;
					}
					list.add(str.substring(start,i));
					if(i+2<chars.length){
						i+=2;
						start=i;
					}
					else{
						start=length;
						i=length;
					}
					startpos=true;
				}
			}else if(chars[i]!=delimer){
				i++;
				startpos=false;
			}else{
				if(start==i){
					list.add("");
					i++;
					start=i;
				}else{
					list.add(str.substring(start,i));
					if(i<length){
						i++;
						while(i<length && chars[i]==delimer){
							list.add("");
							i++;
						}
					}
					startpos=true;
				}
				start=i;
			}
		}
		if(start<length){
			int pos=length;
			for (int j = 0; j < includeSuffixList.size(); j++) {
				if(str.endsWith(includeSuffixList.get(j).toString())){
					pos--;
					break;
				}
			}
			
			list.add(str.substring(start,pos));
		}
		if(str.endsWith(new String(new char[]{delimer}))){
			list.add("");
		}
		String[] retArr=list.toArray(new String[1]);
		return retArr;
	}
	public static String[] split(String str,char delimer){
		return split(str, delimer, null);
	}
	public static String getStackTrace(Exception ex){
		StringWriter writer=new StringWriter();
		PrintWriter wr=new PrintWriter(writer);
		String errMsg=null;
		try  
	    {  
	        ex.printStackTrace(wr);  
	        errMsg=writer.toString();  
	    } catch(Exception e1){
	    	e1.printStackTrace();	
	    }
	    finally  
	    {  
	    	wr.close();  
	    }  
		return errMsg;
	}
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}
	public static String join(Object[] array, String separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = "";
		}

		int bufSize = endIndex - startIndex;
		if (bufSize <= 0) {
			return "";
		}

		bufSize *= (((array[startIndex] == null) ? 16 : array[startIndex]
				.toString().length()) + separator.length());

		StrBuilder buf = new StrBuilder(bufSize);

		for (int i = startIndex; i < endIndex; ++i) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}
	public static int getSplitCharInt(String split){
		int retchar=0;
		if(split.equals("\\t")){
			retchar=10;
		}else if(split.equalsIgnoreCase("0x1F")){
			retchar=31;
		}else {
			retchar=split.charAt(0);
		}
		return retchar;
	}
	public static String getSplitChar(String split){
		String retchar="";
		if(split.equals("\\t")){
			retchar="\t";
		}else if(split.equalsIgnoreCase("0x1F")){
			retchar=String.valueOf('\u001F');
		}else {
			retchar=split;
		}
		return retchar;
	}
	public static String initailCharToUpperCase(String input){
		if(input.length()>2)
			return input.substring(0,1).toUpperCase()+input.substring(1,input.length());
		else
			return null;
	}

}