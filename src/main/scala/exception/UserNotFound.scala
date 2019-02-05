package exception

case object UserNotFound extends RuntimeException(s"User not found")
