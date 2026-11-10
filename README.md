# LibraDesk — Library Management System

A responsive library management dashboard for a college assignment, plus a Kotlin console implementation.

## Live website

Publish `index.html` and its `css/` and `js/` folders using GitHub Pages (Settings → Pages → Deploy from a branch → main / root).

## Website features

- Add books with unique IDs, title, author, publication year, and available copies
- Display and search books by ID, title, or author
- Filter by availability
- Issue books to students and decrement stock
- Return books only against matching active issue records and increment stock
- View active loans and dashboard statistics
- Responsive layout for mobile and desktop

## How to run

**Website:** Open `index.html` in a browser or use VS Code Live Server. No installation needed.

**Kotlin console:** Run `kotlin/Main.kt` in IntelliJ IDEA, or compile with `kotlinc kotlin/Main.kt -include-runtime -d library.jar` then run `java -jar library.jar`.

## Storage limitations

The website stores data in the browser's `localStorage`. Each browser/device has its own records; data is not shared between visitors. Clearing site storage removes the records. The initial sample catalog is demo data. The Kotlin console version stores data in memory during execution and resets on restart. Neither is a production database-backed multiuser system.

## Assignment mapping

| Assignment task | Website | Kotlin console |
|---|---|---|
| Add books + validation | Yes | Yes |
| Display all books | Yes | Yes |
| Search by ID/title | Yes | Yes |
| Issue a book | Yes | Yes |
| Return a book with matching loan | Yes | Yes |
| Display issued books | Yes | Yes |
| Kotlin `when` menu | Not applicable (web navigation) | Yes |
