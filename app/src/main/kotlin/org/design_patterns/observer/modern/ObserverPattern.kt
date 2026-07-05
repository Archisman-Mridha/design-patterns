package org.design_patterns.observer.modern

import kotlin.properties.Delegates

typealias GameObserver = (Score) -> Unit

typealias Score = Pair<Int, Int>

class Game(private val observers: List<GameObserver>) {
  var score: Score by Delegates.observable(Pair(0, 0)) { _, _, newScore ->
    this.observers.forEach { observer -> observer(newScore) }
  }

  fun onTeamAScored() {
    this.score = this.score.copy(first = this.score.first + 1)
  }

  fun onTeamBScored() {
    this.score = this.score.copy(second = this.score.second + 1)
  }
}

class ScoreAnnouncer {
  fun update(score: Score) {
    val (teamAScore, teamBScore) = score

    println("The score is currently : $teamAScore - $teamBScore")
  }
}

class LeadingTeamAnnouncer {
  fun update(score: Score) {
    val (teamAScore, teamBScore) = score

    val announcement = when {
      teamAScore > teamBScore -> "Team A is in lead"
      teamAScore < teamBScore -> "Team B is in lead"
      else -> "Team A and B are in a tie"
    }
    println(announcement)
  }
}

fun run() {
  val scoreAnnouncer = ScoreAnnouncer()

  val leadingTimeAnnouncer = LeadingTeamAnnouncer()

  var game = Game(
    listOf(
      scoreAnnouncer::update,
      leadingTimeAnnouncer::update
    )
  )

  game.onTeamAScored()
  game.onTeamBScored()
  game.onTeamBScored()
}
