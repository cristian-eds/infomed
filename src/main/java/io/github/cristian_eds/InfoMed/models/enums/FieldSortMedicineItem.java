package io.github.cristian_eds.InfoMed.models.enums;

public enum FieldSortMedicineItem {

    NAME("m.name"),
    NUMBER("mi.medicineItemSequence"),
    FREQUENCE("m.frequencyHours"),
    DAY_HOUR("mi.dayHour"),
    CONCLUSION("mi.conclusion"),
    PERSON("m.person.name");

    private final String decription;

    FieldSortMedicineItem(String description) {
        this.decription = description;
    }

    public String getDescription() {
        return this.decription;
    }
}
