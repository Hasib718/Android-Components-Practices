package com.hasib.native_admob

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.hasib.native_admob.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adLoader: AdLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this) {}

        adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd ->
                if (!adLoader.isLoading) {
                    Toast.makeText(this@MainActivity, "Ad Successful", Toast.LENGTH_SHORT).show()
                }

                if (isDestroyed) {
                    ad.destroy()
                }

                val nightModeFlags: Int = applicationContext.resources.configuration.uiMode and
                        Configuration.UI_MODE_NIGHT_MASK
                val color =
                    if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) Color.BLACK else Color.WHITE
                val styles =
                    NativeTemplateStyle.Builder()
                        .withMainBackgroundColor(ColorDrawable(color))
                        .withCallToActionTypefaceColor(color)
                        .build()
                val template = findViewById<TemplateView>(R.id.my_template)
                template.setStyles(styles)
                template.setNativeAd(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Toast.makeText(this@MainActivity, "Ad Failed", Toast.LENGTH_SHORT).show()
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            )
            .build()

        adLoader.loadAds(AdRequest.Builder().build(), 3)
    }
}