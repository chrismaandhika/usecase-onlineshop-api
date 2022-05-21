Application flow diagram can be found on:
src/main/resources/simple_online_shop_ordering_flow.png

Database init script can be found in:
src/main/resources/db_init.sql

Assumptions:

-member id should be in access token

-online system untuk suatu online shop, bukan multi merchants

-login menggunakan oauth2 hanya untuk password grant type dan client credentials grant type

-table untuk product catalog berbeda dengan table master

-product catalog default size adalah 30

-product catalog default sorting adalah ascending by product name

-file thumbnail and banner disimpan di harddisk

-reserve stock terjadi saat product masuk ke chart

-stock asli berkurang saat payment sukses

-available stock = original stock - reserved stock

-product detail berisi product name, description, price, dan available stock

-satu user hanya bisa memiliki satu shipment address

-payment hanya menggunakan virtual account

-payment yang sedang berlangsung tidak bisa ditiban

-shipment price sifatnya fix, tidak terpengaruh jarak maupun bobot


out of scope:

-access token management

-client access management

-search product by other than product name

-product catalog sorting

-product category

-product review

-time limited reserved stock

-delivery process

-warehouse management

-product catalog management

-discount addition

-promo code & voucher addition

-member registration

-loyalty program

-flash sale

-marketing campaign

-auditing

-other online shopping features that are not listed above

nice to have:

-notification when order placed

-notification when payment success/failed

-time limited payment process

-time limited reserved stock