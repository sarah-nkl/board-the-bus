package com.appcessible.boardthebus.injection.module

import com.appcessible.boardthebus.fragment.BusArrivalDialogFragment
import com.appcessible.boardthebus.injection.component.BusArrivalFragmentSubcomponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Scope

@Module(subcomponents = [BusArrivalFragmentSubcomponent::class])
abstract class BusArrivalFragmentBindingModule {

    @Binds
    @IntoMap
    @ClassKey(BusArrivalDialogFragment::class)
    abstract fun bindBusArrivalFragmentInjectorFactory(factory: BusArrivalFragmentSubcomponent.Factory): AndroidInjector.Factory<*>
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BusArrivalFragmentScope
