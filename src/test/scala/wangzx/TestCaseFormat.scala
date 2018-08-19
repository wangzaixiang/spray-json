package wangzx

import spray.json._
import DefaultJsonProtocol._

object TestCaseFormat {

  case class Language(name: String, comments: String)

  case class User(name: String, age: Int, email: String, scores: List[Int], languages: List[Language], big: BigCase)

  case class BigCase
  (
    f1: String,
    f2: Option[Int],
    f3: List[String],
    f4: String,
    f5: String,
    f6: String,
    f7: String,
    f8: String,
    f9: String,
    f10: String,
    f11: String,
    f12: String,
    f13: String,
    f14: String,
    f15: String,
    f16: String,
    f17: String,
    f18: String,
    f19: String,
    f20: String,
    f21: String,
    f22: String,
    f23: String,
    f24: String,
    f25: String,
    f26: String,
    f27: String
  )

  def main(args: Array[String]): Unit = {

    val user = User("wangzx", 40, "wangzx@gmail.com", List(90,80,100),
      List(Language("java", "i love java"), Language("scala", "Code In Scala")),
      BigCase("1", None, List("a", "b"), "4", "5", "6", "7", "8", "9",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
        "20", "21", "22", "23", "24", "25", "26", "27")
    )

    println( user.toJson )

////    val languageFormat = JsonFormat.material[Language]
//    val userFormat = implicitly[JsonFormat[User]]
//
//    val json = userFormat.write(user)
//
//    println(json)
//
//    val user2 = userFormat.read(json)
//    println(user2)
//
//    val sb = new java.lang.StringBuilder
//    PrettyPrinter.print(json, sb)
//
//    println(sb.toString())


  }

}
