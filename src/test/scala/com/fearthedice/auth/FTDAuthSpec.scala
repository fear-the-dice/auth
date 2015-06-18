package com.fearthedice.auth

import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class FTDAuthSpec extends MutableScalatraSpec {
  addServlet(classOf[FTDAuth], "/*")

  "GET / on FTDAuth" should {
    "return status 200" in {
      get("/") {
        status must_== 200
      }
    }
  }
}
