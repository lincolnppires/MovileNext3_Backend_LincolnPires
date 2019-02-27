# Movile Next

Projeto para conclusão do curso Movile Next - Backend.

## Contexto

Necessidade de consultar dados dos clientes do iFood e dos seus pedidos, assim como o de criar e gerenciar o fluxo de um novo pedido. 

## Aplicação Web

A aplicação consiste em uma API RESTfull, com o uso de HATEOAS, 
e a implementação de uma máquina de estados finitos para controle do fluxo de um pedido.

## Tecnologias utilizadas

* Spring Boot
* Spring HATEOAS
* Spring Statemachine
* Spring Model-View-Controller
* Project Lombok
* Apache Maven
* JUnit
* Rest-assure
* Java 8
* Git / GitHub
* H2

## REST Endpoints

	GET: localhost:8080/movile/customers

Tem-se 10 registro de clientes no banco H2, que é inicializado com a aplicação.
 
Para exemplificar a paginação dos dados e os links de navegação, definiu-se o retorno de 3 itens por página. 
Desta forma é possível avaliar a navegação para a próxima, anterior, última e primeira página.

Observa-se também o link de navegação self, que faz referência a própria página e o link orders, que 
permite a navegação para os pedidos de determinado cliente. 

	GET: localhost:8080/movile/customer/{customer_id}

Permite consultar os dados de pedidos de um determinado cliente. 

Apresenta o link de navegação que representa o próprio recurso e o link customers, que permite 
navegar para todos os clientes.

	POST: localhost:8080/movile/customer/{customer_id}/order

 Permite a adição de um novo pedido para um determinado cliente. 
 
 Este processo dispara um evento através da máquina de estados para gerenciar o fluxo do pedido e executar ações assíncronas.















   



