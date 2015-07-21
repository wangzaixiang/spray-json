package wangzx

import spray.json._

/**
 * Created by wangzx on 15/7/6.
 */
object Test1 {

  def main(args: Array[String]) {

//    val x = """{"name": "wangzx", "age": 40}"""
//    val input = ParserInput(x)
//    val aa = new JsonParser(input).parseJsValue()
//    println( aa )

    val a =  json"""{"name": "wangzx", "age": 40}"""
    println("a =" + a.prettyPrint )

    val size = augmentString("...").toString()
    val simple: JsValue = "wangzx"
    val age: JsValue = 43
    val books = JsArray(
      JsObject("title"->"Java Book1", "price"->12.34),
      JsObject("title"->"Scala Book", "price"->43.21),
      JsObject("title"->"Polymer Book", "price"->39.99)
    )
    //val book2 = """[{title: "java Book"}]"
    val obj = json"""{"name": $simple, "age": $age, "books": $books }"""

    println( obj.prettyPrint )

    obj match {

      case json"""{"name": $name, "age": 43, "books": [ { "title": $t1 }, {"title": $t2, "price": $p2 }, $book3 ] }""" =>
        println(s"name = $name age = $age t1 = $t1 t2 = $t2  p2 = $p2, book3 = $book3")

    }

  }

}
