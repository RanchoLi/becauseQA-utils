package com.github.becauseQA.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Response")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Result implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*@XmlElementWrapper(name="rows")
	@XmlElement(name="row")*/
	private List<ResultRow> table;

	public List<ResultRow> getTable() {
		return table;
	}

	public void setTable(List<ResultRow> table) {
		this.table = table;
	}

}
