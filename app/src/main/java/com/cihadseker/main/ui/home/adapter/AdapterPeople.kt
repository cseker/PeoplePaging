package com.cihadseker.main.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cihadseker.core.ui.DataBindingAdapter
import com.cihadseker.main.R
import com.cihadseker.main.data.uimodel.PeopleUI
import javax.inject.Inject

class AdapterPeople @Inject constructor() : DataBindingAdapter<PeopleUI>(PeopleDiffCallBack()) {

    override fun getItemLayoutId(viewType: Int) = R.layout.item_people

    class PeopleDiffCallBack : DiffUtil.ItemCallback<PeopleUI>() {
        override fun areItemsTheSame(oldItem: PeopleUI, newItem: PeopleUI) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PeopleUI, newItem: PeopleUI) =
            oldItem == newItem
    }
}
