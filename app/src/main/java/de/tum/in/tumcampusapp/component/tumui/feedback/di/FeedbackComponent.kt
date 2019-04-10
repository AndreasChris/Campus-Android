package de.tum.`in`.tumcampusapp.component.tumui.feedback.di

import androidx.annotation.Nullable
import dagger.BindsInstance
import dagger.Subcomponent
import de.tum.`in`.tumcampusapp.component.tumui.feedback.FeedbackActivity

@Subcomponent(modules = [FeedbackModule::class])
interface FeedbackComponent {

    fun inject(feedbackActivity: FeedbackActivity)

    @Subcomponent.Builder
    interface Builder {

        fun feedbackModule(feedbackModule: FeedbackModule): FeedbackComponent.Builder

        @BindsInstance
        fun lrzId(@Nullable @LrzId lrzId: String): FeedbackComponent.Builder

        fun build(): FeedbackComponent

    }

}
