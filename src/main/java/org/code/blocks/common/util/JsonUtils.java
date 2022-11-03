package org.code.blocks.common.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.code.blocks.common.exception.ComponentException;

/**
 * data type cast by jackson method.
 *
 * @author darwindu
 */
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final ObjectWriter OBJECT_WRITER;
    private static final ObjectReader OBJECT_READER;

    static {
        // sort by letter
        OBJECT_MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        // when map is serialization, sort by key
        OBJECT_MAPPER.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        // Notice how the doors field contains the value null.
        // A primitive type in Java cannot have the value null.
        // Therefore the Jackson ObjectMapper by default ignores a null value for a primitive field.
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        // Ignore Unknown JSON Fields
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        OBJECT_MAPPER.setVisibility(PropertyAccessor.SETTER, Visibility.NONE);
        OBJECT_MAPPER.setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
        OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        OBJECT_WRITER = OBJECT_MAPPER.writer().withDefaultPrettyPrinter();
        OBJECT_READER = OBJECT_MAPPER.reader();
    }

    /**
     * Json String to Object.
     *
     * @param jsonStr Json String
     * @param clazz Class
     * @return Object
     */
    public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {

        try {
            return OBJECT_READER.readValue(
                OBJECT_MAPPER.getFactory().createParser(jsonStr),
                clazz);
        } catch (IOException e) {
            throw new ComponentException(e);
        }
    }

    /**
     * Json String to Object.
     *
     * method:JsonUtils.jsonToObj(wesignResponse.getResult(), new TypeReference<CreateContractReponse<CreateContractResponseData>>(){}); .
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T jsonToObj(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_READER.readValue(OBJECT_MAPPER.getFactory().createParser(jsonStr), valueTypeRef);
        } catch (IOException e) {
            throw new ComponentException(e);
        }
    }

    /**
     * Object to Json String.
     *
     * @param obj Object
     * @return String
     */
    public static String objToJson(Object obj) {

        try {
            return OBJECT_WRITER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ComponentException(e);
        }
    }
}
