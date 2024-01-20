package com.appcessible.boardthebus.injection.component

import com.appcessible.boardthebus.fragment.BusArrivalDialogFragment
import com.appcessible.boardthebus.injection.module.BusArrivalFragmentScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
@BusArrivalFragmentScope
interface BusArrivalFragmentSubcomponent : AndroidInjector<BusArrivalDialogFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<BusArrivalDialogFragment>
}