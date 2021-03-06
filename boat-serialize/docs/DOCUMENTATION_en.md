# <span class="image">![Boat Serialize](../../logo.svg)</span> `boat-serialize`: Boat Serialize — Serialize and Deserialize Lib of [Boat](../../README.md)

<span id="author" class="author">Sun Qian</span>  
<span id="email" class="email"><fredsuvn@163.com></span>  

Table of Contents

-   [Introduction](#_introduction)
-   [Usage](#_usage)
    -   [JSON](#_json)
-   [Samples](#_samples)

## Introduction

Boat Serialize provides Unified interfaces (`Serializer`, `Serial`) to
support serialize and deserialize operations.

Now format it supports are:

-   [JSON](#_json)

## Usage

### JSON

Json package provides JSON format implementation of `Serial`: `Json`.

To get a `Json`, we can use core utilities of boat-serialize:
`JsonSerials`.

Java Examples

    class Example{
        @Test
        public void test() {
            Json json = JsonSerials.toJson("{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}");
            Map<String, String> map = json.toObject(new TypeRef<Map<String, String>>() {
            });
        }
    }

Kotlin Examples

    class Example {
        @Test
        fun test() {
            val json = "{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}".toJson()
            val map: Map<String, String> = json.toObject(object : TypeRef<Map<String, String>>() {})
        }
    }

`JsonSerials` uses default `JsonSerializer` — the core interface for
`Serializer` with `JSON`, to implement its methods. We can also use
`JsonSerializer` directly:

Java Examples

    class Example{
        @Test
        public void test() {
            JsonSerializer serializer = JsonSerializer.DEFAULT;
            String mapJson = "{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}";
            Map<String, String> map = serializer.toJson(mapJson).toObject(new TypeRef<Map<String, String>>() {
            });
        }
    }

Kotlin Examples

    class Example {
        @Test
        fun test() {
            val serializer = JsonSerializer.DEFAULT
            val mapJson = "{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}"
            val map: Map<String, String> =
                serializer.toJson(mapJson).toObject(object : TypeRef<Map<String, String>>() {})
        }
    }

Default `JsonSerializer` implementation is come from
`Jacksons.DEFAULT_OBJECT_MAPPER` which is an `ObjectMapper` of
`jackson`. We can also create a custom `JsonSerializer` with another
`oObjectMapper`:

Java Examples

    class Example{
        @Test
        public void test() {
            JsonSerializer serializer = Jacksons.newJsonSerializer(Jacksons.DEFAULT_OBJECT_MAPPER);
        }
    }

Kotlin Examples

    class Example {
        @Test
        fun test() {
            val serializer: JsonSerializer = DEFAULT_OBJECT_MAPPER.toJsonSerializer()
        }
    }

## Samples

Java Examples

    package sample.java.xyz.srclab.serialize;

    import org.testng.annotations.Test;
    import xyz.srclab.common.reflect.TypeRef;
    import xyz.srclab.common.serialize.json.Jacksons;
    import xyz.srclab.common.serialize.json.Json;
    import xyz.srclab.common.serialize.json.JsonSerializer;
    import xyz.srclab.common.serialize.json.JsonSerials;
    import xyz.srclab.common.test.TestLogger;

    import java.util.Map;

    public class SerializeSample {

        private static final TestLogger logger = TestLogger.DEFAULT;

        @Test
        public void testJsonSerials() {
            Json json = JsonSerials.toJson("{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}");
            Map<String, String> map = json.toObject(new TypeRef<Map<String, String>>() {
            });
            //{p1=p1 value, p2=p2 value}
            logger.log(map);

            String stringJson = "abc";
            //"abc"
            logger.log("stringJson: {}", JsonSerials.stringify(stringJson));
        }

        @Test
        public void testJsonSerializer() {
            JsonSerializer serializer = JsonSerializer.DEFAULT;
            String mapJson = "{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}";
            Map<String, String> map = serializer.toJson(mapJson).toObject(new TypeRef<Map<String, String>>() {
            });
            //{p1=p1 value, p2=p2 value}
            logger.log(map);

            String stringJson = "abc";
            //"abc"
            logger.log("stringJson: {}", serializer.serialize(stringJson));
        }

        @Test
        public void testJackson() {
            JsonSerializer serializer = Jacksons.newJsonSerializer(Jacksons.DEFAULT_OBJECT_MAPPER);
            String mapJson = "{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}";
            Map<String, String> map = serializer.toJson(mapJson).toObject(new TypeRef<Map<String, String>>() {
            });
            //{p1=p1 value, p2=p2 value}
            logger.log(map);

            String stringJson = "abc";
            //"abc"
            logger.log("stringJson: {}", serializer.serialize(stringJson));
        }
    }

Kotlin Examples

    package sample.kotlin.xyz.srclab.serialize

    import org.testng.annotations.Test
    import xyz.srclab.common.reflect.TypeRef
    import xyz.srclab.common.serialize.json.*
    import xyz.srclab.common.test.TestLogger

    class SerializeSample {

        private val logger = TestLogger.DEFAULT

        @Test
        fun testJsonSerials() {
            val json = "{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}".toJson()
            val map: Map<String, String> = json.toObject(object : TypeRef<Map<String, String>>() {})
            //{p1=p1 value, p2=p2 value}
            logger.log(map)
            val stringJson = "abc"
            //"abc"
            logger.log("stringJson: {}", stringJson.stringify())
        }

        @Test
        fun testJsonSerializer() {
            val serializer = JsonSerializer.DEFAULT
            val mapJson = "{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}"
            val map: Map<String, String> =
                serializer.toJson(mapJson).toObject(object : TypeRef<Map<String, String>>() {})
            //{p1=p1 value, p2=p2 value}
            logger.log(map)
            val stringJson = "abc"
            //"abc"
            logger.log("stringJson: {}", serializer.serialize(stringJson))
        }

        @Test
        fun testJackson() {
            val serializer: JsonSerializer = DEFAULT_OBJECT_MAPPER.toJsonSerializer()
            val mapJson = "{\"p1\":\"p1 value\",\"p2\":\"p2 value\"}"
            val map: Map<String, String> =
                serializer.toJson(mapJson).toObject(object : TypeRef<Map<String, String>>() {})
            //{p1=p1 value, p2=p2 value}
            logger.log(map)
            val stringJson = "abc"
            //"abc"
            logger.log("stringJson: {}", serializer.serialize(stringJson))
        }
    }
