package com.example.myapplication.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * {"date":"2019-10-02",
 * "explanation":"They are not alive -- but they are dying.
 * The unusual forms found in the Carina nebula,
 * a few of which are featured here,
 * might best be described as evaporating.
 * Energetic light and winds from nearby stars are breaking apart the dark dust grains that make the iconic forms opaque.
 * Ironically the figures, otherwise known as dark molecular clouds or bright rimmed globules,
 * frequently create in their midst the very stars that later destroy them.
 * The floating space structures pictured here by the orbiting Hubble Space Telescope span a few light months.
 * The Great Nebula in Carina itself spans about 30 light years, lies about 7,500 light years away,
 * and can be seen with a small telescope toward the constellation of Keel(Carina).",
 * "hdurl":"https://apod.nasa.gov/apod/image/1909/McloudsCarina_Hubble_1397.jpg",
 * "media_type":"image",
 * "service_version":"v1",
 * "title":"Molecular Clouds in the Carina Nebula",
 * "url":"https://apod.nasa.gov/apod/image/1909/McloudsCarina_Hubble_1080.jpg"}
 */
@Parcelize
data class APOD(val title: String,
                @SerializedName("url")
                val imageUrl: String,
                val explanation: String,
                val date: String) : Parcelable