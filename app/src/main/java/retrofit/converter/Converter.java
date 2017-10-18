package retrofit.converter;

import java.lang.reflect.Type;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public interface Converter {
    Object fromBody(TypedInput typedInput, Type type);

    TypedOutput toBody(Object obj);
}
