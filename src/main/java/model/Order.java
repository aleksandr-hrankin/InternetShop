package model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private Long id;
    private List<Product> products;
    private Long userId;

    public Order(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
        products = new ArrayList<>();
    }
}
