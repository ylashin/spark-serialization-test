import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel


object Serializer extends App {

  private def getConfig(useKryo: Boolean) = {
    val conf = new SparkConf()
    if (useKryo) {
      val classesToRegister: Array[Class[_]] = Array(
        classOf[Invoice],
        classOf[Customer],
        classOf[Country],
        classOf[java.util.Date],
        classOf[ProductLineItem],
        classOf[Array[Invoice]],
        classOf[Array[ProductLineItem]],
        classOf[scala.collection.mutable.WrappedArray.ofRef[_]]
      )
      conf.registerKryoClasses(classesToRegister)
      conf.set("spark.kryo.registrationRequired", "true")
    }

    conf
  }

  val spark = SparkSession.builder()
    .appName("Serializer")
    .config(getConfig(args.length > 0 && args(0).toLowerCase == "kryo"))
    .config("spark.local.dir", "E:\\Temp")
    .getOrCreate()

  val invoices = InvoiceGenerator.generateSomeInvoices()

  val df = spark
    .sparkContext
    .parallelize(invoices)
    .persist(StorageLevel.DISK_ONLY)

  println(df.count())

  df.take(10).foreach(println)

  println("Press any key to exit")
  System.in.read()
}
