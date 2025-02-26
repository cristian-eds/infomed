package io.github.cristian_eds.InfoMed.models.converters;

import io.github.cristian_eds.InfoMed.models.enums.ActionType;
import jakarta.persistence.AttributeConverter;

public class ActionTypeConverter implements AttributeConverter<ActionType,String> {

    @Override
    public String convertToDatabaseColumn(ActionType attribute) {
        if (attribute == null) return "";
        return attribute.name().toLowerCase();
    }

    @Override
    public ActionType convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return null;
        return ActionType.valueOf(dbData);
    }
}
