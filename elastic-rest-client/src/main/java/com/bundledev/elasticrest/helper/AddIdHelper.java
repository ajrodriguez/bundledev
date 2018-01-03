package com.bundledev.elasticrest.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.bundledev.elasticrest.exception.HelperException;

import lombok.extern.slf4j.Slf4j;

/**
 * Contains methods that have a utility characteristic for this library
 */
@Slf4j
public class AddIdHelper {

	private static final String METHOD_NAME = "setId";

	/**
	 * Uses the provided id to call the methods <code>setId(String id)</code> of
	 * the provided entity instance
	 * 
	 * @param id
	 *            String containing the id to set
	 * @param source
	 *            Entity object that contains the <code>setId</code> method
	 * @param <T>
	 *            Type of the provided entity object
	 */
	public static <T> void addIdToEntity(String id, T source) {
		addIdToEntity(id, source, METHOD_NAME);
	}

	/**
	 * Uses the provided id to call the methods with the provided name of the
	 * provided entity instance
	 * 
	 * @param id
	 *            String containing the id to set
	 * @param source
	 *            Entity object that contains the <code>setId</code> method
	 * @param <T>
	 *            Type of the provided entity object
	 */
	public static <T> void addIdToEntity(String id, T source, String methodName) {
		Method setIdMethod;
		try {
			setIdMethod = source.getClass().getMethod(methodName, String.class);
			setIdMethod.invoke(source, id);
		} catch (NoSuchMethodException | InvocationTargetException e) {
			String message = String.format("The provided method '%s' is not available.", methodName);
			log.error("method:{}()|message:\'{}\'|exception:{}", methodName, message, e.getMessage() != null ? e.getMessage() : "NULL");
			throw new HelperException(message);
		} catch (IllegalAccessException e) {
			String message = String.format("Id argument '%s' seems to be wrong", id);
			log.error("method:{}()|message:\'{}\'|exception:{}", methodName, message, e.getMessage() != null ? e.getMessage() : "NULL");
			throw new HelperException("Id argument seems to be wrong");
		}

	}
}
