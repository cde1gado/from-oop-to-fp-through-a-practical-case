package io.github.cde1gado.version0

import io.github.cde1gado.exception.UserNotFound
import io.github.cde1gado.model.{BookCard, User}

class LibraryService(
  userRepository: UserRepository,
  bookRepository: BookRepository
) {

  def getUserBookCard(email: String): BookCard = {
    try {
      val maybeUser = userRepository.findUserByEmail(email)
      val user = maybeUser.fold[User](throw UserNotFound)(u => u)
      val books = bookRepository.findBooksByUser(user.id)
      BookCard(user, books)
    } catch {
      case error: Exception =>
        println(s"Error when getBookCard with email $email")
        throw error
    }
  }
}
