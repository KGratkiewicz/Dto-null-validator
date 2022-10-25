# Dto null validator

Maven dependency
```
    <dependency>
      <groupId>org.reflections</groupId>
      <artifactId>reflections</artifactId>
      <version>0.10.2</version>
    </dependency>
```

```

@AllArgsConstructor
public class DtoValidation {
  private Boolean result;
  private String messege;

  public static DtoValidation isValid(Object dtoObject, List<String> optionalParamsNames) {
    var fields = Arrays.stream(dtoObject.getClass().getDeclaredFields()).filter(x -> !optionalParamsNames.contains(x.getName())).toArray(Field[]::new);
    for (var field : fields) {
      Object fieldValue = getObjectValue(dtoObject, field);
      if (fieldValue == null) {
        return new DtoValidation(false, "Missing field: " + field.getName());
      }
    }
    return new DtoValidation(true, "Object siutable");
  }

  private static Object getObjectValue(Object dtoObject, Field field) {
    try {
      return field.get(dtoObject);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
```
