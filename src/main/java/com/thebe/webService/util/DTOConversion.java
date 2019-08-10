package com.thebe.webService.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Class to convert Object to DTO and vice versa
 * @author Thebe.Alfarisi
 * @since Dec, 12th 2018
 * @version 1.0
 *
 */
public class DTOConversion {
	
	public static final Class<?> commonFieldType[] = {
		Integer.class, String.class, Double.class, Timestamp.class, Date.class, java.sql.Date.class 
	};
			
	
	/**
	 * Method to map and convert data from {@link Object} entity to {@link Object} dto
	 * @param dto target {@link Object} dto
	 * @param entity source {@link Object} entity
	 * @return {@link Object} dto result
	 * @throws Exception
	 */
	public static Object toDto(Object dto, Object entity) throws Exception {
		
		Field[] dtoFields01 = dto.getClass().getDeclaredFields();
		Field[] dtoFields02 = dto.getClass().getSuperclass().getDeclaredFields();
		Field[] dtoFields = ArrayUtils.addAll(dtoFields01, dtoFields02);
		
		Field[] entityFields01 = entity.getClass().getDeclaredFields();
		Field[] entityFields02 = entity.getClass().getSuperclass().getDeclaredFields();
		Field[] entityFields = ArrayUtils.addAll(entityFields01, entityFields02);
		
		if (dtoFields.length > 0) {
			for(Field dtoField : dtoFields) {
				dtoField.setAccessible(true);
				if (!dtoField.getName().equalsIgnoreCase("serialVersionUID")) {
					for(Field entityField : entityFields) {
						entityField.setAccessible(true);
						if (dtoField.getName().equalsIgnoreCase(entityField.getName())) {
							if (!entityField.getName().equalsIgnoreCase("serialVersionUID")) {
								boolean isInclude = false;
								for (Class<?> checkType : commonFieldType) {
									if (entityField.getType().equals(checkType)) {
										isInclude = true;
										break;
									}
								}
								if (isInclude) {
									String value = "";
									if (entityField.get(entity) != null) {
										value = String.valueOf(entityField.get(entity));
									}
									dtoField.set(dto, value);
								} else {
									String childName = dtoField.getName();
									Object childObj = entityField.get(entity);
									if (childObj != null) {
										Field[] childFields = childObj.getClass().getDeclaredFields();
										for (Field childField : childFields) {
											childField.setAccessible(true);
											if (childField.getName().equalsIgnoreCase(childName)) {
												String value = "";
												if (childField.get(childObj) != null) {
													value = String.valueOf(childField.get(childObj));
												}
												dtoField.set(dto, value);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return dto;
	}
	
	/**
	 * Method to map and convert data from {@link Object} dto to {@link Object} entity
	 * @param dto source {@link Object} dto
	 * @param entity target {@link Object} entity
	 * @return {@link Object} entity result
	 * @throws Exception
	 */
	public static Object fromDto(Object dto, Object entity) throws Exception {
		
		Field[] dtoFields01 = dto.getClass().getDeclaredFields();
		Field[] dtoFields02 = dto.getClass().getSuperclass().getDeclaredFields();
		Field[] dtoFields = ArrayUtils.addAll(dtoFields01, dtoFields02);
		
		Field[] entityFields01 = entity.getClass().getDeclaredFields();
		Field[] entityFields02 = entity.getClass().getSuperclass().getDeclaredFields();
		Field[] entityFields = ArrayUtils.addAll(entityFields01, entityFields02);
		
		if (dtoFields.length > 0) {
			for(Field dtoField : dtoFields) {
				dtoField.setAccessible(true);
				if (!dtoField.getName().equalsIgnoreCase("serialVersionUID")) {
					for(Field entityField : entityFields) {
						entityField.setAccessible(true);
						if (dtoField.getName().equalsIgnoreCase(entityField.getName())) {
							if (!entityField.getName().equalsIgnoreCase("serialVersionUID")) {
								if (dtoField.get(dto) != null) {
									boolean isInclude = false;
									for (Class<?> checkType : commonFieldType) {
										if (entityField.getType().equals(checkType)) {
											isInclude = true;
											break;
										}
									}
									if (isInclude) {
										String value = "";
										if (dtoField.get(dto) != null) {
											value = String.valueOf(dtoField.get(dto));
										}
										if (entityField.getType().equals(String.class)) {
											entityField.set(entity, String.valueOf(value));
										} else if (entityField.getType().equals(Integer.class)) {
											entityField.set(entity, Integer.valueOf((String) value));
										} else if (entityField.getType().equals(Timestamp.class)) {
											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
											java.util.Date date = sdf.parse((String) value);
											java.sql.Date sqlDate = new java.sql.Date(date.getTime());
											entityField.set(entity, sqlDate);
										} else if (entityField.getType().equals(Double.class)) {
											entityField.set(entity, Double.valueOf((String) value));
										}
									} else {
										String childName = dtoField.getName();
										Object foreignObject = entityField.getType().newInstance();
										Field[] childFields = foreignObject.getClass().getDeclaredFields();
										for (Field childField : childFields) {
											childField.setAccessible(true);
											if (childField.getName().equalsIgnoreCase(childName)) {
												Integer value = null;
												if (dtoField.get(dto) != null) {
													value = Integer.valueOf((String) dtoField.get(dto));
												}
												childField.set(foreignObject, value);
												break;
											}
										}
										entityField.set(entity, foreignObject);
									}
								}
							}
						}
					}
				}
			}
		}
		return entity;
	}

}
