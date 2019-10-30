package com.example.myapplication.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.Navigator
import com.example.myapplication.api.FetchAPODAPI.Companion.parseAPODList
import com.example.myapplication.observeForTesting
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private lateinit var repository: MainRepository

    @MockK
    private lateinit var navigator: Navigator

    /**
     * For LiveData won't encounter Method getMainLooper in android.os.Looper not mocked.
     * SetDelegate to ArchTaskExecutor to execute on the same thread immediately for executeOnDiskIO and postToMainThread.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        repository = MainRepository()
        viewModel = MainViewModel(repository, navigator)
    }

    @Test
    fun `GIVEN APOD list with some data's imageUrl not endWith jpg, THEN remove them from list`() {
        val list = parseAPODList(RAW_DATA_SECOND_IMAGE_URL_NOT_VALID)

        assertThat(list).hasSize(2)
        assertThat(list[1].imageUrl.endsWith(".jpg")).isFalse()

        repository._apodList.value = list

        viewModel.apodList.observeForTesting {
            assertThat(viewModel.apodList.value).hasSize(1)

            val apod = viewModel.apodList.value?.firstOrNull()
            assertThat(apod).isNotNull()
            assertThat(apod?.imageUrl?.endsWith(".jpg")).isTrue()
        }

    }

    companion object {
        private const val RAW_DATA_SECOND_IMAGE_URL_NOT_VALID =
            "[{\"copyright\":\"Giorgia HoferCortina Astronomical Association\",\"date\":\"2019-09-02\",\"explanation\":\"What are those bright lights in the sky ahead?  When hiking a high mountain pass in northern Italy three weeks ago, a conjunction between our Moon and the distant planet Jupiter was visible toward the south just after sunset. The picturesque mountains in the distance are Tre Cime di Lavaredo (Three Peaks of Lavaredo), a UNESCO World Heritage Site and three of the best known mountain peaks in Italy, the Dolomites, and the entire Alps. In the foreground on the left is Locatelli Hut, a refuge for tired hikers as it is located over an hour from nearest parking lot. The bright sky object on the upper left is Saturn.  The entire scene was captured on a single 8-second exposure.  Jupiter and Saturn will remain prominent in the southwestern sky after sunset this month, while the Moon, in its monthly orbit around the Earth, will pass near Jupiter again in about four days.\",\"hdurl\":\"https://apod.nasa.gov/apod/image/1909/MoonJupiterPass_Hofer_1200.jpg\",\"media_type\":\"image\",\"service_version\":\"v1\",\"title\":\"The Moon and Jupiter over the Alps\",\"url\":\"https://apod.nasa.gov/apod/image/1909/MoonJupiterPass_Hofer_960.jpg\"}," +
                    "{\"date\":\"2019-09-03\",\"explanation\":\"What created this unusual explosion? Three weeks ago, gravitational wave detectors in the USA and Europe -- the LIGO and Virgo detectors -- detected a burst of gravitational radiation that had the oscillating pattern expected when a black hole destroys a neutron star.  One object in event S190814bv was best fit with a mass greater than five times the mass of the Sun -- making it a good candidate for a black hole, while the other object appeared to have a mass less than three times the mass of the Sun -- making it a good candidate for a neutron star. No similar event had been detected with gravitational waves before.  Unfortunately, no light was seen from this explosion, light that might have been triggered by the disrupting neutron star. It is theoretically possible that the lower mass object was also a black hole, even though no clear example of a black hole with such a low mass is known. The featured video was created to illustrate a previously suspected black hole - neutron star collision detected in light in 2005, specifically gamma-rays from the burst GRB 050724. The animated video starts with a foreground neutron star orbiting a black hole surrounded by an accretion disk. The black hole's gravity then shreds the neutron star, creating a jet as debris falls into the black hole. S190814bv will continue to be researched, with clues about the nature of the objects involved possibly coming from future detections of similar systems.   Follow APOD in English on: Instagram, Facebook,  Reddit, or Twitter\",\"media_type\":\"video\",\"service_version\":\"v1\",\"title\":\"Unusual Signal Suggests Neutron Star Destroyed by Black Hole\",\"url\":\"https://www.youtube.com/embed/bQ9qcR-KCsA?rel=0\"}]"
    }
}