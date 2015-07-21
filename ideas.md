* json"..." support
  * JsonParser support StringContext input.
  * for pattern match, JsonParser support result `[JsValue | JsValuePlaceHolder]`
  * JsonParser support object key as plain string key and single-quoted string. like "{ name: 'wangzaixiang' }" for readable
  
* Simple Object-Json Mapping using macro or reflect.
  * compile time mapping via implicitly
  * runtime mapping via ThreadLocal context