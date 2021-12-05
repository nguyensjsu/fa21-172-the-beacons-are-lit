//package com.example.philzcart;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
//
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.RepresentationModelAssembler;
//import org.springframework.stereotype.Component;
//
//@Component
//class CartModelAssembler implements RepresentationModelAssembler<PhilzCart, EntityModel<PhilzCart>> {
//
//    @Override
//    public EntityModel<PhilzCart> toModel(PhilzCart order) {
//
//        // Unconditional links to single-item resource and aggregate root
//
//        EntityModel<PhilzCart> orderModel = EntityModel.of(order,
//                linkTo(methodOn(PhilzCartController.class).one(order.getUserId())).withSelfRel(),
//                linkTo(methodOn(PhilzCartController.class).all()).withRel("orders"));
//
//        // Conditional links based on state of the order
//
////        if (order.getStatus() == Status.IN_PROGRESS) {
////            orderModel.add(linkTo(methodOn(PhilzCartController.class).cancel(order.getId())).withRel("cancel"));
////            orderModel.add(linkTo(methodOn(PhilzCartController.class).complete(order.getId())).withRel("complete"));
////        }
//
//        return orderModel;
//    }
//}