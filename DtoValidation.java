package example.pack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class DtoValidation {
  private Boolean result;
  private List<String> missingAttr;

  public static DtoValidation isValid(Object dtoObject, List<String> optionalParams) {
    var missing = Arrays.stream(dtoObject.getClass().getDeclaredFields())
      .filter(x -> !optionalParams.contains(x.getName()))
      .filter(x -> getObjectValue(dtoObject, x) == null)
      .map(Field::getName)
      .collect(Collectors.toList());

    return new DtoValidation(missing.size() == 0, missing);
  }

  private static Object getObjectValue(Object dtoObject, Field field) {
    try {
      return field.get(dtoObject);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public String getMessage() {
    return "Brakujące attrybuty:" + String.join("," , this.getMissingAttr());
  }
}

}
