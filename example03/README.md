# Example 03

This covers the first section of chapter 4 - Use of interfaces using SpringBoot

Compare this with Example 02 project (that one does not use Spring packages)

# Packages

- **proxies**: Proxies is used for interfaces / classes that interact with outside the app (E.g. Notification System, Sending Email etc)
- **repositories**: This handles interaction with databases (also called Data Access Objects)
- **services**: Services hold the use cases / business logic of an application
- **model**: Model models the data of the app

In the implementation classes for each interfaces, we just print a text (simulating what would happen in the application). This is to make sure, we operate with minimum structure of the project, rather than add any domain specific knowledge. I want this project to be a template for other such projects. 

Classes in repository and services packages use @Repository and @Service annotation (which give more context than generic @Component annotation, although all of them instruct spring to create beans of the associated classes). 

I also add a conflict. Since we are resolving classes using their interfaces, I created two classes that implement CommentNotificationproxy interface. I added CommentPushNotificationproxy classes after running the project first. After adding this interface, the code failed, because it could not resolve a definitive single class, instead spring was asked to choose between two equally qualifiying classes.

To resolve this issue, either we can use `@Primary` annotation as discussed before, or use `@Qualifier` to first decorate the classes and then specify which class to use in the service class `CommentService` by specifying that specific Qualifier.

```Java
@Component
@Qualifier("PUSH")
public class CommentPushNotificationProxy implements CommentNotificationProxy {
    // ...
}
```

```Java
@Component
@Qualifier("EMAIL")
public class EmailCommentNotificationProxy implements CommentNotificationProxy{
    // ...
}
```

```Java
 public CommentService(CommentRepository commentRepository,
                      @Qualifier("EMAIL") CommentNotificationProxy commentNotificationProxy) {
    this.commentRepository = commentRepository;
    this.commentNotificationProxy = commentNotificationProxy;
}
```
