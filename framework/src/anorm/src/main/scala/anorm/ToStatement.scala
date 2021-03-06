package anorm

import java.lang.{
  Boolean => JBool,
  Byte => JByte,
  Character,
  Double => JDouble,
  Float => JFloat,
  Long => JLong,
  Integer,
  Short => JShort
}

import java.util.{ UUID => JUUID }

import java.math.{ BigDecimal => JBigDec, BigInteger }

import java.sql.{ PreparedStatement, Types, Timestamp }

/** Sets value as statement parameter. */
trait ToStatement[A] {

  /**
   * Sets value |v| on statement |s| at specified |index|.
   */
  def set(s: PreparedStatement, index: Int, v: A): Unit
}

/**
 * Provided conversions to set statement parameter.
 */
object ToStatement extends JodaToStatement {
  import scala.collection.immutable.SortedSet

  /**
   * Sets boolean value on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE enabled = {b}").on('b -> true)
   * }}}
   */
  implicit object booleanToStatement extends ToStatement[Boolean] {
    def set(s: PreparedStatement, i: Int, b: Boolean): Unit = s.setBoolean(i, b)
  }

  /**
   * Sets Java Boolean object on statement.
   * For `null` value, `setNull` with `BOOLEAN` is called on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE enabled = {b}").
   *   on('b -> java.lang.Boolean.TRUE)
   * }}}
   */
  implicit object javaBooleanToStatement extends ToStatement[JBool] {
    def set(s: PreparedStatement, i: Int, b: JBool): Unit =
      if (b != null) s.setBoolean(i, b) else s.setNull(i, Types.BOOLEAN)
  }

  /**
   * Sets byte value on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").on('b -> 1.toByte)
   * }}}
   */
  implicit object byteToStatement extends ToStatement[Byte] {
    def set(s: PreparedStatement, i: Int, b: Byte): Unit = s.setByte(i, b)
  }

  /**
   * Sets Java Byte object on statement.
   * For `null` value, `setNull` with `TINYINT` is called on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").on('b -> new java.lang.Byte(1))
   * }}}
   */
  implicit object javaByteToStatement extends ToStatement[JByte] {
    def set(s: PreparedStatement, i: Int, b: JByte): Unit =
      if (b != null) s.setByte(i, b) else s.setNull(i, Types.TINYINT)
  }

  /**
   * Sets double value on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").on('b -> 1d)
   * }}}
   */
  implicit object doubleToStatement extends ToStatement[Double] {
    def set(s: PreparedStatement, i: Int, d: Double): Unit = s.setDouble(i, d)
  }

  /**
   * Sets Java Double object on statement.
   * For `null` value, `setNull` with `DOUBLE` is called on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").
   *   on('b -> new java.lang.Double(1d))
   * }}}
   */
  implicit object javaDoubleToStatement extends ToStatement[JDouble] {
    def set(s: PreparedStatement, i: Int, d: JDouble): Unit =
      if (d != null) s.setDouble(i, d) else s.setNull(i, Types.DOUBLE)
  }

  /**
   * Sets float value on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").on('b -> 1f)
   * }}}
   */
  implicit object floatToStatement extends ToStatement[Float] {
    def set(s: PreparedStatement, i: Int, f: Float): Unit = s.setFloat(i, f)
  }

  /**
   * Sets Java Float object on statement.
   * For `null` value, `setNull` with `FLOAT` is called on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").
   *   on('b -> new java.lang.Float(1f))
   * }}}
   */
  implicit object javaFloatToStatement extends ToStatement[JFloat] {
    def set(s: PreparedStatement, i: Int, f: JFloat): Unit =
      if (f != null) s.setFloat(i, f) else s.setNull(i, Types.FLOAT)
  }

  /**
   * Sets long value on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").on('b -> 1l)
   * }}}
   */
  implicit object longToStatement extends ToStatement[Long] {
    def set(s: PreparedStatement, i: Int, l: Long): Unit = s.setLong(i, l)
  }

  /**
   * Sets Java Long object on statement.
   * For `null` value, `setNull` with `BIGINT` is called on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").
   *   on('b -> new java.lang.Long(1l))
   * }}}
   */
  implicit object javaLongToStatement extends ToStatement[JLong] {
    def set(s: PreparedStatement, i: Int, l: JLong): Unit =
      if (l != null) s.setLong(i, l) else s.setNull(i, Types.BIGINT)
  }

  /**
   * Sets integer value on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").on('b -> 1)
   * }}}
   */
  implicit object intToStatement extends ToStatement[Int] {
    def set(s: PreparedStatement, i: Int, v: Int): Unit = s.setInt(i, v)
  }

  /**
   * Sets Java Integer object on statement.
   * For `null` value, `setNull` with `INTEGER` is called on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").
   *   on('b -> new java.lang.Integer(1))
   * }}}
   */
  implicit object integerToStatement extends ToStatement[Integer] {
    def set(s: PreparedStatement, i: Int, v: Integer): Unit =
      if (v != null) s.setInt(i, v) else s.setNull(i, Types.INTEGER)
  }

  /**
   * Sets short value on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").on('b -> 1.toShort)
   * }}}
   */
  implicit object shortToStatement extends ToStatement[Short] {
    def set(s: PreparedStatement, i: Int, v: Short): Unit = s.setShort(i, v)
  }

  /**
   * Sets Java Short object on statement.
   * For `null` value, `setNull` with `SMALLINT` is called on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE flag = {b}").
   *   on('b -> new java.lang.Short(1.toShort))
   * }}}
   */
  implicit object javaShortToStatement extends ToStatement[JShort] {
    def set(s: PreparedStatement, i: Int, v: JShort): Unit =
      if (v != null) s.setShort(i, v) else s.setNull(i, Types.SMALLINT)
  }

  /**
   * Sets Java Character as parameter value.
   * For `null` character, `setNull` with `VARCHAR` is called on statement.
   *
   * {{{
   * SQL("SELECT * FROM tbl WHERE flag = {c}").
   *   on("c" -> new java.lang.Character('f'))
   * }}}
   */
  implicit object characterToStatement extends ToStatement[Character] {
    def set(s: PreparedStatement, i: Int, ch: Character) =
      if (ch != null) s.setString(i, ch.toString) else s.setNull(i, Types.CHAR)
  }

  /**
   * Sets character as parameter value.
   *
   * {{{
   * SQL("SELECT * FROM tbl WHERE flag = {c}").on("c" -> 'f')
   * }}}
   */
  implicit object charToStatement extends ToStatement[Char] {
    def set(s: PreparedStatement, i: Int, ch: Char): Unit =
      s.setString(i, ch.toString)
  }

  /**
   * Sets string as parameter value.
   * Value `null` is accepted.
   *
   * {{{
   * SQL("SELECT * FROM tbl WHERE name = {n}").on("n" -> "str")
   * }}}
   */
  implicit object stringToStatement extends ToStatement[String] {
    def set(s: PreparedStatement, i: Int, str: String) = s.setString(i, str)
  }

  /**
   * Sets null for not assigned value.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE category = {c}").on('c -> NotAssigned)
   * }}}
   */
  implicit object notAssignedToStatement extends ToStatement[NotAssigned.type] {
    def set(s: PreparedStatement, i: Int, n: NotAssigned.type): Unit =
      s.setObject(i, null)
  }

  /**
   * Sets null for None value.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE category = {c}")
   *   .on('c -> None)
   * }}}
   */
  @deprecated("Parameter value should be passed using `Option.empty[T]`",
    since = "2.3.7")
  implicit object noneToStatement extends ToStatement[None.type] {
    def set(s: PreparedStatement, i: Int, n: None.type) = s.setObject(i, null)
  }

  /**
   * Sets not empty optional A inferred as Some[A].
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE category = {c}").on('c -> Some("cat"))
   * }}}
   */
  implicit def someToStatement[A](implicit c: ToStatement[A]) =
    new ToStatement[Some[A]] with NotNullGuard {
      def set(s: PreparedStatement, index: Int, v: Some[A]): Unit =
        c.set(s, index, v.get)
    }

  /**
   * Sets optional A inferred as Option[A].
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE category = {c}").on('c -> Option("cat"))
   * SQL"SELECT * FROM Test WHERE nullable_int = ${Option.empty[Int]}"
   * }}}
   */
  implicit def optionToStatement[A >: Nothing](implicit c: ToStatement[A], meta: ParameterMetaData[A]) =
    new ToStatement[Option[A]] with NotNullGuard {
      def set(s: PreparedStatement, index: Int, o: Option[A]) = {
        o.fold[Unit](s.setNull(index, meta.jdbcType))(c.set(s, index, _))
      }
    }

  /**
   * Sets Java big integer on statement.
   * For `null` value, `setNull` with `NUMERIC` is called on statement.
   *
   * {{{
   * SQL("UPDATE tbl SET max = {m}").on('m -> new BigInteger(15))
   * }}}
   */
  implicit object javaBigIntegerToStatement extends ToStatement[BigInteger] {
    def set(s: PreparedStatement, index: Int, v: BigInteger): Unit =
      if (v != null) s.setBigDecimal(index, new JBigDec(v))
      else s.setNull(index, Types.NUMERIC)
  }

  /**
   * Sets big integer on statement.
   * For `null` value, `setNull` with `NUMERIC` is called on statement.
   *
   * {{{
   * SQL("UPDATE tbl SET max = {m}").on('m -> BigInt(15))
   * }}}
   */
  implicit object scalaBigIntegerToStatement extends ToStatement[BigInt] {
    def set(s: PreparedStatement, index: Int, v: BigInt): Unit =
      if (v != null) s.setBigDecimal(index, new JBigDec(v.bigInteger))
      else s.setNull(index, Types.NUMERIC)
  }

  /**
   * Sets Java big decimal on statement.
   * Value `null` is accepted.
   *
   * {{{
   * SQL("UPDATE tbl SET max = {m}").on('m -> new java.math.BigDecimal(10.02f))
   * }}}
   */
  implicit object javaBigDecimalToStatement extends ToStatement[JBigDec] {
    def set(s: PreparedStatement, index: Int, v: JBigDec): Unit =
      s.setBigDecimal(index, v)
  }

  /**
   * Sets big decimal on statement.
   * For `null` value, `setNull` with `DECIMAL` is called on statement.
   *
   * {{{
   * SQL("UPDATE tbl SET max = {m}").on('m -> BigDecimal(10.02f))
   * }}}
   */
  implicit object scalaBigDecimalToStatement extends ToStatement[BigDecimal] {
    def set(s: PreparedStatement, index: Int, v: BigDecimal): Unit =
      if (v != null) s.setBigDecimal(index, v.bigDecimal)
      else s.setNull(index, Types.DECIMAL)
  }

  /**
   * Sets timestamp as statement parameter.
   * Value `null` is accepted.
   *
   * {{{
   * SQL("UPDATE tbl SET modified = {ts}").
   *   on('ts -> new Timestamp(date.getTime))
   * }}}
   */
  implicit object timestampToStatement extends ToStatement[Timestamp] {
    def set(s: PreparedStatement, index: Int, ts: Timestamp): Unit =
      s.setTimestamp(index, ts)
  }

  /**
   * Sets date as statement parameter.
   * For `null` value, `setNull` with `TIMESTAMP` is called on statement.
   *
   * {{{
   * SQL("UPDATE tbl SET modified = {d}").on('d -> new Date())
   * }}}
   */
  implicit object dateToStatement extends ToStatement[java.util.Date] {
    def set(s: PreparedStatement, index: Int, date: java.util.Date): Unit =
      if (date != null) s.setTimestamp(index, new Timestamp(date.getTime))
      else s.setNull(index, Types.TIMESTAMP)
  }

  /**
   * Sets UUID as statement parameter.
   * For `null` value, `setNull` with `VARCHAR` is called on statement.
   *
   * {{{
   * SQL("INSERT INTO lang_tbl(id, name) VALUE ({i}, {n})").
   *   on("i" -> JUUID.randomUUID(), "n" -> "lang")
   * }}}
   */
  implicit object uuidToStatement extends ToStatement[JUUID] {
    def set(s: PreparedStatement, index: Int, id: JUUID): Unit =
      if (id != null) s.setString(index, id.toString)
      else s.setNull(index, Types.VARCHAR)
  }

  /**
   * Sets opaque value as statement parameter.
   * UNSAFE: It's set using [[java.sql.PreparedStatement.setObject]].
   *
   * {{{
   * SQL("EXEC indexed_at {d}").on('d -> anorm.Object(new java.util.Date()))
   * }}}
   */
  implicit object objectToStatement extends ToStatement[anorm.Object] {
    def set(s: PreparedStatement, index: Int, o: anorm.Object): Unit =
      s.setObject(index, o.value)
  }

  /**
   * Sets Id parameter on statement.
   *
   * {{{
   * SQL("INSERT INTO tbl(id, name) VALUES ({i}, {v}").
   *   on("i" -> anorm.Id("id"), "v" -> "name")
   * }}}
   */
  implicit def idToStatement[A](implicit c: ToStatement[A]) =
    new ToStatement[Id[A]] with NotNullGuard {
      def set(s: PreparedStatement, index: Int, id: Id[A]): Unit =
        c.set(s, index, id.get)
    }

  /**
   * Sets multi-value parameter from list on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE cat IN ({categories})").
   *   on('categories -> List(1, 3, 4)
   * }}}
   */
  implicit def listToStatement[A](implicit c: ToStatement[A]): ToStatement[List[A]] = traversableToStatement[A, List[A]]

  /**
   * Sets multi-value parameter from sequence on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE cat IN ({categories})").
   *   on('categories -> Seq("a", "b", "c")
   * }}}
   */
  implicit def seqToStatement[A](implicit c: ToStatement[A]): ToStatement[Seq[A]] = traversableToStatement[A, Seq[A]]

  /**
   * Sets multi-value parameter from set on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE cat IN ({categories})").
   *   on('categories -> Set(1, 3, 4)
   * }}}
   */
  implicit def setToStatement[A](implicit c: ToStatement[A]): ToStatement[Set[A]] = traversableToStatement[A, Set[A]]

  /**
   * Sets multi-value parameter from sorted set on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE cat IN ({categories})").
   *   on('categories -> SortedSet("a", "b", "c")
   * }}}
   */
  implicit def sortedSetToStatement[A](implicit c: ToStatement[A]): ToStatement[SortedSet[A]] = traversableToStatement[A, SortedSet[A]]

  /**
   * Sets multi-value parameter from stream on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE cat IN ({categories})").
   *   on('categories -> Stream(1, 3, 4)
   * }}}
   */
  implicit def streamToStatement[A](implicit c: ToStatement[A]): ToStatement[Stream[A]] = traversableToStatement[A, Stream[A]]

  /**
   * Sets multi-value parameter from vector on statement.
   *
   * {{{
   * SQL("SELECT * FROM Test WHERE cat IN ({categories})").
   *   on('categories -> Vector("a", "b", "c")
   * }}}
   */
  implicit def vectorToStatement[A](implicit c: ToStatement[A]): ToStatement[Vector[A]] = traversableToStatement[A, Vector[A]]

  /**
   * Sets multi-value parameter on statement, with custom formatting
   * (using [[anorm.SeqParameter]]).
   *
   * {{{
   * import anorm.SeqParameter
   * SQL("SELECT * FROM Test t WHERE {categories}").
   *   on('categories -> SeqParameter(
   *     values = Seq("a", "b", "c"), separator = " OR ",
   *     pre = "EXISTS (SELECT NULL FROM j WHERE t.id=j.id AND name=",
   *     post = ")"))
   * }}}
   */
  implicit def seqParamToStatement[A](implicit c: ToStatement[Seq[A]]) =
    new ToStatement[SeqParameter[A]] with NotNullGuard {
      def set(s: PreparedStatement, offset: Int, ps: SeqParameter[A]): Unit =
        c.set(s, offset, ps.values)
    }

  @inline private def traversableToStatement[A, T <: Traversable[A]](implicit c: ToStatement[A]) = new ToStatement[T] with NotNullGuard {
    def set(s: PreparedStatement, offset: Int, ps: T) =
      if (ps == null) throw new IllegalArgumentException()
      else ps.foldLeft(offset) { (i, p) => c.set(s, i, p); i + 1 }
  }

  /**
   * Sets an array parameter on statement.
   * @see [[java.sql.Array]]
   *
   * {{{
   * SQL("INSERT INTO Table(arr) VALUES {a}").on(Array("A", "2", "C"))
   * }}}
   */
  implicit def arrayToParameter[A <: AnyRef](implicit m: ParameterMetaData[A]) =
    new ToStatement[Array[A]] with NotNullGuard {
      def set(s: PreparedStatement, i: Int, arr: Array[A]) =
        if (arr == null) throw new IllegalArgumentException()
        else s.setArray(i, s.getConnection.createArrayOf(
          m.sqlType, arr.map(a => a: AnyRef)))
    }
}

import org.joda.time.{ DateTime, Instant }

/** Meta data for Joda parameters */
object JodaParameterMetaData {
  /** Date/time parameter meta data */
  implicit object JodaDateTimeMetaData extends ParameterMetaData[DateTime] {
    val sqlType = "TIMESTAMP"
    val jdbcType = Types.TIMESTAMP
  }

  /** Instant parameter meta data */
  implicit object JodaInstantMetaData extends ParameterMetaData[Instant] {
    val sqlType = "TIMESTAMP"
    val jdbcType = Types.TIMESTAMP
  }
}

sealed trait JodaToStatement { // TODO: Move in a separate file
  /**
   * Sets joda-time DateTime as statement parameter.
   * For `null` value, `setNull` with `TIMESTAMP` is called on statement.
   *
   * {{{
   * import org.joda.time.DateTime
   *
   * SQL("UPDATE tbl SET modified = {d}").on('d -> new DateTime())
   * }}}
   */
  implicit object jodaDateTimeToStatement extends ToStatement[DateTime] {
    def set(s: PreparedStatement, index: Int, date: DateTime): Unit =
      if (date != null) s.setTimestamp(index, new Timestamp(date.getMillis()))
      else s.setNull(index, Types.TIMESTAMP)
  }

  /**
   * Sets joda-time Instant as statement parameter.
   * For `null` value, `setNull` with `TIMESTAMP` is called on statement.
   *
   * {{{
   * import org.joda.time.Instant
   *
   * SQL("UPDATE tbl SET modified = {d}").on('d -> new Instant())
   * }}}
   */
  implicit object jodaInstantToStatement extends ToStatement[Instant] {
    def set(s: PreparedStatement, index: Int, instant: Instant): Unit =
      if (instant != null) s.setTimestamp(index,
        new Timestamp(instant.getMillis))
      else s.setNull(index, Types.TIMESTAMP)
  }
}
