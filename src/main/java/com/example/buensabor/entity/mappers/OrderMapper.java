package com.example.buensabor.entity.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Order;
import com.example.buensabor.entity.OrderProduct;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.dto.OrderDTO;
import com.example.buensabor.entity.dto.OrderProductDTO;
import com.example.buensabor.entity.dto.ClientDTOs.ClientBasicDTO;
import com.example.buensabor.entity.dto.OrderDTOs.OrderProductBasicDTO;
import com.example.buensabor.entity.dto.OrderDTOs.OrderResponseDTO;
import com.example.buensabor.entity.dto.ProductDtos.ProductBasicDTO;
@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, OrderDTO> {

    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "client", source = "client")
    @Mapping(target = "orderProducts", source = "orderProducts")
    OrderResponseDTO toSummaryDTO(Order order);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ClientBasicDTO toBasicDTO(Client client);

    @Mapping(target = "product", source = "product")
    OrderProductDTO toProductDTO(OrderProduct orderProduct);

    @Mapping(target = "companyId", source = "company.id")
    ProductBasicDTO toBasicDTO(Product product);

    // 👇 ESTE FALTABA
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "title", source = "product.title") // <-- agrega el mapeo del título
    OrderProductBasicDTO toBasicOrderProductDTO(OrderProduct orderProduct);

    List<OrderResponseDTO> toSummaryDTOList(List<Order> orders);
    List<OrderProductDTO> toProductDTOList(List<OrderProduct> products);
    List<OrderProductBasicDTO> toBasicOrderProductDTOList(List<OrderProduct> products);
}


