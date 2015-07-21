package spray.json

import scala.collection.SortedMap

/**
 * Created by wangzx on 15/7/6.
 */

class JsonInterpolation(sc: StringContext) {

  object json {

    def apply(args: JsValue*): JsValue = new JsonParser(ParserInput(sc, args)).parseJsValue()

    def unapplySeq(input: JsValue): Option[Seq[JsValue]] = {

      val placeHolders = Seq.range(0, sc.parts.length-1).map(x => JsNumber(Integer.MAX_VALUE - x) )

      val pi = ParserInput(sc, placeHolders)
      val pattern = new JsonParser(pi).parseJsValue()

      val results = collection.mutable.ArrayBuffer[JsValue]()
      Seq.range(0, sc.parts.length-1).foreach { x => results += null }

      patternMatch(pattern, input, placeHolders, results)

      Some(results.toSeq)
    }

    // array match
    private def patternMatch(pattern: JsValue, input: JsValue, placeHolders: Seq[JsValue], results: collection.mutable.ArrayBuffer[JsValue]): Unit = {

      def isPlaceHolder(value: JsNumber) = {
        val num = value.value.toInt
        val index = Integer.MAX_VALUE - num.toInt
        num > 0 && index < placeHolders.size && placeHolders(index).eq(value)
      }

      pattern match {
        case x: JsObject =>
          x.fields.foreach {
            case (key, n @ JsNumber(num)) if isPlaceHolder(n) =>
              val index = Integer.MAX_VALUE - num.toInt
              assert(input.asJsObject.fields contains key)
              results(index) = input.asJsObject.fields(key)

            case (key, value) =>
              assert(input.asJsObject.fields contains key)
              patternMatch(value, input.asJsObject.fields(key), placeHolders, results)
          }
        case x: JsArray =>
          assert(input.isInstanceOf[JsArray])
          assert(input.asInstanceOf[JsArray].elements.size >= x.elements.size)
          x.elements.zipWithIndex.foreach {
            case (x: JsNumber, y: Int) if isPlaceHolder(x) =>
              val index = Integer.MAX_VALUE - x.value.toInt
              results(index) = input.asInstanceOf[JsArray].elements(y)
            case (x: JsValue,y: Int)=>
              patternMatch(x, input.asInstanceOf[JsArray].elements.apply(y), placeHolders, results)
          }
        case x: JsString =>
          assert(x == input)
        case x: JsBoolean =>
          assert(x == input)
        case x: JsNumber =>
          assert(x == input)
        case x@ JsNull =>
          assert(x == input)
      }
    }

  }

}

