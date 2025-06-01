package com.kr.cground.controller;

import com.kr.cground.dto.ItemRequest;
import com.kr.cground.dto.OrderRequest;
import com.kr.cground.persistence.entity.ItemTb;
import com.kr.cground.persistence.entity.OrderTb;
import com.kr.cground.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/cground/order")
    public ResponseEntity<?> order(
            @RequestBody OrderRequest request
    ) {

        List<ItemTb> itemTbs = request.getItems().stream().map(ItemRequest::mapToEntity).toList();

        Integer amount = itemTbs.stream().map(ItemTb::getAmount).reduce(0, Integer::sum);

        OrderTb orderTb = OrderTb.builder()
                .buyer(request.getBuyer())
                .items(itemTbs)
                .amount(amount)
                .build();

        boolean isSuccess = orderService.addOrder(orderTb);

        return ResponseEntity.ok().build();
    }
}
