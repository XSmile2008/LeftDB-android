/*
 * Copyright 2017 Andrii Horishnii
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

package com.github.andreyrage.leftdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.andreyrage.leftdb.entities.SerializableObject;
import com.github.andreyrage.leftdb.utils.SerializeUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;

public class DBUtilsUpdate extends LeftDBUtils {

	public static DBUtilsUpdate newInstance(Context context, String name, int version) {
		DBUtilsUpdate dbUtils = new DBUtilsUpdate();
		dbUtils.setDBContext(context, name, version);
		return dbUtils;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onCreate(db);
		createTable(db, SerializableObject.class);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onDowngrade(db, oldVersion, newVersion);
		LeftDBHandler dbHandler = getDbHandler();
		try {
			Field field = dbHandler.getClass().getDeclaredField("name");
			field.setAccessible(true);
			field.set(dbHandler, "update2.sqlite");
			upgradeRows(db);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String serializeObject(Object object) {
		try {
			return Arrays.toString(SerializeUtils.serialize(object));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected <T> T deserializeObject(String string, Class<T> tClass, Type genericType) {
		String[] byteValues = string.substring(1, string.length() - 1).split(",");
		byte[] bytes = new byte[byteValues.length];
		for (int i=0, len=bytes.length; i<len; i++) {
			bytes[i] = Byte.parseByte(byteValues[i].trim());
		}

		try {
			Object o = SerializeUtils.deserialize(bytes);
			if (o != null) {
				return tClass.cast(o);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
