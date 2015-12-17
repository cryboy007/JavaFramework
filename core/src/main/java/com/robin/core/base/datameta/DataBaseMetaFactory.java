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
package com.robin.core.base.datameta;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

public class DataBaseMetaFactory {
	public static BaseDataBaseMeta getDataBaseMetaByType(String type,DataBaseParam param){
		BaseDataBaseMeta datameta=null;
		try{
		List<String> dbTypes=Arrays.asList(BaseDataBaseMeta.dbTypeEnmu);
		if(dbTypes.contains(type)){
			String className="cn.com.talkweb.core.base.datameta."+type+"DataBaseMeta";
			Class<?> clazz= Class.forName(className);
			Constructor<?> construct=clazz.getDeclaredConstructor(new Class[]{DataBaseParam.class});
			datameta=(BaseDataBaseMeta) construct.newInstance(param);
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return datameta;
	}
}