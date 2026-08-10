# HTTP Client software component

Taking the book
[Domain-Driven Design by Vaughn Vernon](https://vaughnvernon.com/)
as a starting point, then the diagram in the book on page 463, shows a HTTP
client as a facade.

![HTTP client DDD Vaughn Vernon](./HTTP%20client%20diagrams.jpg)

But, looking at the definition of a Facade according to the book of the
[Gang of Four](https://en.wikipedia.org/wiki/Design_Patterns) (GoF):

_"Provide a unified interface to a set of interfaces in a subsystem. Facade
defines a higher-level interface that makes the subsystem easier to use."_

This unified interface, should it not be a reflection in the programming
language of choice, of the Published Language of the Open Host System of the
bounded context that the HttpClient is supposed to target?

To illustrate this:

![Concrete http client full diagram](./HTTP%20client%20diagrams-Page-http-client-facade-concrete.jpg)