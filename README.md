# Dynamic Web Framework

## Overview

This project aims to create a dynamic web framework inspired by JAX-RS and Spring. The framework is designed around controllers that handle incoming requests and generate JSON responses. It also supports dependency injection at the controller level.

## Features

- **Route Registration**: Programmers can define available routes by annotating controller methods with `@Path`, requiring a mandatory path parameter. HTTP methods can be specified using `@GET` or `@POST`.
  
- **Controller Annotation**: The `@Controller` annotation marks classes that contain methods to process client requests. Each method corresponds to an action tied to a specific route.

- **Dependency Injection**: Implements Inversion of Control (IoC) by allowing the framework to manage the initialization of controller dependencies. Controllers are instantiated only once, ensuring that the same instance is reused for each request.

- **Dynamic Programming**: Utilizes reflection to analyze all project classes at runtime. The framework discovers classes annotated with `@Controller` and maps routes to their corresponding methods based on the defined annotations.

## Annotations

- **@Controller**: Marks a class as a controller.
  
- **@GET/@POST**: Indicates the HTTP methods that can trigger the annotated method.

- **@Path**: Specifies the route that triggers the controller method.

- **@Autowired**: Marks class attributes for dependency injection during controller initialization.

- **@Bean**: Used to mark classes with specific instantiation behavior (singleton or prototype).

- **@Service** and **@Component**: Serve as alternatives to `@Bean`, with predefined scope behaviors.

- **@Qualifier**: Resolves which specific bean should be injected when multiple implementations exist.

## Dependency Container

The Dependency Container allows the registration of concrete implementations for interfaces, throwing exceptions if required types are not provided.

## DI Engine

The DI Engine is responsible for recursively initializing all annotated dependencies, managing instance creation for `@Bean` and `@Service` instances, and consulting the Dependency Container when dealing with interfaces.

## Exceptions

The framework throws exceptions for several scenarios:

1. Multiple `@Bean` instances with the same `@Qualifier` value.
2. An attribute of type Interface annotated with `@Autowired` without a corresponding `@Qualifier`.
3. `@Autowired` on an attribute that is not annotated with `@Bean`, `@Service`, or `@Component`.

## Usage

To get started with the dynamic web framework, follow these steps:

1. Annotate your controller classes with `@Controller`.
2. Define your routes using `@Path` and specify HTTP methods with `@GET` or `@POST`.
3. Use `@Autowired` for dependency injection within your controllers.
4. Implement your business logic within the annotated methods.

## Conclusion

This dynamic web framework leverages the power of reflection and dynamic programming to provide a flexible and robust solution for building web applications. With built-in support for dependency injection and route management, it streamlines the development process while adhering to best practices.
