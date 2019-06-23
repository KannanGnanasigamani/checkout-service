------------------

Checkout Sevice API is designed to calculate the checkout total for the checkout items. It accepts items and promotions(Special Prices) for every checkout transaction and returns the checkout total.

Language : Java 

Technology: Spring Boot

Author: Kannan Gnanasigamani

#API:
-----
http://localhost:9090/checkout/api/calculateTotal

Example Payload:
{
 "items": [
	{
		"itemName": "B",
		"itemPrice": 0.30
	},
	{
		"itemName": "A",
		"itemPrice": 0.50
	},
	{
		"itemName": "B",
		"itemPrice": 0.30
	},
	{
		"itemName": "A",
		"itemPrice": 0.50
	}
 ],
 
 "promotions": [
	{
		"itemName": "A",
		"offerUnit": 3,
		"offerValue": 1.30
	},
	{
		"itemName": "B",
		"offerUnit": 2,
		"offerValue": 0.45
	}
 ]
}


