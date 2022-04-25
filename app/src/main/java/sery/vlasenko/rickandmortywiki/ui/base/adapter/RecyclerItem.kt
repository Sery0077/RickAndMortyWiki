package sery.vlasenko.rickandmortywiki.ui.base.adapter

interface RecyclerItem {
    val id: String?
    override fun equals(other: Any?): Boolean
}