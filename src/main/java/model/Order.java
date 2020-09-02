package model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class Order {
    @NonNull
    private Long id;
    private List<Product> products = new ArrayList<>();
    @NonNull
    private Long userId;
}
