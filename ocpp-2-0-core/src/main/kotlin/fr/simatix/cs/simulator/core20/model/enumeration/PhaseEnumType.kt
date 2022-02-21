package fr.simatix.cs.simulator.core20.model.enumeration

import com.fasterxml.jackson.annotation.JsonValue

enum class PhaseEnumType(val value: String) {
    L1("L1"),

    L2("L2"),

    L3("L3"),

    N("N"),

    L1N("L1-N"),

    L2N("L2-N"),

    L3N("L3-N"),

    L1L2("L1-L2"),

    L2L3("L2-L3"),

    L3L1("L3-L1");

    @JsonValue
    fun getEnumValue(): String{
        return value
    }
}
