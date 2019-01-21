package org.open.software.utils.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Option {
	private String name;
	private boolean complete;
	private String[] values;

	public Option(String value) {

		int i = value.indexOf("=");
		if (i != -1) {
			this.name = value.substring(0, i);
			set(value.substring(i + 1));
		} else {
			this.name = value;
		}
	}

	public void set(String o) {
		List<String> valuesList = new ArrayList<>();
		
		while(o.isEmpty() == false) {
			String p = null;
			int i;
			if(o.startsWith("\"")) {
				// next quote
				i = o.indexOf("\"", 1);
				
				if(i != -1) {
					p = o.substring(1, i);
					o = o.substring(i + 1);
				}
			} else if((i = o.indexOf(",")) != -1) {
				p = o.substring(0, i);
				o = o.substring(i);
			} else {
				p = o;
				o= "";
			}
			
			if(o.startsWith(",")) {
				o = o.substring(1);
			}
			
			valuesList.add(p);
		}
		
		values = valuesList.toArray(new String[] {});
		complete = true;
	}

	public <T> T get(Class<T> type, String value) {

		if (Boolean.class.equals(type)) {
			return type.cast(((value == null) || Boolean.valueOf(value)));
		} else if (value != null) {
			if (String.class.equals(type)) {
				return type.cast(value);
			} else if (Integer.class.isAssignableFrom(type)) {
				return (T) Integer.valueOf(value);
			}
			if (Long.class.isAssignableFrom(type)) {
				return (T) Long.valueOf(value);
			}
			if (Float.class.isAssignableFrom(type)) {
				return (T) Float.valueOf(value);
			}
			if (Double.class.isAssignableFrom(type)) {
				return (T) Double.valueOf(value);
			}
		}

		return null;
	}
	
	public <T> T get(Class<T> type) {
		return get(type, values != null ? values[0] : null);
	}
	
	public <T> List<T> list(Class<T> type) {
		List<T> result = new ArrayList<>();
		Arrays.asList(values).stream().forEach(c -> {
			try {
				result.add(get(type, c));
			}catch(Exception e) {
				// IGN
			}			
		});
		
		return result;
	}
	
	String getName() {
		return name;
	}
	
	boolean isComplete() {
		return complete;
	}
}