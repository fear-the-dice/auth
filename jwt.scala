import authentikat.jwt._

object Jwt {
  def main(args: Array[String]) {
    val uuid = java.util.UUID.randomUUID.toString
    val timestamp: Long = System.currentTimeMillis / 1000
    val header = JwtHeader("HS256")

    val claimsSet = JwtClaimsSet(
      Map(
        "iss" -> "auth.fearthedice.com",
        "aud" -> "*.fearthedice.com",
        "jti" -> uuid,
        "iat" -> timestamp
      )
    )

    val jwt: String = JsonWebToken(header, claimsSet, "secretkey")
    println(jwt)
  }
}
