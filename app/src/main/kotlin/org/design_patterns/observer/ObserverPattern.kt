package org.design_patterns.observer

interface Observer {
  fun update()
}

abstract class Subject {
  private val observers: MutableList<Observer> = mutableListOf<Observer>()

  fun attach(observer: Observer) = this.observers.add(observer)
  fun detach(observer: Observer) = this.observers.remove(observer)

  protected fun onUpdate() = this.observers.forEach { observer -> observer.update() }
}

typealias Score = Pair<Int, Int>

class Game : Subject() {
  var score: Score = Pair(0, 0)
    private set(value) {
      field = value
      this.onUpdate()
    }

  fun onTeamAScored() {
    this.score = this.score.copy(first = this.score.first + 1)
  }

  fun onTeamBScored() {
    this.score = this.score.copy(second = this.score.second + 1)
  }
}

class ScoreAnnouncer(private val game: Game) : Observer {
  init {
    game.attach(this)
  }

  override fun update() {
    val (teamAScore, teamBScore) = game.score

    println("The score is currently : $teamAScore - $teamBScore")
  }
}

class LeadingTeamAnnouncer(private val game: Game) : Observer {
  init {
    game.attach(this)
  }

  override fun update() {
    val (teamAScore, teamBScore) = game.score

    val announcement = when {
      teamAScore > teamBScore -> "Team A is in lead"
      teamAScore < teamBScore -> "Team B is in lead"
      else -> "Team A and B are in a tie"
    }
    println(announcement)
  }
}

fun run() {
  var game = Game()

  val scoreAnnouncer = ScoreAnnouncer(game)

  val leadingTimeAnnouncer = LeadingTeamAnnouncer(game)

  game.onTeamAScored()
  game.onTeamBScored()
  game.onTeamBScored()
}
