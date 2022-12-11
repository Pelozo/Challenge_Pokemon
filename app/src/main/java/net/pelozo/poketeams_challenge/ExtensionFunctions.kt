package net.pelozo.poketeams_challenge

import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.firebase.database.ValueEventListener
import net.pelozo.poketeams_challenge.base.BaseFragment

inline fun <reified T : ViewModel, B : ViewBinding> BaseFragment<B>.retrieveViewModel(): T {
    val viewModel: T by viewModels()
    return viewModel
}

inline fun <reified T : ViewModel, B : ViewBinding> BaseFragment<B>.withViewModel(body: T.() -> Unit): T {
    return if (baseViewModel == null) {
        val vm = retrieveViewModel<T, B>()
        vm.body()
        vm
    } else {
        baseViewModel as T
    }
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(livedata: L, body: (T) -> Unit) {
    livedata.observe(this, Observer(body))
}

fun <T : Any, L : LiveData<Event<T>>> LifecycleOwner.observeEvent(livedata: L, body: (T) -> Unit) {
    livedata.observe(this, EventObserver { body.invoke(it) })
}
