package version0

import exception.UserNotFound
import model.{BookCard, User}

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
