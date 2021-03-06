# ![](logo.svg) Boat: SrcLab Core Libraries for Java/kotlin

## Documentation:

- AsciiDoc:
  * [English](docs/DOCUMENTATION_en.adoc)
  * [简体中文](docs/DOCUMENTATION_zh.adoc)
- Markdown:
  * [English](docs/DOCUMENTATION_en.md)
  * [简体中文](docs/DOCUMENTATION_zh.md)
- HTML:
  * [English](docs/DOCUMENTATION_en.html)
  * [简体中文](docs/DOCUMENTATION_zh.html)

More see [docs/](docs/)

## Build

This project depends on [srclab-dependencies](https://github.com/srclab-projects/srclab-dependencies), you may:

```shell
# install srclab-dependencies
git clone -b master https://github.com/srclab-projects/srclab-dependencies.git

cd srclab-dependencies && mvn clean install

# build boat
git clone -b master https://github.com/srclab-projects/boat.git

cd boat && gradle clean build
```

**Note:**

* Some properties should be configured if you want to enable publish to remote, see publish info part
  of [build.gradle](build.gradle)
* `boat-protobuf` need `protoc` to compile protobuf files, some architectures don't support it (such as `ARM`);

## Join

* fredsuvn@163.com
* https://github.com/srclab-projects/boat
* QQ group: 566185308

## License

[Apache 2.0 license][license]

[license]: https://www.apache.org/licenses/LICENSE-2.0.html

###### _There seems to be something below?_

↓↓↓↓↓↓↓↓

<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

# boat-egg: _*Wow, you found me!*_

## Java

```java
public class EggSample {

  @Test
  public void testEgg() {
    EggManager eggManager = BoatEggManager.INSTANCE;
    Egg egg = eggManager.pick("O Battle");
    egg.hatchOut("Thank you, Taro.");
    //Or
    //egg.hatchOut("谢谢你，泰罗。");
  }
}
```

## Kotlin

```kotlin
class EggSample {

  @Test
  fun testEgg() {
    val egg = BoatEggManager.pick("O Battle")
    egg.hatchOut("Thank you, Taro.")
    //Or
    //egg.hatchOut("谢谢你，泰罗。")
  }
}
```