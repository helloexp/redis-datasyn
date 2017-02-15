package com.hand.util.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class JsonMapper {

	protected ObjectMapper objectMapper;

	public JsonMapper() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
	}

	public void asRuntime(Throwable throwable) {
		if (throwable instanceof RuntimeException) {
			throw (RuntimeException) throwable;
		}
		throw new RuntimeException(throwable);
	}

	public List<Map<String, ?>> convertToList(Collection<String> jsons) {
		List<Map<String, ?>> list = new ArrayList<>();
		try {
			for (String json : jsons) {
				if (json != null) {
					list.add(objectMapper.readValue(json, HashMap.class));
				}
			}
		} catch (IOException e) {
			asRuntime(e);
		}
		return list;
	}

	public String convertToJson(Map<String, ?> map) {
		try {
			return objectMapper.writeValueAsString(map);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, ?> convertToMap(String json) {
		try {
			System.err.println(json);
			return objectMapper.readValue(json, HashMap.class);
		} catch (IOException e) {
			asRuntime(e);
		}
		return null;
	}
}
