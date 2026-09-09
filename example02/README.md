# Example 02

This covers the first section of chapter 4 - Use of interfaces without the use of SpringBoot

Reason to cover this separately is often in Applications, you want to architect a project with clear separation of responsibilities, and focus on using interfaces when implementations may vary.

# Packages
- **proxies**: Proxies is used for interfaces / classes that interact with outside the app (E.g. Notification System, Sending Email etc)
- **repositories**: This handles interaction with databases (also called Data Access Objects)
- **services**: Services hold the use cases / business logic of an application
- **model**: Model models the data of the app

In the implementation classes for each interfaces, we just print a text (simulating what would happen in the application). This is to make sure, we operate with minimum structure of the project, rather than add any domain specific knowledge. I want this project to be a template for other such projects. Next project will use spring framework. This project will be a counterpart of various things we will implement using the framework.

If you see the Main class we do all the object initialization there. CommentService class only deals with interfaces as its dependencies. It only knows what it can do (using the method of the interfaces). However, it does not know how it is going to do those work. The implementation classes are sent to it through constructor injection from the main method.

Spring makes it easy by the use of various annotations

If you run the main method, you should see following output

```
Storing comment: Look, I recently bought a new Parrot!
Sending notification for comment: Look, I recently bought a new Parrot!
```