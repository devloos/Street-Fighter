package edu.st.common.serialize;

import java.io.IOException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;

public class Serializer<T extends Message> {

    ObjectMapper mapper;
    PolymorphicTypeValidator ptv;

    public Serializer() {
        mapper = new ObjectMapper();
    }

    public String serialize(Packet<T> packet) {
        try {
            return mapper.writeValueAsString(packet);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Packet<T> deserialize(String jsonStr) {
        try {
            JsonNode parent = mapper.readTree(jsonStr);
            String type = parent.get("message").get("type").asText();

            JavaType javaType;
            javaType = mapper.getTypeFactory().constructParametricType(Packet.class, Class.forName(type));

            return mapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
