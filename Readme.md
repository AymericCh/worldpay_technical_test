# Offer/Product API techical test
This simple api allows to create offer for products known by the system

## Product
A product is defined by :
- an ID
- a name
- a price

The following methods are available :
- Create a new product (POST) : /product
- Get product (GET): /product/{id_product}
- Get all products (GET): /product

## Offer
An offer is defined by :
- an ID
- a price
- a description
- a product id
- a start time with the following pattern "yyyy-MM-dd HH:mm:ss"
- a end time with the following pattern "yyyy-MM-dd HH:mm:ss"
- a currency identified by the ISO 4217 currency codes.

The following methods are available :
- Create a new offer (POST) : /offer
- Get offer (GET): /offer?id={id_offer}
- Get all offers (GET): /offer/all
- Cancel offer (POST) : /offer/cancel?id={id_offer}
