package de.tum.`in`.tumcampusapp.component.ui.tufilm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.tum.`in`.tumcampusapp.component.ui.ticket.fragment.EventDetailsViewModel
import de.tum.`in`.tumcampusapp.component.ui.ticket.model.Event
import de.tum.`in`.tumcampusapp.component.ui.ticket.repository.EventsRemoteRepository
import de.tum.`in`.tumcampusapp.component.ui.ticket.repository.TicketsLocalRepository
import de.tum.`in`.tumcampusapp.component.ui.tufilm.model.Kino
import de.tum.`in`.tumcampusapp.component.ui.tufilm.repository.KinoLocalRepository
import de.tum.`in`.tumcampusapp.utils.Utils
import de.tum.`in`.tumcampusapp.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.IllegalStateException

class KinoDetailsViewModel @Inject constructor(
        private val kinoLocalRepository: KinoLocalRepository,
        private val eventsRemoteRepository: EventsRemoteRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var eventDetailsViewModel : EventDetailsViewModel
    /*private*/ lateinit var ticketCount: LiveData<Int?>
    //fun getTicketCount(): LiveData<Int?> {return ticketCount}

    private val _kino = MutableLiveData<Kino>()
    val kino: LiveData<Kino> = _kino

    private val _event= MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    fun initEvent(eventId: Int, ticketsLocalRepository: TicketsLocalRepository) {
        this.eventDetailsViewModel = EventDetailsViewModel(eventId, eventsRemoteRepository, ticketsLocalRepository)
        this.ticketCount = eventDetailsViewModel.ticketCount
    }

    fun fetchTicketCount(eventId: Int) {
        if(eventDetailsViewModel == null) throw IllegalStateException("eventDetailsViewModel not initialised. Call initEvent(...) first.")
        return eventDetailsViewModel.fetchTicketCount()
    }

    fun isEventBooked(event: Event): Boolean {
        if(eventDetailsViewModel == null) throw IllegalStateException("eventDetailsViewModel not initialised. Call initEvent(...) first.")
        return eventDetailsViewModel.isEventBooked(event)
    }

    fun getBookedTicketCount(event: Event): Int {
        if(eventDetailsViewModel == null) throw IllegalStateException("eventDetailsViewModel not initialised. Call initEvent(...) first.")
        return eventDetailsViewModel.getBookedTicketCount(event)
    }

    fun fetchKinoByPosition(position: Int) {
        compositeDisposable += kinoLocalRepository.getKinoByPosition(position)
                .subscribeOn(Schedulers.io())
                .subscribe(_kino::postValue)
    }

    fun fetchEventByMovieId(movieId: String) {
        compositeDisposable += kinoLocalRepository.getEventByMovieId(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(_event::postValue)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
