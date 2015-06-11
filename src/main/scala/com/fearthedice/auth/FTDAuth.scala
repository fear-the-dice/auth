package com.fearthedice.auth

import org.scalatra._
import scalate.ScalateSupport
import authentikat.jwt._
import com.redis._

class FTDAuth extends FearTheDiceAuthStack {
  val r = new RedisClient("localhost", 6379)
  val header: JwtHeader = JwtHeader("HS256")
  val expiry: Int = 86400
  val sub:String = "8205d3b2-3e73-432b-b7eb-b73f73818d83"

  var jwt: String = _

  before() {
    val uuid: String = java.util.UUID.randomUUID.toString
    val timestamp: Long = System.currentTimeMillis / 1000
    val claimsSet = JwtClaimsSet(
      Map(
        "iss" -> "auth.fearthedice.com",
        "aud" -> "*.fearthedice.com",
        "jti" -> uuid,
        "iat" -> expiry,
        "sub" -> sub
      )
    )

    jwt = JsonWebToken(header, claimsSet, "secretkey") 
    r.setex("ftd/" + sub + "/" + uuid, 86400, 0)

    contentType="text/html"
  }

  get("/") {
    {jwt}
  }

}

