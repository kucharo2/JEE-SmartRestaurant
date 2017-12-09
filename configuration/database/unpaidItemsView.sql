CREATE OR REPLACE VIEW "UnpaidItems" AS
 SELECT food.name, orderfood.price, orderfood.order_item_food_id AS orderitem_id, true AS is_dish, order.order_id
   FROM "Order" order
   JOIN "OrderItemFood" orderfood ON order.order_id = orderfood.order_id
   JOIN "Dish" food ON orderfood.dish_id = food.dish_id
  WHERE orderfood.paid = false
UNION ALL
 SELECT item.name, orderdrink.price, orderdrink.order_item_drink_id AS orderitem_id, false AS is_dish, order.order_id
   FROM "Order" order
   JOIN "OrderItemDrink" orderdrink ON order.order_id = orderdrink.order_id
   JOIN "Drink" item ON item.drink_id = orderdrink.drink_id
  WHERE orderdrink.paid = false;

ALTER TABLE "UnpaidItems"
  OWNER TO student_db13_16;