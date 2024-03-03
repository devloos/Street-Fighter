package edu.st.common.serialize;

// singleton implementation of the Serializer
public class SerializerFactory {
    private static Serializer serializer = null;

    public static Serializer getSerializer() {
        if (serializer == null) {
            serializer = new Serializer();
        }
        return serializer;
    }
}
