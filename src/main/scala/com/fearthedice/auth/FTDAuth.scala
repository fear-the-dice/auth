package com.fearthedice.auth

import org.scalatra._
import scalate.ScalateSupport
import authentikat.jwt._

object Jwt {
  val header: JwtHeader = JwtHeader("HS256")
  val uuid: String = java.util.UUID.randomUUID.toString
  val timestamp: Long = System.currentTimeMillis / 1000

  def getJwt(): String = {
    val claimsSet = JwtClaimsSet(
      Map(
        "iss" -> "auth.fearthedice.com",
        "aud" -> "*.fearthedice.com",
        "jti" -> uuid,
        "iat" -> timestamp
      )
    )

    return JsonWebToken(header, claimsSet, "secretkey")
  }
}

class FTDAuth extends FearTheDiceAuthServerStack {
  var jwt: String = _

  before() {
    jwt = Jwt.getJwt
    contentType="text/html"
  }

  get("/") {
    {jwt}
  }

}
