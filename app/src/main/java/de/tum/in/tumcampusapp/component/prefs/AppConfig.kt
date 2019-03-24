package de.tum.`in`.tumcampusapp.component.prefs

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager.RINGER_MODE_NORMAL
import androidx.core.content.edit
import com.google.gson.Gson
import de.tum.`in`.tumcampusapp.BuildConfig
import de.tum.`in`.tumcampusapp.component.ui.chat.model.ChatMember
import de.tum.`in`.tumcampusapp.component.ui.news.model.NewsAlert
import de.tum.`in`.tumcampusapp.component.ui.overview.CardManager
import de.tum.`in`.tumcampusapp.component.ui.overview.card.Card
import de.tum.`in`.tumcampusapp.utils.Const
import de.tum.`in`.tumcampusapp.utils.DateTimeUtils.parseIsoDateWithMillis
import de.tum.`in`.tumcampusapp.utils.tryOrNull
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Inject

class AppConfig @Inject constructor(
        private val sharedPrefs: SharedPreferences
) {

    constructor(context: Context) : this(context.defaultSharedPreferences)

    var tumOnlinePersonId: String?
        get() = sharedPrefs.getString(Const.TUMO_PIDENT_NR)
        set(value) = sharedPrefs.edit { putString(Const.TUMO_PIDENT_NR, value) }

    var chatRoomDisplayName: String?
        get() = sharedPrefs.getString(Const.CHAT_ROOM_DISPLAY_NAME)
        set(value) = sharedPrefs.edit { putString(Const.CHAT_ROOM_DISPLAY_NAME, value) }

    var lrzId: String?
        get() = sharedPrefs.getString(Const.LRZ_ID)
        set(value) = sharedPrefs.edit { putString(Const.LRZ_ID, value) }

    var defaultCampus: String
        get() = sharedPrefs.getString(Const.DEFAULT_CAMPUS, "G")
        set(value) = sharedPrefs.edit { putString(Const.DEFAULT_CAMPUS, value) }

    var role: String?
        get() = sharedPrefs.getString(Const.ROLE)
        set(value) = sharedPrefs.edit { putString(Const.ROLE, value) }

    var firebaseRegId: String?
        get() = sharedPrefs.getString(Const.FCM_REG_ID)
        set(value) = sharedPrefs.edit { putString(Const.FCM_REG_ID, value) }

    var firebaseTokenId: String?
        get() = sharedPrefs.getString(Const.FCM_TOKEN_ID)
        set(value) = sharedPrefs.edit { putString(Const.FCM_TOKEN_ID, value) }

    var firebaseRegIdSentToServer: Boolean
        get() = sharedPrefs.getBoolean(Const.FCM_REG_ID_SENT_TO_SERVER, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.FCM_REG_ID_SENT_TO_SERVER, value) }

    var firebaseInstanceId: String?
        get() = sharedPrefs.getString(Const.FCM_INSTANCE_ID)
        set(value) = sharedPrefs.edit { putString(Const.FCM_INSTANCE_ID, value) }

    var firebaseRegIdLastTransmission: Long
        get() = sharedPrefs.getLong(Const.FCM_REG_ID_LAST_TRANSMISSION, 0L)
        set(value) = sharedPrefs.edit { putLong(Const.FCM_REG_ID_LAST_TRANSMISSION, value) }

    var updateMessage: String?
        get() = sharedPrefs.getString(Const.UPDATE_MESSAGE)
        set(value) = sharedPrefs.edit { putString(Const.UPDATE_MESSAGE, value) }

    var showUpdateNote: Boolean
        get() = sharedPrefs.getBoolean(Const.SHOW_UPDATE_NOTE, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.SHOW_UPDATE_NOTE, value) }

    var uniqueId: String?
        get() = sharedPrefs.getString(Const.PREF_UNIQUE_ID)
        set(value) = sharedPrefs.edit { putString(Const.PREF_UNIQUE_ID, value) }

    var privateKey: String?
        get() = sharedPrefs.getString(Const.PRIVATE_KEY)
        set(value) = sharedPrefs.edit { putString(Const.PRIVATE_KEY, value) }

    var publicKey: String?
        get() = sharedPrefs.getString(Const.PUBLIC_KEY)
        set(value) = sharedPrefs.edit { putString(Const.PUBLIC_KEY, value) }

    var publicKeyUploaded: Boolean
        get() = sharedPrefs.getBoolean(Const.PUBLIC_KEY_UPLOADED, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.PUBLIC_KEY_UPLOADED, value) }

    var accessToken: String?
        get() = sharedPrefs.getString(Const.ACCESS_TOKEN)
        set(value) = sharedPrefs.edit { putString(Const.ACCESS_TOKEN, value) }

    var tumOnlineStudentId: String?
        get() = sharedPrefs.getString(Const.TUMO_STUDENT_ID)
        set(value) = sharedPrefs.edit { putString(Const.TUMO_STUDENT_ID, value) }

    var tumOnlineEmployeeId: String?
        get() = sharedPrefs.getString(Const.TUMO_EMPLOYEE_ID)
        set(value) = sharedPrefs.edit { putString(Const.TUMO_EMPLOYEE_ID, value) }

    var tumOnlineExternalId: String?
        get() = sharedPrefs.getString(Const.TUMO_EXTERNAL_ID)
        set(value) = sharedPrefs.edit { putString(Const.TUMO_EXTERNAL_ID, value) }

    var isSilenceServiceActivated: Boolean
        get() = sharedPrefs.getBoolean(Const.SILENCE_SERVICE, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.SILENCE_SERVICE, value) }

    var syncCalendar: Boolean
        get() = sharedPrefs.getBoolean(Const.SYNC_CALENDAR, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.SYNC_CALENDAR, value) }

    var newspread: Int
        get() = sharedPrefs.getString("news_newspread", 7.toString()).toInt()
        set(value) = sharedPrefs.edit { putString("news_newspread", value.toString()) }

    var latestNewsOnly: Boolean
        get() = sharedPrefs.getBoolean("card_news_latest_only", true)
        set(value) = sharedPrefs.edit { putBoolean("card_news_latest_only", value) }

    var hasNewsNotificationsEnabled: Boolean
        get() = sharedPrefs.getBoolean("card_news_phone", false)
        set(value) = sharedPrefs.edit { putBoolean("card_news_phone", value) }

    var hasEduroamNotificationsEnabled: Boolean
        get() = sharedPrefs.getBoolean("card_eduroam_phone", false)
        set(value) = sharedPrefs.edit { putBoolean("card_eduroam_phone", value) }

    var showLoginCard: Boolean
        get() = sharedPrefs.getBoolean(CardManager.SHOW_LOGIN, true)
        set(value) = sharedPrefs.edit { putBoolean(CardManager.SHOW_LOGIN, value) }

    var showSupportCard: Boolean
        get() = sharedPrefs.getBoolean(CardManager.SHOW_SUPPORT, true)
        set(value) = sharedPrefs.edit { putBoolean(CardManager.SHOW_SUPPORT, value) }

    var savedAppVersion: Int
        get() = sharedPrefs.getString(Const.SAVED_APP_VERSION, BuildConfig.VERSION_CODE.toString()).toInt()
        set(value) = sharedPrefs.edit { putString(Const.SAVED_APP_VERSION, value.toString()) }

    var rainbowMode: Boolean
        get() = sharedPrefs.getBoolean(Const.RAINBOW_MODE, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.RAINBOW_MODE, value) }

    var isChatEnabled: Boolean
        get() = sharedPrefs.getBoolean(Const.GROUP_CHAT_ENABLED, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.GROUP_CHAT_ENABLED, value) }

    var autoJoinChatRooms: Boolean
        get() = sharedPrefs.getBoolean(Const.AUTO_JOIN_NEW_ROOMS, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.AUTO_JOIN_NEW_ROOMS, value) }

    var chatMember: ChatMember?
        get() {
            val value = sharedPrefs.getString(Const.CHAT_MEMBER) ?: return null
            return Gson().fromJson(value, ChatMember::class.java)
        }
        set(value) {
            val json = Gson().toJson(value)
            sharedPrefs.edit { putString(Const.CHAT_MEMBER, json) }
        }

    var creditcardHolder: String
        get() = sharedPrefs.getString(Const.KEY_CARD_HOLDER, "")
        set(value) = sharedPrefs.edit { putString(Const.KEY_CARD_HOLDER, value) }

    var showWifiSetupNotification: Boolean
        get() = sharedPrefs.getBoolean("wifi_setup_notification_dismissed", true)
        set(value) = sharedPrefs.edit { putBoolean("wifi_setup_notification_dismissed", value) }

    var isTumOnlineDisabled: Boolean
        get() = sharedPrefs.getBoolean(Const.TUMO_DISABLED, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.TUMO_DISABLED, value) }

    var isEmployeeMode: Boolean
        get() = sharedPrefs.getBoolean(Const.EMPLOYEE_MODE, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.EMPLOYEE_MODE, value) }

    var hasTransportNotificationsEnabled: Boolean
        get() = sharedPrefs.getBoolean("card_mvv_phone", false)
        set(value) = sharedPrefs.edit { putBoolean("card_mvv_phone", value) }

    var hasCalendarNotificationsEnabled: Boolean
        get() = sharedPrefs.getBoolean("card_next_phone", false)
        set(value) = sharedPrefs.edit { putBoolean("card_next_phone", value) }

    var hasTuitionFeesNotificationsEnabled: Boolean
        get() = sharedPrefs.getBoolean("card_tuition_fee_phone", true)
        set(value) = sharedPrefs.edit { putBoolean("card_tuition_fee_phone", value) }

    var hasCafeteriaNotificationsEnabled: Boolean
        get() = sharedPrefs.getBoolean("card_cafeteria_phone", true)
        set(value) = sharedPrefs.edit { putBoolean("card_cafeteria_phone", value) }

    var hasChatNotificationsEnabled: Boolean
        get() = sharedPrefs.getBoolean("card_chat_phone", true)
        set(value) = sharedPrefs.edit { putBoolean("card_chat_phone", value) }

    var isBackgroundServiceEnabled: Boolean
        get() = sharedPrefs.getBoolean(Const.BACKGROUND_MODE, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.BACKGROUND_MODE, value) }

    var isBackgroundServiceAlwaysEnabled: Boolean
        get() = sharedPrefs.getString("background_mode_set_to") == 0.toString()
        set(value) = sharedPrefs.edit {
            val prefsValue = if (value) 0.toString() else ""
            putString("background_mode_set_to", prefsValue)
        }

    var isCalendarInWeekMode: Boolean
        get() = sharedPrefs.getBoolean(Const.CALENDAR_WEEK_MODE, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.CALENDAR_WEEK_MODE, value) }

    var showCanceledEvents: Boolean
        get() = sharedPrefs.getBoolean(Const.CALENDAR_FILTER_CANCELED, true)
        set(value) = sharedPrefs.edit { putBoolean(Const.CALENDAR_FILTER_CANCELED, value) }

    var refreshCards: Boolean
        get() = sharedPrefs.getBoolean(Const.REFRESH_CARDS, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.REFRESH_CARDS, value) }

    var lastUpdate: Long
        get() = sharedPrefs.getLong("last_update", 0L)
        set(value) = sharedPrefs.edit { putLong("last_update", value) }

    var showTopNews: Boolean
        get() = sharedPrefs.getBoolean("show_top_news", false)
        set(value) = sharedPrefs.edit { putBoolean("show_top_news", value) }

    private var newsAlertUntil: String?
        get() = sharedPrefs.getString(Const.NEWS_ALERT_SHOW_UNTIL)
        set(value) = sharedPrefs.edit { putString(Const.NEWS_ALERT_SHOW_UNTIL, value) }

    private var newsAlertImageUrl: String?
        get() = sharedPrefs.getString(Const.NEWS_ALERT_IMAGE)
        set(value) = sharedPrefs.edit { putString(Const.NEWS_ALERT_IMAGE, value) }

    private var newsAlertUrl: String?
        get() = sharedPrefs.getString(Const.NEWS_ALERT_LINK)
        set(value) = sharedPrefs.edit { putString(Const.NEWS_ALERT_LINK, value) }

    var isSilenceOn: Boolean
        get() = sharedPrefs.getBoolean(Const.SILENCE_ON, false)
        set(value) = sharedPrefs.edit { putBoolean(Const.SILENCE_ON, value) }

    var silenceOldState: Int
        get() = sharedPrefs.getString(Const.SILENCE_OLD_STATE, RINGER_MODE_NORMAL.toString()).toInt()
        set(value) = sharedPrefs.edit { putString(Const.SILENCE_OLD_STATE, value.toString()) }

    var silenceMode: String
        get() = sharedPrefs.getString("silent_mode_set_to", 0.toString())
        set(value) = sharedPrefs.edit { putString("silent_mode_set_to", value) }

    var newsAlert: NewsAlert?
        get() = fetchNewsAlert()
        set(value) {
            value?.let { storeNewsAlert(value) }
        }

    private fun fetchNewsAlert(): NewsAlert? {
        val displayUntil = newsAlertUntil ?: return null
        val until = tryOrNull { parseIsoDateWithMillis(displayUntil) } ?: return null

        if (until.isBeforeNow || showTopNews.not()) {
            return null
        }

        val link = newsAlertUrl ?: return null
        val imageUrl = newsAlertImageUrl ?: return null
        return NewsAlert(imageUrl, link, displayUntil)
    }

    private fun storeNewsAlert(newsAlert: NewsAlert) {
        val oldShowUntil = newsAlertUntil
        val oldImage = newsAlertImageUrl

        newsAlertImageUrl = newsAlert.url
        newsAlertUrl = newsAlert.link

        // there is a NewsAlert update if the image link or the date changed
        // --> Card should be displayed again
        showTopNews = oldShowUntil != newsAlert.displayUntil || oldImage != newsAlert.url

        newsAlertUntil = newsAlert.displayUntil
    }

    fun defaultStationForCampus(campus: String): String? {
        return sharedPrefs.getString("card_stations_default_$campus")
    }

    fun showNewspread(newspread: Int, defaultValue: Boolean): Boolean {
        return sharedPrefs.getBoolean("news_source_$newspread", defaultValue)
    }

    fun showNewspread(newspread: Int): Boolean {
        return showNewspread(newspread, newspread <= 7)
    }

    fun showNewsSource(newspread: Int): Boolean {
        return sharedPrefs.getBoolean("card_news_source_$newspread", true)
    }

    fun setShowNewsSource(newsSource: Int, show: Boolean) {
        sharedPrefs.edit { putBoolean("news_source_$newsSource", show) }
    }

    fun showCafeteriaMenu(menu: String): Boolean {
        val defaultValue = "tg" == menu || "ae" == menu
        return sharedPrefs.getBoolean("card_cafeteria_$menu", defaultValue)
    }

    fun showCardOnHome(settingsPrefix: String): Boolean {
        return sharedPrefs.getBoolean(settingsPrefix + "_start", true)
    }

    fun cardPosition(clazz: Class<out Card>): Int {
        val key = "${clazz.simpleName}_card_position"
        return sharedPrefs.getInt(key, -1)
    }

    fun setCardPosition(clazz: Class<out Card>, position: Int) {
        val key = "${clazz.simpleName}_card_position"
        return sharedPrefs.edit { putInt(key, position) }
    }

}

fun SharedPreferences.getString(key: String): String? {
    val result = getString(key, "")
    return if (result.isNotBlank()) result else null
}
