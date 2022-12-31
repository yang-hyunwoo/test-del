package test.domain;

import lombok.Getter;

public enum ExtendTypeEnum {
    N("NECESSARY"),
    C("CHOICE");

    @Getter
    private final String description;

    ExtendTypeEnum(String description) {
        this.description = description;
    }
}
