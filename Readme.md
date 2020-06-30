# Account Application Description

Account Application (AA) is a simple application that allows to process transactions for only one account.
It allows to create new or replace existing account with specified value.
There is a possibility to create debit and credit transactions for existing account.

# Project structure
The application is monolithic maven project and consists of two modules: User Interface (UI) and Web API ones.

To build ReactJS UI part maven frontend plugin with webpack is used.

Web API module uses spring boot MVC with Tomcat-embedded and has dependency on UI module.

## Web API Java module
This module uses two entities: Transaction and Account ones.
To store application data Map is used.
To control conurrent access ReadWriteLock is leveraged.

## AA API

The application has context path **bank**.

All API endpoints has prefix **api**

### Endpoints to control account.

```
POST /account?value= - create new account with specified value
GET /account/{accountId} - get account with specified account id
GET /account - get account list 
```

### Transaction endpoints
```
POST account/{accountId}/transaction/credit - create credit transaction for specified account id
POST account/{accountId}/transaction/debit - create debit transaction for specified account id
GET account/{accountId}/transaction - get all transactions
```
