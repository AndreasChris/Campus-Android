package de.tum.`in`.tumcampusapp.component.ui.overview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import de.tum.`in`.tumcampusapp.R
import de.tum.`in`.tumcampusapp.component.ui.overview.card.Card
import de.tum.`in`.tumcampusapp.component.ui.overview.card.CardViewHolder
import de.tum.`in`.tumcampusapp.utils.Utils

/**
 * Card that describes how to dismiss a card
 */
class SupportCard(context: Context) : Card(CardManager.CARD_SUPPORT, context, "") {

    public override fun discard(editor: Editor) =
            Utils.setSetting(context, CardManager.SHOW_SUPPORT, false)

    override fun shouldShow(prefs: SharedPreferences) =
            Utils.getSettingBool(context, CardManager.SHOW_SUPPORT, true)

    override fun getId() = 0

    companion object : CardAdapter.CardViewHolderFactory {
        override fun inflateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_support, parent, false)

            view.findViewById<View>(R.id.facebook_button)
                    .setOnClickListener { v ->
                        val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(view.context.getString(R.string.facebook_link))
                        }
                        v.context.startActivity(browserIntent)
                    }

            view.findViewById<View>(R.id.github_button)
                    .setOnClickListener { v ->
                        val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(view.context.getString(R.string.github_link))
                        }
                        v.context.startActivity(browserIntent)
                    }

            view.findViewById<View>(R.id.email_button)
                    .setOnClickListener { v ->
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            val uri = Uri.parse(view.context.getString(R.string.support_email_link))
                            setDataAndType(uri, "text/plain")
                            putExtra(Intent.EXTRA_SUBJECT, view.context.getString(R.string.feedback))
                        }
                        v.context.startActivity(Intent.createChooser(intent, "Send Email"))
                    }

            return CardViewHolder(view)
        }
    }
}