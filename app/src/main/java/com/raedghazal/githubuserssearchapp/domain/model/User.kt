package com.raedghazal.githubuserssearchapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val login: String?,
    @PrimaryKey
    val id: Int,
    val node_id: String?,
    val avatar_url: String?,
    val gravatar_id: String?,
    val url: String?,
    val html_url: String?,
    val followers_url: String?,
    val subscriptions_url: String?,
    val organizations_url: String?,
    val repos_url: String?,
    val received_events_url: String?,
    val type: String?,
    val score: Int,
    val following_url: String?,
    val gists_url: String?,
    val starred_url: String?,
    val events_url: String?,
    val site_admin: Boolean,
    val isFavorite: Boolean = false
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeInt(id)
        parcel.writeString(node_id)
        parcel.writeString(avatar_url)
        parcel.writeString(gravatar_id)
        parcel.writeString(url)
        parcel.writeString(html_url)
        parcel.writeString(followers_url)
        parcel.writeString(subscriptions_url)
        parcel.writeString(organizations_url)
        parcel.writeString(repos_url)
        parcel.writeString(received_events_url)
        parcel.writeString(type)
        parcel.writeInt(score)
        parcel.writeString(following_url)
        parcel.writeString(gists_url)
        parcel.writeString(starred_url)
        parcel.writeString(events_url)
        parcel.writeByte(if (site_admin) 1 else 0)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}
//User(login=RaedGhazal, id=29174647, node_id=MDQ6VXNlcjI5MTc0NjQ3, avatar_url=https://avatars.githubusercontent.com/u/29174647?v=4, gravatar_id=, url=https://api.github.com/users/RaedGhazal, html_url=https://github.com/RaedGhazal, followers_url=https://api.github.com/users/RaedGhazal/followers, subscriptions_url=https://api.github.com/users/RaedGhazal/subscriptions, organizations_url=https://api.github.com/users/RaedGhazal/orgs, repos_url=https://api.github.com/users/RaedGhazal/repos, received_events_url=https://api.github.com/users/RaedGhazal/received_events,
// type=User, score=1, following_url=https://api.github.com/users/RaedGhazal/following{/other_user}, gists_url=https://api.github.com/users/RaedGhazal/gists{/gist_id}, starred_url=https://api.github.com/users/RaedGhazal/starred{/owner}{/repo}, events_url=https://api.github.com/users/RaedGhazal/events{/privacy}, site_admin=false, isFavorite=false)