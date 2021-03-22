package attributeConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import training.enumerations.ClientPackageStateEnum;

@Converter(autoApply = true)
public class attributeConverterClientPackageStateEnum implements AttributeConverter<ClientPackageStateEnum,String> {

	@Override
	public String convertToDatabaseColumn(ClientPackageStateEnum attribute) {
		return attribute.getShortName();
	}

	@Override
	public ClientPackageStateEnum convertToEntityAttribute(String dbData) {
		return ClientPackageStateEnum.fromShortName(dbData);
	}

}
