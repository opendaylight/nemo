package org.opendaylight.nemo.tool.eclipse.plugin.rest.actions.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hj on 10/29/15.
 */
public class Property implements INemoItem {
	private String propertyName;
	private List<String> propertyValues;

	public Property(String propertyName, List<String> propertyValues) {
		this.propertyName = propertyName;
		this.propertyValues = propertyValues;
	}

	@Override
	public JSONObject toJSONObject() {
		if (propertyName == null || propertyValues == null) {
			throw new IllegalStateException("Illegal Property: " + toString());
		}
		JSONObject jsProperty = new JSONObject();
		jsProperty.put("property-name", propertyName);

		// JS array,property values
		JSONArray jsonValuesArray = new JSONArray();
		for (int i = 0; i < propertyValues.size(); i++) {
			String value = propertyValues.get(i);
			JSONObject jsonValue = new JSONObject();
			jsonValue.put("order", i + "");
			jsonValue.put("value", value);
			jsonValuesArray.put(i, jsonValue);
		}
		JSONObject pJs = new JSONObject();
		if (propertyName.equals("bandwidth"))
			pJs.put("int-value", jsonValuesArray);
		else
			pJs.put("string-value", jsonValuesArray);
		jsProperty.put("property-values", pJs);

		return jsProperty;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Property property = (Property) o;

		if (propertyName != null ? !propertyName.equals(property.propertyName) : property.propertyName != null)
			return false;
		if (propertyValues != null ? !propertyValues.equals(property.propertyValues) : property.propertyValues != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = propertyName != null ? propertyName.hashCode() : 0;
		result = 31 * result + (propertyValues != null ? propertyValues.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Property{" + "propertyName='" + propertyName + '\'' + ", propertyValues=" + propertyValues + '}';
	}
}
