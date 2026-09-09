# Example 04

This covers the first section of chapter 5 - Use of Prototype Scope

Prototype scopes are instantiated per call. i.e each call gets a new instance. Sometimes you want to create a mutable instance for each request and prevent multiple threads modifying the same value. Default scope of Spring is singleton scope. This creates a single object (per name) that is supposed to work as a single object for all threads. As a result, the objects need to be effectively final. This is one reason to use Constructor injection, with fields defined as final (as final allows initialization through constructors).

A utility class modifying the passed object is ok, but if it uses another bean to say persist the data, this class needs to be part of spring as well, but since it modifies the passed object, it needs to be prototype instead. 

Note: One thing to be careful is, you need to call the instance from inside the method (don't define it as field label), because if the calling class is initialized as singleton, its attributes are also injected only once.

```Java
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommentProcessor {
    @Autowired
    private CommentRepository commentRepository;

    private Comment comment;
    // ...
}
```

```Java
@Service
public class CommentService {
    @Autowired
    private ApplicationContext context;

    public void sendComment(Comment c) {
        CommentProcessor p = context.getBean(CommentProcessor.class);
        p.setComment(c);
        p.processComment(c);
        p.validateComment(c);
        c = p.getComment();
        System.out.println("Modified comment: Author: " + c.getAuthor() + ", Text: " + c.getText());
    }
}
```

As you can see, we call the `context.getBean` method from inside the `sendComment` method. This way, each call to the method will get a new instance of `CommentProcessor` as intended.

Use Prototype scope only when needed (e.g. legacy class migration, where you have a lot of mutating classes, but you cannot migrate them all at once)

## Output
```
Storing comment: Look, I recently bought a new Parrot!
Validation changed text to default template text
Modified comment: Author: Badri, Text: Comment is bad, will be removed
Storing comment: Comment is bad, will be removed
Validation changed text to default template text
Modified comment: Author: Badri, Text: Comment is bad, will be removed
```
