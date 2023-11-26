package booklet.menuhere.domain.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    SIGNATURE("SIGNATURE"), COFFEE("COFFEE"), BEVERAGE("BEVERAGE"), TEA("TEA"), DESSERT("DESSERT");

    private final String description;
}
