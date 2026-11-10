import java.time.LocalDate

data class Book(val id: String, val title: String, val author: String, val year: Int, var available: Int)
data class Loan(val bookId: String, val studentId: String, val date: LocalDate = LocalDate.now(), var returned: Boolean = false)

val books = linkedMapOf<String, Book>()
val loans = mutableListOf<Loan>()

fun ask(prompt: String): String { print(prompt); return readlnOrNull()?.trim().orEmpty() }

fun addBook() {
    val id = ask("Book ID: ")
    if (id.isBlank() || books.keys.any { it.equals(id, ignoreCase = true) }) { println("Invalid or duplicate book ID."); return }
    val title = ask("Title: "); val author = ask("Author: ")
    val year = ask("Publication year: ").toIntOrNull()
    val copies = ask("Available copies: ").toIntOrNull()
    if (title.isBlank() || author.isBlank() || year == null || year !in 1450..(LocalDate.now().year + 1) || copies == null || copies < 0) {
        println("Invalid book details."); return
    }
    books[id] = Book(id, title, author, year, copies)
    println("Book added successfully.")
}
fun printBooks(items: Collection<Book>) {
    if (items.isEmpty()) { println("No books found."); return }
    println("%-12s %-32s %-24s %-8s %-10s".format("ID", "TITLE", "AUTHOR", "YEAR", "AVAILABLE"))
    items.forEach { println("%-12s %-32s %-24s %-8d %-10d".format(it.id.take(12), it.title.take(32), it.author.take(24), it.year, it.available)) }
}
fun searchBook() {
    val query = ask("Enter book ID or title: ")
    val found = books.values.filter { it.id.equals(query, true) || it.title.contains(query, true) }
    printBooks(found)
    found.forEach { println("${it.id}: ${if (it.available > 0) "Available" else "Unavailable"}") }
}
fun issueBook() {
    val id = ask("Book ID: "); val student = ask("Student ID: ")
    val book = books.values.find { it.id.equals(id, true) }
    if (book == null || student.isBlank() || book.available < 1) { println("Book unavailable or invalid details."); return }
    if (loans.any { !it.returned && it.bookId == book.id && it.studentId.equals(student, true) }) { println("Student already has this book issued."); return }
    book.available--
    loans.add(Loan(book.id, student))
    println("Book issued successfully.")
}
fun returnBook() {
    val id = ask("Book ID: "); val student = ask("Student ID: ")
    val loan = loans.find { !it.returned && it.bookId.equals(id, true) && it.studentId.equals(student, true) }
    if (loan == null) { println("No matching outstanding issue record."); return }
    loan.returned = true
    books[loan.bookId]?.let { it.available++ }
    println("Book returned successfully.")
}
fun showIssued() {
    val active = loans.filter { !it.returned }
    if (active.isEmpty()) { println("No books currently issued."); return }
    println("%-12s %-32s %-18s %-14s %-10s".format("BOOK ID", "TITLE", "STUDENT ID", "ISSUED", "STATUS"))
    active.forEach { loan ->
        val title = books[loan.bookId]?.title ?: "Unknown"
        println("%-12s %-32s %-18s %-14s %-10s".format(loan.bookId.take(12), title.take(32), loan.studentId.take(18), loan.date.toString(), "Issued"))
    }
}
fun main() {
    while (true) {
        println("\n=== LIBRARY MANAGEMENT SYSTEM ===")
        println("1. Add a new book\n2. Display all books\n3. Search for a book\n4. Issue a book\n5. Return a book\n6. Display currently issued books\n7. Exit")
        when (ask("Choose an option (1-7): ")) {
            "1" -> addBook()
            "2" -> printBooks(books.values)
            "3" -> searchBook()
            "4" -> issueBook()
            "5" -> returnBook()
            "6" -> showIssued()
            "7" -> { println("Goodbye!"); return }
            else -> println("Invalid option. Please choose 1-7.")
        }
    }
}
