package spray.json

import org.specs2.mutable._
import spray.json._

/**
 * Created by wangzx on 15/7/6.
 */
class JsonInterpolationSpec extends Specification {

  "The JsonInterpolation" should {

    "" in {
      json"{name: 'wangzx'}" === JsObject("name" -> JsString("wangzx"))
    }

  }
}
