package connect4

import java.io.{InputStream, OutputStream}

import scala.collection.mutable


class Board() {

   val board : Array[Array[String]] = Array.fill(6, 7)(" ")

  // boardState.
  val boardState: mutable.Map[Int, Int] =
    mutable.Map(0 -> 6, 1 -> 6, 2 -> 6, 3 -> 6, 4 -> 6, 5 -> 6, 6 -> 6)


  def draw(): String = {
    val columnNumbers = " 1 2 3 4 5 6 7"
    val horizontalSeparator = "---------------"

    s"""|$columnNumbers
        |$horizontalSeparator
        |${board.map(_.mkString("|", "|", "|")).mkString("\n|")}
        |$horizontalSeparator""".stripMargin
  }

}

class Connect4(outputStream: OutputStream, inputStream: InputStream)
  extends CommandLineApp(outputStream, inputStream, "Connect 4") {
  val b: Board = new Board
  override def processCommand(line: String): String = {

    // command argument is of the form:  col PlayerSymbol
    // 1 X   Player_1 choosing column 1
    // 2 O   Player_2 choosing column 2

    val in = line.split(" ")
    val col = in(0).toInt - 1
    val turn = in(1)
    val currentPos = b.boardState(col)
    b.board(currentPos - 1)(col) = turn
    b.boardState(col) = currentPos -  1 // update the column position
    b.draw()
  }
}

object Connect4 extends App {
  new Connect4(System.out, System.in).run()
}
