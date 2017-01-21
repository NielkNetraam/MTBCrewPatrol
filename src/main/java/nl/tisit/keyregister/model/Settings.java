package nl.tisit.keyregister.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "settings")
public class Settings {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String parameter;
	private ValueType valueType;
	private String stringValue;
	private BigDecimal numericValue;
	
	@Type(type ="org.hibernate.type.LocalDateType")
	private LocalDate dateValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public BigDecimal getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(BigDecimal numericValue) {
		this.numericValue = numericValue;
	}

	public LocalDate getDateValue() {
		return dateValue;
	}

	public void setDateValue(LocalDate dateValue) {
		this.dateValue = dateValue;
	}

	@Override
	public String toString() {
		return "Settings [id=" + id + ", parameter=" + parameter + ", valueType=" + valueType + ", stringValue="
				+ stringValue + ", numericValue=" + numericValue + ", dateValue=" + dateValue + "]";
	}
	
	
}
