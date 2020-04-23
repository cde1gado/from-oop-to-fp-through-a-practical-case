package io.github.cde1gado.version1

import io.github.cde1gado.exception.UserNotFound
import io.github.cde1gado.model.{BookCard, User}

import scala.util.{Failure, Success, Try}

class LibraryService(
  userRepository: UserRepository,
  bookRepository: BookRepository
) {

  def getUserBookCard(email: String): Try[BookCard] =
    (for {
      maybeUser <- userRepository.findUserByEmail(email)
      user <- maybeUser.fold[Try[User]](Failure(UserNotFound))(Success.apply)
      books <- bookRepository.findBooksByUser(user.id)
    } yield BookCard(user, books))
      .recoverWith { case error =>
        println(s"Error when getBookCard with email $email")
        Failure(error)
      }

  /* using flatMap:
  def getUserBookCard(email: String): Try[BookCard] =
    userRepository.findUserByEmail(email)
      .flatMap { maybeUser =>
        maybeUser.fold[Try[User]](Failure(UserNotFound))(Success.apply)
          .flatMap { user =>
            bookRepository.findBooksByUser(user.id)
              .map { books =>
                BookCard(user, books)
              }
          }
      }*/
}
