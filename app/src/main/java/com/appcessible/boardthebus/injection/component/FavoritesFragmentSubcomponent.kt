package com.appcessible.boardthebus.injection.component

import com.appcessible.boardthebus.fragment.FavoritesFragment
import com.appcessible.boardthebus.injection.module.FavoritesFragmentScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
@FavoritesFragmentScope
interface FavoritesFragmentSubcomponent : AndroidInjector<FavoritesFragment> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<FavoritesFragment>
}