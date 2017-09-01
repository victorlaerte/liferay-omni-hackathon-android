package com.liferay.omnihackathon.model

import android.os.Parcel
import android.os.Parcelable


/**
 * @author Victor Oliveira
 */
class Processo(var name:String, var status: String) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(name)
		parcel.writeString(status)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Processo> {
		override fun createFromParcel(parcel: Parcel): Processo {
			return Processo(parcel)
		}

		override fun newArray(size: Int): Array<Processo?> {
			return arrayOfNulls(size)
		}
	}
}