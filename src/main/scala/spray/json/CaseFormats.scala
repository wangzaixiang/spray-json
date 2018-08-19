package spray.json

import scala.language.experimental.macros

//object Test {
//
//  case class User(name: String, age: Int, scores: List[Int])
//
//  object UserFormat extends CaseFormat[User] {
//
//    import DefaultJsonProtocol._
//
//    val fName = Field[String]("name")
//    val fAge  = Field[Int]("age")
//    val fScores = Field[List[Int]]("scores")
//
//
//    override def read(json: JsValue): User = User(
//      fName.read(json), fAge.read(json), fScores.read(json)
//    )
//
//    override def write(obj: User): JsValue =
//      JsObject( fName.write(obj.name), fAge.write(obj.age), fScores.write(obj.scores) )
//  }
//
//
//}

trait CaseClassFormat[CASE] extends JsonFormat[CASE] {

  import DefaultJsonProtocol._

  case class Field[T](name: String, default: Option[T] = None)(implicit fmt: JsonFormat[T]) {
    def read(json: JsValue): T = json match {
      case x: JsObject if( x.fields.contains(name) == false && fmt.isInstanceOf[OptionFormat[_]] ) =>
        None.asInstanceOf[T]
      case x: JsObject  =>
        if(x.fields.contains(name))
          fmt.read( x.fields(name) )
        else if(default.isDefined) default.get
        else deserializationError(s"Object is missing required member $name", null, name :: Nil)
      case _ =>
        deserializationError(s"Object expected in field $name", null, name :: Nil)
    }

    def write(value: T): (String, JsValue) =
      if(value == null) (name, JsNull)
      else (name, implicitly[JsonFormat[T]].write(value))
  }

}

object CaseClassFormat {

  /**
    * provide a macro expand for CaseClassFormat[T].
    */
  def material_format[T: c.WeakTypeTag](c: scala.reflect.macros.whitebox.Context): c.Tree = {

    import c.universe._

    val tag = implicitly[c.WeakTypeTag[T]]
    assert( tag.tpe.typeSymbol.asClass.isCaseClass, s"support Case class only, but ${tag.tpe.typeSymbol.fullName} is not")

    val companion: Symbol = tag.tpe.typeSymbol.asClass.companion

    val constuctor: MethodSymbol = tag.tpe.typeSymbol.asClass.primaryConstructor.asMethod

    var index = 0
    val codes: List[(Tree, Tree, Tree)] = constuctor.paramLists(0).map { p: Symbol =>
      val term = p.asTerm
      index += 1

      val name: String = term.name.toString
      val newTerm = TermName(name)

      val qDef = if(term.isParamWithDefault) {
        val defMethod: MethodSymbol = companion.asModule.typeSignature.member(TermName("$lessinit$greater$default$" + index)).asMethod
        q"val $newTerm = Field[${term.typeSignature}]($name, Some($companion.$defMethod) )"
      } else q"""val $newTerm = Field[${term.typeSignature}]($name)"""

      val qRead = q"$newTerm.read(json)"
      val qWrite = q"$newTerm.write(obj.$newTerm)"

      (qDef, qRead, qWrite)
    }

    val defs = codes.map(_._1)
    val reads = codes.map(_._2)
    val writes = codes.map(_._3)

    val tree =
      q"""
         import spray.json._
         new CaseClassFormat[$tag] {
            ..${defs}
            override def read(json: JsValue): $tag =  new $tag( ..$reads )
            override def write(obj: $tag): JsValue = JsObject( ..$writes )

         }
       """

    tree
  }

}

trait CaseFormats {

  implicit def material[T] : JsonFormat[T] = macro CaseClassFormat.material_format[T]

}
