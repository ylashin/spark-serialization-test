import java.util.Date

case class Customer(Id: Int,
                    Name: String,
                    BirthDate: Date)

case class Country(Id: Int,
                   Name: String,
                   Code: String)

case class ProductLineItem(Id: Int,
                           Name: String)

case class Invoice(InvoiceNo: String,
                   StockCode: String,
                   Description: String,
                   Quantity: Int,
                   UnitPrice: Double,
                   Customer: Customer,
                   Country: Country,
                   LineItems: Array[ProductLineItem])

object InvoiceGenerator {

  def generateSomeInvoices() = (0 to 100000)
    .map(x =>
      Invoice(
        s"INV-$x",
        s"CODE-$x",
        s"Description $x",
        x % 10,
        x.toDouble,
        Customer = Customer(x, "The customer",  new Date(2000, 1, 2)),
        Country = Country(x, s"Country $x", s"CODE$x"),
        LineItems = (0 to 2).map(i => ProductLineItem(i, s"Product$i")).toArray
      )
    )
}