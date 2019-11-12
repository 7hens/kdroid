function _appendJavaScriptElement() {
  var script = document.createElement("script");
  script.text = "console.log(1234567890);";
  script.type = "text/javascript";
  // script.appendChild(document.createTextNode("function functionOne(){alert(\"成功运行\"); }"));
  script.onload = function () { console.log("js loaded"); };
  document.body.appendChild(script);
}