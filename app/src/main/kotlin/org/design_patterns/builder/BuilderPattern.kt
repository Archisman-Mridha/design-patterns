package org.design_patterns.builder

sealed interface Destination

@JvmInline
value class EmailAddress(val value: String) : Destination

@JvmInline
value class PhoneNumber(val value: String) : Destination

data class Subscription(val destination: Destination, val topic: Topic, val frequency: Frequency) {
  enum class Topic {
    NEWS,
    ANALYTICS,
    SECURITY_ALERTS
  }

  enum class Frequency {
    IMMEDIATELY,
    DAILY,
    WEEKLY
  }
}

data class NotificationSettings(val enabled: Boolean, val subscriptions: List<Subscription>)

interface INotificationSettingsBuilder {
  var enabled: Boolean
  fun addSubscription(destination: Destination, topic: Subscription.Topic, frequency: Subscription.Frequency)

  fun build(): NotificationSettings
}

class NotificationSettingsBuilder : INotificationSettingsBuilder {
  override var enabled = false
  private val subscriptions = mutableListOf<Subscription>()

  override fun addSubscription(destination: Destination, topic: Subscription.Topic, frequency: Subscription.Frequency) {
    this.subscriptions.add(Subscription(destination, topic, frequency))
  }

  override fun build() = NotificationSettings(this.enabled, this.subscriptions.toList())
}

fun run() {
  val notificationSettingsBuilder: INotificationSettingsBuilder = NotificationSettingsBuilder()

  notificationSettingsBuilder.enabled = true

  notificationSettingsBuilder.addSubscription(
    EmailAddress("archisman.mridha@gmail.com"), Subscription.Topic.SECURITY_ALERTS, Subscription.Frequency.IMMEDIATELY
  )

  val notificationSettings = notificationSettingsBuilder.build()
}
