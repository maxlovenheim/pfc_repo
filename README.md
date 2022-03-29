# pfc_repo
Case for PFC


Microservice that validates IBAN number.

Endpoints

(POST)
api/v1/iban/validate

Expected request body (json): {"iban": "<IBAN NUMBER>"}
  
Response: HTTP_200_OK, Body = true if IBAN is valid and false otherwise

If request body is of wrong format for example {"iBaN": "<IBAN NUMBER>"} response will be HTTP_400_BAD_REQUEST
