package de.tum.`in`.tumcampusapp.service.di

import dagger.Subcomponent
import de.tum.`in`.tumcampusapp.component.ui.onboarding.StartupActivity
import de.tum.`in`.tumcampusapp.component.ui.ticket.activity.EventsActivity
import de.tum.`in`.tumcampusapp.service.DownloadWorker

@Subcomponent(modules = [DownloadModule::class])
interface DownloadComponent {

    fun inject(downloadWorker: DownloadWorker)

    fun inject(eventsActivity: EventsActivity)

    fun inject(startupActivity: StartupActivity)

    @Subcomponent.Builder
    interface Builder {

        fun downloadModule(downloadModule: DownloadModule): Builder

        fun build(): DownloadComponent

    }

}
