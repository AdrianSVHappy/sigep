package es.asv.sigep.converter.enums;

import es.asv.sigep.enums.RolEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RolEnumConvert implements AttributeConverter<RolEnum, String>{

	@Override
	public String convertToDatabaseColumn(RolEnum attribute) {
		return (attribute == null) ? null : attribute.getId();
	}

	@Override
	public RolEnum convertToEntityAttribute(String dbData) {
		return (dbData == null) ? null : RolEnum.fromId(dbData);
	}
}
