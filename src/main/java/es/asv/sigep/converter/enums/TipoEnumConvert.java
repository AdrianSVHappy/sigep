package es.asv.sigep.converter.enums;

import es.asv.sigep.enums.TipoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TipoEnumConvert implements AttributeConverter<TipoEnum, String> {

	@Override
	public String convertToDatabaseColumn(TipoEnum attribute) {
		return (attribute == null) ? null : attribute.getId();
	}

	@Override
	public TipoEnum convertToEntityAttribute(String dbData) {
		return (dbData == null) ? null : TipoEnum.fromId(dbData);
	}

}
