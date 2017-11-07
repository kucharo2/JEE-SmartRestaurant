CREATE OR REPLACE VIEW "UnpaidItems" AS
 SELECT food.name, billfood.price, billfood.bill_item_food_id AS billitem_id, true AS is_dish, bill.bill_id
   FROM "Bill" bill
   JOIN "BillItemFood" billfood ON bill.bill_id = billfood.bill_id
   JOIN "Dish" food ON billfood.dish_id = food.dish_id
  WHERE billfood.paid = false
UNION ALL
 SELECT item.name, billdrink.price, billdrink.bill_item_drink_id AS billitem_id, false AS is_dish, bill.bill_id
   FROM "Bill" bill
   JOIN "BillItemDrink" billdrink ON bill.bill_id = billdrink.bill_id
   JOIN "Drink" item ON item.drink_id = billdrink.drink_id
  WHERE billdrink.paid = false;

ALTER TABLE "UnpaidItems"
  OWNER TO student_db13_16;